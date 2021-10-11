import React from 'react';
import styled from 'styled-components';
import {Popover} from "antd";
import Button from "antd-button-color";
import SignInForm from "./SignInForm";
import {UserAddOutlined, UserDeleteOutlined} from "@ant-design/icons"
import {useDispatch, useSelector} from "react-redux";
import {logOutAction} from "../../reducers";

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
    const logInUser = useSelector((state)=>state.index.logInUser);
    const dispatch = useDispatch();

    const logoutClick = () =>{
        dispatch(logOutAction());
    }
    return (
        <style.Header>
            <style.Title>X-ray Classifier</style.Title>
            {logInUser != null ?
                <Button style={{fontWeight: "bold" ,marginLeft : "5%"}} onClick={logoutClick}><UserDeleteOutlined />로그아웃</Button>
                :
                <Popover placement="topLeft" title={<div><UserAddOutlined /> 회원가입</div>}  content={<SignInForm/>} trigger="click">
                    <Button style={{fontWeight: "bold" ,marginLeft : "5%"}}><UserAddOutlined />회원가입</Button>
                </Popover>
            }
        </style.Header>
    )
};

export default Header;