import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
    Typography,
    Card,
    TextField,
    Container
} from '@mui/material'

const Register = () => {

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [userId, setUserId] = useState("");
    const [organization, setOrganization] = useState("");
    const [password, setPassword] = useState("");

    const formItems = [
        {label: "First Name", value: firstName, setter: setFirstName},
        {label: "Last Name", value: lastName, setter: setLastName},
        {label: "Email", value: email, setter: setEmail},
        {label: "User ID", value: userId, setter: setUserId},
        {label: "Organization", value: organization, setter: setOrganization},
        {label: "Password", value: password, setter: setPassword}
    ]

    return (
        <Container>
            <Typography variant="h1">
                Register
            </Typography>

            {formItems.map(formItem => {
                return (
                    <TextField
                        label={formItem.label}
                        value={formItem.value}
                        onChange={e => formItem.setter(e.target.value)}
                        variant="outlined"
                    />
                )
            })}
        </Container>
    )
}

export default Register;
