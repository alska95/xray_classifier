import React, { useCallback, useState } from 'react';
import  { Alert,Form, Input, Checkbox, Button } from 'antd';
import styled from 'styled-components';
import {useDispatch, useSelector} from "react-redux";

import {SIGN_IN_REQUEST, signInAction} from '../../reducers/index';

const ErrorMessage = styled.div`
  color: red;
`;

const SignInForm = () => {
    const useInput = (initValue = null) => {
        const [value, setter] = useState(initValue);
        const handler = useCallback((e) => {
            setter(e.target.value);
        }, []);
        return [value, handler, setter];
    };


    const dispatch = useDispatch();
    const { signInLoading, signInUserInformation, signInError,  signInSuccess} = useSelector((state) => state.index);


    const [email, onChangeEmail] = useInput('');
    const [nickname, setNickName] = useState('');
    const onChangeNickName=useCallback((e)=>{
        setNickName(e.target.value);
    },[nickname])
    const [password, onChangePassword] = useInput('');

    const [passwordCheck, setPasswordCheck] = useState('');
    const [passwordError, setPasswordError] = useState(false);
    const onChangePasswordCheck = useCallback((e) => {
        setPasswordCheck(e.target.value);
        setPasswordError(e.target.value !== password);
    }, [password]);

    const [term, setTerm] = useState('');
    const [termError, setTermError] = useState(false);

    const onChangeTerm = useCallback((e) => {
        setTerm(e.target.checked);
        setTermError(false);
    }, []);

    const onSubmit = useCallback(() => {
        if (password !== passwordCheck) {
            return setPasswordError(true);
        }
        if (!term) {
            return setTermError(true);
        }
        console.log(email, nickname, password);
        dispatch(signInAction({
            "email" : email,
            "nickName" : nickname,
            "password" : password
        }));
        setNickName('');
    }, [email, password, passwordCheck, term , nickname]);


    return (
        <div>
            <Form onFinish={onSubmit}>
                <div>
                    <label htmlFor="user-email">?????????</label>
                    <br />
                    <Input name="user-email" type="email" value={email} required onChange={onChangeEmail} />
                </div>
                <div>
                    <label htmlFor="user-nick">?????????</label>
                    <br />
                    <Input name="user-nick" value={nickname} required onChange={onChangeNickName} />
                    <div style={{color: "red"}}>
                        {signInError && "???????????? ????????? ?????????."}
                    </div>
                </div>
                <div>
                    <label htmlFor="user-password">????????????</label>
                    <br />
                    <Input name="user-password" type="password" value={password} required onChange={onChangePassword}
                           placeholder={"??????????????? ????????? ?????? ???????????????."} />
                </div>
                <div>
                    <label htmlFor="user-password-check"
                           placeholder={"??????????????? ????????? ?????? ???????????????."}>??????????????????</label>
                    <br />
                    <Input
                        name="user-password-check"
                        type="password"
                        value={passwordCheck}
                        required
                        onChange={onChangePasswordCheck}
                    />
                    {passwordError && <ErrorMessage>??????????????? ???????????? ????????????.</ErrorMessage>}
                </div>
                <div>
                    <p></p>
                    <Checkbox name="user-term" checked={term} onChange={onChangeTerm} style={{fontWeight:  "bold"}}>
                        ????????? ???????????? ???????????? ????????????, ????????? ??? ??? ????????? ???????????????.</Checkbox>
                    {termError && <ErrorMessage>????????? ??????????????? ?????????.</ErrorMessage>}
                </div>
                <div style={{ marginTop: 10 }}>
                    <Button type="primary" htmlType="submit" loading={signInLoading}>????????????</Button>
                    {signInSuccess &&(
                        signInUserInformation.nickName != "duplicated user" ?
                            <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                                <p></p>
                                <Alert type="success" message="???????????? ??????!" banner closable={true}/>
                            </div>
                            :
                            <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                                <p></p>
                                <Alert type="error" message="?????? ???????????? ???????????????!" banner closable={true}/>
                            </div>
                    )}
                </div>
            </Form>
        </div>
    );
};

export default SignInForm;
