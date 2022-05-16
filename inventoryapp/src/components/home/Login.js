import {useNavigate} from "react-router-dom";
import {Form, Button, FormGroup, Input, Label, FormText} from "reactstrap";
import React, {useState} from "react";
import SignupButton from "./SignupButton";
import "./Login.css";
import AuthService from "../../services/AuthService";
import axios from "axios";

const Login = (props) => {
    const [companyName, setCompanyName] = useState('');
    const [password, setPassword] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [consoleMessage, setConsoleMessage] = useState('');
    const navigate = useNavigate();
    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";

    const handleCompanyNameChange = (event) => {
        setCompanyName(event.target.value);
    }

    const handlePasswordChange = (event) => {
        validatePassword(event);
        setPasswordValue(event);
    }

    const validatePassword = (event) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/;

        if(passwordRegex.test(event.target.value)) {
            setValidPassword(true)
        } else {
            setValidPassword(false);
        }

    }

    const setPasswordValue = (event) => {
        setPassword(event.target.value);
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
        setConsoleMessage("Signing in....");
        console.log("Signing in.....")
        const url = `${baseURL}/company/${companyName}?password=${password}`;

        axios.get(url).then(response => {
            console.log(response.data);
            AuthService.setUserSession(companyName);
            props.authenticate();
            navigate('/inventory');
        }).catch(error => {
            console.log(error.message);
            setConsoleMessage("Looks like there is an issue with your username or password. " +
                "Please enter valid credentials");
        })
    }


    return (

        <div>
            <div className={"signin"}>
                <h4>Welcome Back!</h4>
                <Form className={"form"} onSubmit={handleFormSubmit}>
                    <FormGroup>
                        <Label htmlFor={"companyName"}>Company Name</Label>
                        <Input type={"text"} name={"companyName"} id={"companyName"} value={companyName}
                               onChange={handleCompanyNameChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label htmlFor={"password"}>Password</Label>
                        <Input type={"password"} name={"password"} id={"password"} value={password}
                               valid={validPassword}
                               invalid={validPassword}
                               onChange={handlePasswordChange}
                        />
                        <FormText>Your password needs to be minimum 8 characters, 1 uppercase, 1 lowercase and 1 special character.</FormText>
                    </FormGroup>
                    <Button>Login</Button>
                </Form>
                <div>
                    {consoleMessage}
                </div>
                <div className={"noAccount"}>
                    <h6>Do not have an account. <SignupButton />
                    </h6>
                </div>
            </div>

        </div>

    )

}

export default Login;