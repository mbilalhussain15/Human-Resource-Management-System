import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './EmployeeList.css';
import Sidebar from '../Components/Sidebar';
import { BsThreeDotsVertical } from "react-icons/bs";
import { FaPhoneAlt } from 'react-icons/fa';
import { IoIosMail } from "react-icons/io";
import HeaderComponent from '../Components/HeaderComponent';

function Employee() {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState({});

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/employee/all')
      .then(response => {
        const employeeData = response.data;
        console.log("employeeData ="+employeeData)
        setEmployees(employeeData);
        employeeData.forEach(employee => {
          fetchDepartment(employee.departmentId);
          console.log(employee)
        });
      })
      .catch(error => {
        console.error("There was an error fetching the employee data!", error);
      });
  }, []);

  const fetchDepartment = (departmentId) => {
    if (!departments[departmentId]) {
      axios.get(`http://localhost:8080/api/departments/${departmentId}`)
        .then(response => {
          setDepartments(prevDepartments => ({
            ...prevDepartments,
            [departmentId]: response.data.name
          }));
          console.log(response)
        })
        .catch(error => {
          console.error(`There was an error fetching the department data for ID ${departmentId}!`, error);
        });
    }
  };

  return (
    <div className="app">
      <Sidebar />
      <div className="main-content">
        <HeaderComponent />
        <div className="employee-container">
          <div className="employee-text">Employee List</div>
          <table className="employee-table">
            <thead>
              <tr style={{
                fontWeight: '0',
                fontSize: '0.8vw',
                color: 'black'
              }}>
                <th style={{
                  padding: '20px 0px 20px 40px',
                  marginLeft: '10px'
                }}>Name</th>
                <th>Position</th>
                <th>Employment Type</th>
                <th>Status</th>
                <th>Joining Date</th>
                <th>Email address</th>
                <th>Phone</th>
                <th></th>
              </tr>
            </thead>
            <tbody style={{ fontSize: '0.8vw', textAlign: 'center' }}>
              {employees.map(employee => (
                <tr key={employee.employeeID}>
                  <td style={{ display: 'flex', alignItems: 'center', borderLeft: '1px solid #E0E4EA', padding: '20px' }}>
                    
                    <span>{employee.firstName}</span>
                  </td>
                  <td>
                    <span style={{
                      backgroundColor: '#DDCBFC',
                      color: 'black',
                      borderRadius: '30px',
                      padding: '8px 20px',
                      display: 'inline-block'
                    }}>{employee.designation}</span>
                  </td>
                  <td>{employee.employmentType}</td>
                  <td>
                    <span style={{
                      backgroundColor: employee.employmentStatus === 'Active' ? '#DDFCE0' : '#FCE0E0',
                      color: employee.employmentStatus === 'Active' ? '#0EB01D' : '#B00E0E',
                      borderRadius: '30px',
                      padding: '8px 20px',
                      display: 'inline-block'
                    }}>{employee.employmentStatus}</span>
                  </td>
                  <td>{employee.createdAt}</td>
                  <td><IoIosMail style={{ marginRight: '5px' }} />{employee.email}</td>
                  <td><FaPhoneAlt style={{ marginRight: '5px' }} />{employee.phone}</td>
                  <td style={{ borderRight: '1px solid #E0E4EA' }}>
                    <BsThreeDotsVertical />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default Employee;
