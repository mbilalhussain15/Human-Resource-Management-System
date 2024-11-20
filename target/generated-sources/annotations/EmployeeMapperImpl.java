
import com.humanresourcemanagementsystem.Dto.EmployeeDTO;
import com.humanresourcemanagementsystem.Entity.Employee;
import com.humanresourcemanagementsystem.Entity.Person;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-20T01:35:34+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO toEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setPersonID( employeePersonPersonID( employee ) );
        employeeDTO.setFirstName( employeePersonFirstName( employee ) );
        employeeDTO.setLastName( employeePersonLastName( employee ) );
        employeeDTO.setAddress( employeePersonAddress( employee ) );
        employeeDTO.setEmpEmail( employeePersonPersonEmail( employee ) );
        employeeDTO.setPhone( employeePersonPhone( employee ) );
        employeeDTO.setDateOfBirth( employeePersonDateOfBirth( employee ) );
        employeeDTO.setGender( employeePersonGender( employee ) );
        employeeDTO.setNationality( employeePersonNationality( employee ) );
        employeeDTO.setMaritalStatus( employeePersonMaritalStatus( employee ) );
        employeeDTO.setPersonType( employeePersonPersonType( employee ) );
        employeeDTO.setCreatedAt( employeePersonCreatedAt( employee ) );
        employeeDTO.setUpdatedAt( employeePersonUpdatedAt( employee ) );
        employeeDTO.setEmployeeID( employee.getEmployeeID() );
        employeeDTO.setPerson( employee.getPerson() );
        employeeDTO.setSupervisorId( employee.getSupervisorId() );
        employeeDTO.setManageWhom( employee.getManageWhom() );
        employeeDTO.setEmploymentType( employee.getEmploymentType() );
        employeeDTO.setAdmin( employee.getAdmin() );
        employeeDTO.setEmpPassword( employee.getEmpPassword() );
        employeeDTO.setDesignation( employee.getDesignation() );
        employeeDTO.setHireDate( employee.getHireDate() );
        employeeDTO.setTerminationDate( employee.getTerminationDate() );
        employeeDTO.setEmploymentStatus( employee.getEmploymentStatus() );
        employeeDTO.setWorkLocation( employee.getWorkLocation() );

        return employeeDTO;
    }

    @Override
    public Employee toEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setPerson( employeeDTOToPerson( employeeDTO ) );
        employee.setEmployeeID( employeeDTO.getEmployeeID() );
        employee.setSupervisorId( employeeDTO.getSupervisorId() );
        employee.setManageWhom( employeeDTO.getManageWhom() );
        employee.setEmploymentType( employeeDTO.getEmploymentType() );
        employee.setAdmin( employeeDTO.getAdmin() );
        employee.setEmpEmail( employeeDTO.getEmpEmail() );
        employee.setEmpPassword( employeeDTO.getEmpPassword() );
        employee.setDesignation( employeeDTO.getDesignation() );
        employee.setHireDate( employeeDTO.getHireDate() );
        employee.setTerminationDate( employeeDTO.getTerminationDate() );
        employee.setEmploymentStatus( employeeDTO.getEmploymentStatus() );
        employee.setWorkLocation( employeeDTO.getWorkLocation() );
        employee.setCreatedAt( employeeDTO.getCreatedAt() );
        employee.setUpdatedAt( employeeDTO.getUpdatedAt() );

        return employee;
    }

    private int employeePersonPersonID(Employee employee) {
        if ( employee == null ) {
            return 0;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return 0;
        }
        int personID = person.getPersonID();
        return personID;
    }

    private String employeePersonFirstName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String firstName = person.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String employeePersonLastName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String lastName = person.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String employeePersonAddress(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String address = person.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }

    private String employeePersonPersonEmail(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String personEmail = person.getPersonEmail();
        if ( personEmail == null ) {
            return null;
        }
        return personEmail;
    }

    private String employeePersonPhone(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String phone = person.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private Date employeePersonDateOfBirth(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        Date dateOfBirth = person.getDateOfBirth();
        if ( dateOfBirth == null ) {
            return null;
        }
        return dateOfBirth;
    }

    private String employeePersonGender(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String gender = person.getGender();
        if ( gender == null ) {
            return null;
        }
        return gender;
    }

    private String employeePersonNationality(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String nationality = person.getNationality();
        if ( nationality == null ) {
            return null;
        }
        return nationality;
    }

    private String employeePersonMaritalStatus(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String maritalStatus = person.getMaritalStatus();
        if ( maritalStatus == null ) {
            return null;
        }
        return maritalStatus;
    }

    private String employeePersonPersonType(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        String personType = person.getPersonType();
        if ( personType == null ) {
            return null;
        }
        return personType;
    }

    private Date employeePersonCreatedAt(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        Date createdAt = person.getCreatedAt();
        if ( createdAt == null ) {
            return null;
        }
        return createdAt;
    }

    private Date employeePersonUpdatedAt(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Person person = employee.getPerson();
        if ( person == null ) {
            return null;
        }
        Date updatedAt = person.getUpdatedAt();
        if ( updatedAt == null ) {
            return null;
        }
        return updatedAt;
    }

    protected Person employeeDTOToPerson(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Person person = new Person();

        person.setPersonID( employeeDTO.getPersonID() );
        person.setFirstName( employeeDTO.getFirstName() );
        person.setLastName( employeeDTO.getLastName() );
        person.setAddress( employeeDTO.getAddress() );
        person.setPersonEmail( employeeDTO.getEmpEmail() );
        person.setPhone( employeeDTO.getPhone() );
        person.setDateOfBirth( employeeDTO.getDateOfBirth() );
        person.setGender( employeeDTO.getGender() );
        person.setNationality( employeeDTO.getNationality() );
        person.setMaritalStatus( employeeDTO.getMaritalStatus() );
        person.setPersonType( employeeDTO.getPersonType() );
        person.setCreatedAt( employeeDTO.getCreatedAt() );
        person.setUpdatedAt( employeeDTO.getUpdatedAt() );

        return person;
    }
}
