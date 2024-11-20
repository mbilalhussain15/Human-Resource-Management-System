import React, { useState } from "react";
import loginImage from "../images/loginImage.svg";
import nexusLogo from "../images/nexusLogo.png";
import "./Register.css";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  // State variables for form inputs and validation
  const [step, setStep] = useState(1); // Step for rendering forms
  const navigate = useNavigate();
  // State variables for the first page inputs
  const [personEmail, setpersonEmail] = useState('');
  const [personPassword, setpersonPassword] = useState('');
  const [confirmpersonPassword, setConfirmpersonPassword] = useState('');
  const [personPasswordError, setpersonPasswordError] = useState('');

  // State variables for the second page inputs
  const [firstName, setfirstName] = useState('');
  const [lastName, setlastName] = useState('');
  const [dateOfBirth, setdateOfBirth] = useState('');
  const [nationality, setNationality] = useState('');
  const [gender, setGender] = useState('');
  const [phone, setPhone] = useState('');
  const [maritalStatus, setMaritalStatus] = useState('');
  const [street, setStreet] = useState('');
  const [streetNo, setStreetNo] = useState('');
  const [postcode, setPostcode] = useState('');
  const [city, setCity] = useState('');
  const [country, setCountry] = useState('');

  // Function to validate personPassword strength
  const validatepersonPassword = (personPassword) => {
    const personPasswordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return personPasswordRegex.test(personPassword);
  };

  const handleFirstSubmit = (e) => {
    e.preventDefault();
    if (personPassword !== confirmpersonPassword) {
      setpersonPasswordError('personPasswords do not match'); 
    } else if (!validatepersonPassword(personPassword)) {
      setpersonPasswordError('personPassword must be at least 8 characters long and contain at least one uppercase letter, one number, and one special character'); // Set error message if personPassword is not strong enough
    } else {
      setpersonPasswordError(''); 
      setStep(2);
    }
  };


  const handleSecondSubmit = async (e) => {
    e.preventDefault();
    try {
      // Combine address fields
      const address = `${street}, ${streetNo}, ${postcode}, ${city}, ${country}`;

      const response = await axios.post("http://localhost:8080/api/v1/employee/save", {
        personEmail,
        empEmail:personEmail,
        empPassword:personPassword,
        personPassword,
        firstName,
        lastName,
        address,
        dateOfBirth,
        nationality,
        gender,
        phone,
        maritalStatus,
        street,
        streetNo,
        postcode,
        city,
        country,
        departmentId:1,
      }, {
        headers: {
          "Content-Type": "application/json",
        },
      });

    //       console.log('Form submitted', {
    //   personEmail, personPassword,
    //   firstName, lastName, dateOfBirth, nationality,
    //   gender, phone, maritalStatus,
    //   street, streetNo, postcode, city, country
    // });
      console.log("Employee registered successfully:", response.data);
      navigate('/');
    } catch (error) {
      console.error("Error registering employee:", error);
    }
  };


  // const handleSecondSubmit = (e) => {
  //   e.preventDefault();
  //   console.log('Form submitted', {
  //     personEmail, personPassword, confirmpersonPassword,
  //     firstName, lastName, dateOfBirth, nationality,
  //     gender, phone, maritalStatus,
  //     street, streetNo, postcode, city, country
  //   });
  // };

  const handleBack = () => {
    setStep(1);
  };

  return (
    <div className="register-container">
      {step === 1 ? (
        <>
          <div className="image-section">
            <img src={loginImage} alt="Human Resource System" />
          </div>
          <div className="form-section">
            <img src={nexusLogo} alt="HR Nexus Logo" className="logo" />
            <h2>Create an account</h2>
            <h5>Please type in your E-Mail and personPassword</h5>
            <form onSubmit={handleFirstSubmit}>
              <input
                type="email"
                placeholder="email@domain.com"
                value={personEmail}
                onChange={(e) => setpersonEmail(e.target.value)}
                required
              />
              <input
                type="password"
                placeholder="Password"
                value={personPassword}
                onChange={(e) => setpersonPassword(e.target.value)}
                required
              />
              <input
                type="password"
                placeholder="repeat Password"
                value={confirmpersonPassword}
                onChange={(e) => setConfirmpersonPassword(e.target.value)}
                required
              />
              {personPasswordError && <p className="error">{personPasswordError}</p>}
              <button type="submit">Next</button>
            </form>
          </div>
        </>
      ) : (
        <>
          <div className="form-section">
            <button className="back-button" type="button" onClick={handleBack}>Back</button>
            <img src={nexusLogo} alt="HR Nexus Logo" className="logo-large" />
            <h2>Create an account</h2>
            <form onSubmit={handleSecondSubmit}>
              <div className="form-group">
                <div className="personal-info">
                  <h3>Personal Information</h3>
                  <input
                    type="text"
                    placeholder="firstName"
                    value={firstName}
                    onChange={(e) => setfirstName(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="lastName"
                    value={lastName}
                    onChange={(e) => setlastName(e.target.value)}
                    required
                  />
                  <input
                    type="date"
                    placeholder="Date of birth"
                    value={dateOfBirth}
                    onChange={(e) => setdateOfBirth(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Nationality"
                    value={nationality}
                    onChange={(e) => setNationality(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Gender"
                    value={gender}
                    onChange={(e) => setGender(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Phone number"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Marital Status"
                    value={maritalStatus}
                    onChange={(e) => setMaritalStatus(e.target.value)}
                    required
                  />
                </div>
                <div className="address-info">
                  <h3>Address</h3>
                  <input
                    type="text"
                    placeholder="Street"
                    value={street}
                    onChange={(e) => setStreet(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Street No."
                    value={streetNo}
                    onChange={(e) => setStreetNo(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Postcode"
                    value={postcode}
                    onChange={(e) => setPostcode(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="City"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    required
                  />
                  <input
                    type="text"
                    placeholder="Country"
                    value={country}
                    onChange={(e) => setCountry(e.target.value)}
                    required
                  />
                  
                </div>
              </div>
              <button type="submit">Create account</button>
            </form>
          </div>
          <div className="image-section">
            <img src={loginImage} alt="Human Resource System" />
          </div>
        </>
      )}
    </div>
  );
};

export default Register;
