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
                    <label htmlFor="user-email">이메일</label>
                    <br />
                    <Input name="user-email" type="email" value={email} required onChange={onChangeEmail} />
                </div>
                <div>
                    <label htmlFor="user-nick">아이디</label>
                    <br />
                    <Input name="user-nick" value={nickname} required onChange={onChangeNickName} />
                    <div style={{color: "red"}}>
                        {signInError && "중복되는 아이디 입니다."}
                    </div>
                </div>
                <div>
                    <label htmlFor="user-password">비밀번호</label>
                    <br />
                    <Input name="user-password" type="password" value={password} required onChange={onChangePassword}
                           placeholder={"비밀번호는 암호화 되어 저장됩니다."} />
                </div>
                <div>
                    <label htmlFor="user-password-check"
                           placeholder={"비밀번호는 암호화 되어 저장됩니다."}>비밀번호체크</label>
                    <br />
                    <Input
                        name="user-password-check"
                        type="password"
                        value={passwordCheck}
                        required
                        onChange={onChangePasswordCheck}
                    />
                    {passwordError && <ErrorMessage>비밀번호가 일치하지 않습니다.</ErrorMessage>}
                </div>
                <div>
                    <p></p>
                    <Checkbox name="user-term" checked={term} onChange={onChangeTerm} style={{fontWeight:  "bold"}}>
                        분석에 사용되는 이미지는 저장되며, 재사용 될 수 있음에 동의합니다.</Checkbox>
                    {termError && <ErrorMessage>약관에 동의하셔야 합니다.</ErrorMessage>}
                </div>
                <div style={{ marginTop: 10 }}>
                    <Button type="primary" htmlType="submit" loading={signInLoading}>가입하기</Button>
                    {signInSuccess &&(
                        signInUserInformation.nickName != "duplicated user" ?
                            <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                                <p></p>
                                <Alert type="success" message="회원가입 완료!" banner closable={true}/>
                            </div>
                            :
                            <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                                <p></p>
                                <Alert type="error" message="유저 아이디가 중복됩니다!" banner closable={true}/>
                            </div>
                    )}
                </div>
            </Form>
        </div>
    );
};

export default SignInForm;
