package com.humanresourcemanagementsystem.Service.Impl;

import com.humanresourcemanagementsystem.Dto.EmployeeDTO;
import com.humanresourcemanagementsystem.Dto.EmployeePersonDTO;
import com.humanresourcemanagementsystem.Entity.Department;
import com.humanresourcemanagementsystem.Entity.Employee;
import com.humanresourcemanagementsystem.Entity.Person;
import com.humanresourcemanagementsystem.Mapper.EmployeeMapper;
import com.humanresourcemanagementsystem.Repo.DepartmentRepository;
import com.humanresourcemanagementsystem.Repo.EmployeeRepository;
import com.humanresourcemanagementsystem.Repo.PersonRepository;
import com.humanresourcemanagementsystem.Service.EmployeeService;
import com.humanresourcemanagementsystem.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class EmployeeIMPL implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeIMPL.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            // Map the Employee entity to EmployeeDTO using the mapper
            return employeeMapper.toEmployeeDTO(employee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    @Override
//Display education by ID
    public EmployeeDTO getEmployee(int id) {
        System.out.println("employeeId= "+id);
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // Fetch associated education information
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployeeID(employee.getEmployeeID());
            employeeDTO.setSupervisorId(employee.getSupervisorId());
            employeeDTO.setDepartmentId(employee.getDepartment());


            employeeDTO.setSupervisor(employee.getIsSupervisor());
            employeeDTO.setManageWhom(employee.getManageWhom());
            employeeDTO.setEmploymentType(employee.getEmploymentType());
            employeeDTO.setAdmin(employee.getAdmin());
            employeeDTO.setEmpEmail(employee.getEmpEmail());
            employeeDTO.setEmpPassword(employee.getEmpPassword());
            employeeDTO.setDesignation(employee.getDesignation());
            employeeDTO.setHireDate(employee.getHireDate());
            employeeDTO.setTerminationDate(employee.getTerminationDate());
            employeeDTO.setEmploymentStatus(employee.getEmploymentStatus());
            employeeDTO.setWorkLocation(employee.getWorkLocation());
            employeeDTO.setSupervisor(employee.getIsSupervisor());
            employeeDTO.setAdmin(employee.getAdmin());

            // Set person fields
            Person person = employee.getPerson();
            if (person != null) {
                employeeDTO.setPersonID(person.getPersonID());
                employeeDTO.setFirstName(person.getFirstName());
                employeeDTO.setLastName(person.getLastName());
                employeeDTO.setAddress(person.getAddress());
                employeeDTO.setPersonEmail(person.getPersonEmail());
                employeeDTO.setPhone(person.getPhone());
                employeeDTO.setDateOfBirth(person.getDateOfBirth());
                employeeDTO.setGender(person.getGender());
                employeeDTO.setNationality(person.getNationality());
                employeeDTO.setMaritalStatus(person.getMaritalStatus());
                employeeDTO.setPersonType(person.getPersonType());
                employeeDTO.setCreatedAt(person.getCreatedAt());
                employeeDTO.setUpdatedAt(person.getUpdatedAt());
            }

            return employeeDTO;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    @Override
    public List<EmployeePersonDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);

        return employees.stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String addEmployee(EmployeeDTO employeeDTO) {
        // Create a Person entity from the DTO
        Person person = new Person(
                employeeDTO.getPersonID(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getAddress(),
                employeeDTO.getPersonEmail(),
                passwordEncoder.encode(employeeDTO.getPersonPassword()),
                employeeDTO.getPhone(),
                employeeDTO.getDateOfBirth(),
                employeeDTO.getGender(),
                employeeDTO.getNationality(),
                employeeDTO.getMaritalStatus(),
                employeeDTO.getPersonType()
        );

        // Save the Person entity
        Person savedPerson = personService.savePerson(person);

        // Create an Employee entity and set the person
        Employee employee = new Employee(savedPerson);

        // Retrieve department
        LOGGER.info("Fetching department with ID: " + employeeDTO.getDepartmentId());
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + employeeDTO.getDepartmentId()));

        LOGGER.info("Department found: " + department.getDepartmentName());

        // Set other employee details from the DTO
        employee.setDepartment(department);
        employee.setManageWhom(employeeDTO.getManageWhom());
        employee.setEmploymentType(employeeDTO.getEmploymentType());
        employee.setAdmin(employeeDTO.getAdmin());
        employee.setEmpEmail(employeeDTO.getEmpEmail());
        employee.setEmpPassword(passwordEncoder.encode(employeeDTO.getEmpPassword()));
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setTerminationDate(employeeDTO.getTerminationDate());
        employee.setEmploymentStatus(employeeDTO.getEmploymentStatus());
        employee.setWorkLocation(employeeDTO.getWorkLocation());
        employee.setCreatedAt(employeeDTO.getCreatedAt());
        employee.setUpdatedAt(employeeDTO.getUpdatedAt());

        // Save the Employee entity to get the employeeID
        Employee savedEmployee = employeeRepository.save(employee);

        // Set supervisorId to the employee's own ID if necessary
        if (employeeDTO.getSupervisorId() == 0) {
            savedEmployee.setSupervisorId(savedEmployee.getEmployeeID());
            employeeRepository.save(savedEmployee);
        }

        return savedEmployee.getPerson().getFirstName();
    }

    public EmployeePersonDTO updateEmployeePerson(int employeeID, EmployeePersonDTO employeePersonDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Person person = employee.getPerson();

            // Update employee details
            employee.setDesignation(employeePersonDTO.getDesignation());
            employeeRepository.save(employee);

            // Update person details
            person.setFirstName(employeePersonDTO.getFirstName());
            person.setLastName(employeePersonDTO.getLastName());
            person.setAddress(employeePersonDTO.getAddress());
            person.setPersonEmail(employeePersonDTO.getEmail());
            person.setPersonPassword(passwordEncoder.encode(employeePersonDTO.getPassword()));
            person.setPhone(employeePersonDTO.getPhone());
            person.setDateOfBirth(employeePersonDTO.getDateOfBirth());
            person.setGender(employeePersonDTO.getGender());
            person.setNationality(employeePersonDTO.getNationality());
            person.setMaritalStatus(employeePersonDTO.getMaritalStatus());
            person.setPersonType(employeePersonDTO.getPersonType());
            person.setUpdatedAt(employeePersonDTO.getUpdatedAt());
            personRepository.save(person);
            return employeePersonDTO;
        }

        return null;
    }
}



























//
//package com.humanresourcemanagementsystem.Service.Impl;
//
//import com.humanresourcemanagementsystem.Dto.EmployeeDTO;
//import com.humanresourcemanagementsystem.Dto.EmployeePersonDTO;
//import com.humanresourcemanagementsystem.Entity.Department;
//import com.humanresourcemanagementsystem.Entity.Employee;
//import com.humanresourcemanagementsystem.Entity.Person;
//
//import com.humanresourcemanagementsystem.Mapper.EmployeeMapper;
//import com.humanresourcemanagementsystem.Repo.DepartmentRepository;
//import com.humanresourcemanagementsystem.Repo.EmployeeRepository;
//import com.humanresourcemanagementsystem.Repo.PersonRepository;
//import com.humanresourcemanagementsystem.Service.EmployeeService;
//import com.humanresourcemanagementsystem.Service.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.logging.Logger;
//
//@Service
//public class EmployeeIMPL implements EmployeeService {
//
//    private static final Logger LOGGER = Logger.getLogger(EmployeeIMPL.class.getName());
//
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Autowired
//    private PersonService personService;
//
//    @Autowired
//    private EmployeeMapper employeeMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public EmployeeDTO getEmployeeById(int id) {
//        Optional<Employee> employeeOpt = employeeRepository.findById(id);
//        if (employeeOpt.isPresent()) {
//            Employee employee = employeeOpt.get();
//            // Map the Employee entity to EmployeeDTO using the mapper
//            return employeeMapper.toEmployeeDTO(employee);
//        } else {
//            throw new RuntimeException("Employee not found with id: " + id);
//        }
//    }
//
//    @Override
////Display education by ID
//    public EmployeeDTO getEmployee(int id) {
//        Optional<Employee> employeeOpt = employeeRepository.findById(id);
//        System.out.println("employeeDTO-2: " + employeeOpt);
//        if (employeeOpt.isPresent()) {
//            Employee employee = employeeOpt.get();
//
//
//            // Fetch associated education information
//            EmployeeDTO employeeDTO = new EmployeeDTO();
//            employeeDTO.setEmployeeID(employee.getEmployeeID());
//            employeeDTO.setSupervisorId(employee.getSupervisorId());
//            employeeDTO.setDepartmentId(employee.getDepartment());
//            employeeDTO.setSupervisor(employee.getIsSupervisor());
//            employeeDTO.setManageWhom(employee.getManageWhom());
//            employeeDTO.setEmploymentType(employee.getEmploymentType());
//            employeeDTO.setAdmin(employee.getAdmin());
//            employeeDTO.setEmpEmail(employee.getEmpEmail());
//            employeeDTO.setEmpPassword(employee.getEmpPassword());
//            employeeDTO.setDesignation(employee.getDesignation());
//            employeeDTO.setHireDate(employee.getHireDate());
//            employeeDTO.setTerminationDate(employee.getTerminationDate());
//            employeeDTO.setEmploymentStatus(employee.getEmploymentStatus());
//            employeeDTO.setWorkLocation(employee.getWorkLocation());
//            employeeDTO.setSupervisor(employee.getIsSupervisor());
//            employeeDTO.setAdmin(employee.getAdmin());
//
//            // Set person fields
//            Person person = employee.getPerson();
//            if (person != null) {
//                employeeDTO.setPersonID(person.getPersonID());
//                employeeDTO.setFirstName(person.getFirstName());
//                employeeDTO.setLastName(person.getLastName());
//                employeeDTO.setAddress(person.getAddress());
//                employeeDTO.setPersonEmail(person.getPersonEmail());
//                employeeDTO.setPhone(person.getPhone());
//                employeeDTO.setDateOfBirth(person.getDateOfBirth());
//                employeeDTO.setGender(person.getGender());
//                employeeDTO.setNationality(person.getNationality());
//                employeeDTO.setMaritalStatus(person.getMaritalStatus());
//                employeeDTO.setPersonType(person.getPersonType());
//                employeeDTO.setCreatedAt(person.getCreatedAt());
//                employeeDTO.setUpdatedAt(person.getUpdatedAt());
//            }
//
//            return employeeDTO;
//        } else {
//            throw new RuntimeException("Employee not found with id: " + id);
//        }
//    }
//
//    @Override
//    public List<EmployeePersonDTO> getAllEmployees() {
//        List<Employee> employees = employeeRepository.findAll();
//
//        return employees.stream()
//                .map(employeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public String addEmployee(EmployeeDTO employeeDTO) {
//        // Create a Person entity from the DTO
////        System.out.println("employeeDTO: " + employeeDTO);
//
//        Person person = new Person(
//                employeeDTO.getPersonID(),
//                employeeDTO.getFirstName(),
//                employeeDTO.getLastName(),
//                employeeDTO.getAddress(),
//                employeeDTO.getPersonEmail(),
//                passwordEncoder.encode(employeeDTO.getPersonPassword()),
//                employeeDTO.getPhone(),
//                employeeDTO.getDateOfBirth(),
//                employeeDTO.getGender(),
//                employeeDTO.getNationality(),
//                employeeDTO.getMaritalStatus(),
//                employeeDTO.getPersonType()
//        );
//
//
//
//        // Save the Person entity
//        Person savedPerson = personService.savePerson(person);
//
//        // Create an Employee entity and set the person
//        Employee employee = new Employee(savedPerson);
//
////        // Retrieve department
////        LOGGER.info("Fetching department with ID: " + employeeDTO.getDepartmentId());
////        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
////                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + employeeDTO.getDepartmentId()));
////
////        LOGGER.info("Department found: " + department.getDepartmentName());
//
//
//
//
//        if (employeeDTO.getDepartmentId() != 0) {
//            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
//                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + employeeDTO.getDepartmentId()));
//            employee.setDepartment(department);
//            employee.setDepartment(department);
//        } else {
//            LOGGER.info("No department ID provided. Skipping department association.");
//        }
//
//
//
//
//
//
//
//
//
//        // Set other employee details from the DTO
//
//        employee.setManageWhom(employeeDTO.getManageWhom());
//        employee.setEmploymentType(employeeDTO.getEmploymentType());
//        employee.setAdmin(employeeDTO.getAdmin());
//        employee.setEmpEmail(employeeDTO.getEmpEmail());
//        employee.setEmpPassword(passwordEncoder.encode(employeeDTO.getEmpPassword()));
//        employee.setDesignation(employeeDTO.getDesignation());
//        employee.setHireDate(employeeDTO.getHireDate());
//        employee.setTerminationDate(employeeDTO.getTerminationDate());
//        employee.setEmploymentStatus(employeeDTO.getEmploymentStatus());
//        employee.setWorkLocation(employeeDTO.getWorkLocation());
//        employee.setCreatedAt(employeeDTO.getCreatedAt());
//        employee.setUpdatedAt(employeeDTO.getUpdatedAt());
//
//        // Save the Employee entity to get the employeeID
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        // Set supervisorId to the employee's own ID if necessary
//        if (employeeDTO.getSupervisorId() == 0) {
//            savedEmployee.setSupervisorId(savedEmployee.getEmployeeID());
//            employeeRepository.save(savedEmployee);
//        }
//
//        return savedEmployee.getPerson().getFirstName();
//    }
//
//    public EmployeePersonDTO updateEmployeePerson(int employeeID, EmployeePersonDTO employeePersonDTO) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);
//
//        if (optionalEmployee.isPresent()) {
//            Employee employee = optionalEmployee.get();
//            Person person = employee.getPerson();
//
//            // Update employee details
//            employee.setDesignation(employeePersonDTO.getDesignation());
//            employeeRepository.save(employee);
//
//            // Update person details
//            person.setFirstName(employeePersonDTO.getFirstName());
//            person.setLastName(employeePersonDTO.getLastName());
//            person.setAddress(employeePersonDTO.getAddress());
//            person.setPersonEmail(employeePersonDTO.getEmail());
//            person.setPersonPassword(passwordEncoder.encode(employeePersonDTO.getPassword()));
//            person.setPhone(employeePersonDTO.getPhone());
//            person.setDateOfBirth(employeePersonDTO.getDateOfBirth());
//            person.setGender(employeePersonDTO.getGender());
//            person.setNationality(employeePersonDTO.getNationality());
//            person.setMaritalStatus(employeePersonDTO.getMaritalStatus());
//            person.setPersonType(employeePersonDTO.getPersonType());
//            person.setUpdatedAt(employeePersonDTO.getUpdatedAt());
//            personRepository.save(person);
//            return employeePersonDTO;
//        }
//
//        return null;
//    }
//}
