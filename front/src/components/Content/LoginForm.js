import React, {useCallback, useEffect, useState} from 'react';
import {Form, Input, Button, Alert} from 'antd';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';

import { logInAction } from '../../reducers/index';

const ButtonWrapper = styled.div`
  margin-top: 10px;
`;

const FormWrapper = styled(Form)`
  padding: 10px;
`;

const LoginForm = () => {
    const useInput = (initValue = null) => {
        const [value, setter] = useState(initValue);
        const handler = useCallback((e) => {
            setter(e.target.value);
        }, []);
        return [value, handler, setter];
    };

    const dispatch = useDispatch();
    const [userNickName, onChangeName] = useInput('');
    const [password, onChangePassword] = useInput('');
    const logInUser = useSelector((state)=>state.index.logInUser);
    const logInFailure = useSelector((state)=>state.index.logInFailure);

    const onSubmitForm = useCallback(() => {
        console.log(userNickName, password);
        dispatch(logInAction({ userNickName, password }));
    }, [userNickName, password]);

    return (
        <FormWrapper onFinish={onSubmitForm}>
            <div style={{color : "white" , fontWeight : "bold" , fontSize : "20px"}}>
                <label htmlFor="user-name" >아이디</label>
                <br />
                <Input name="user-name" type="name" value={userNickName} onChange={onChangeName} required />
            </div>
            <div style={{color : "white" , fontWeight : "bold" , fontSize : "20px"}}>
                <label htmlFor="user-password">비밀번호</label>
                <br />
                <Input
                    name="user-password"
                    type="password"
                    value={password}
                    onChange={onChangePassword}
                    required
                />
            </div>
            <ButtonWrapper>
                <Button type="primary" htmlType="submit" >로그인</Button>
                {logInUser == null && logInFailure == true &&
                <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                    <p></p>
                    <Alert type="error" message="존재하지 않는 아이디 이거나, 비밀번호가 틀립니다." banner/>
                </div>
                }
            </ButtonWrapper>
        </FormWrapper>
    );
};

export default LoginForm;
