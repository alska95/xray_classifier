import React from 'react';
import styled from 'styled-components';
import {Popover} from "antd";
import Button from "antd-button-color";
import SignInForm from "./SignInForm";

const style = {};

style.Header = styled.header`
    display: flex;
    padding: 10px;
    background-color: #343a40;
`;

style.Title = styled.label`
    font-size: 20px;
    font-weight: bold;
    color: #ffffff;
`;

const Header = () => {
    return (
        <style.Header>
            <style.Title>X-ray Classifier</style.Title>
            <Popover placement="topLeft" title={"회원가입"} content={<SignInForm/>} trigger="click">
                <Button style={{fontWeight: "bold" ,marginLeft : "5%"}}>회원가입</Button>
            </Popover>
        </style.Header>
    )
};

export default Header;