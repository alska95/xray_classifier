import React, { useCallback, useState } from 'react';
import {Alert, Form, Input, Checkbox, Button, Card, Image} from 'antd';
import styled from 'styled-components';
import {useDispatch, useSelector} from "react-redux";

import {loadPostAction, SIGN_IN_REQUEST, signInAction, updatePostAction} from '../../reducers/index';
import Disease from "./Disease";
import {SaveOutlined} from "@ant-design/icons"

const ErrorMessage = styled.div`
  color: red;
`;

const ImageStyleO = {
    width: "224px",
    height: "224px",
}
const ImageStyleH = {
    width: "224px",
    height: "224px",
    marginLeft: "20px"
}

function getResultArray(diagnosisResult){
    diagnosisResult.replace("\\n" , " ");
    var resultArray = diagnosisResult.split(",");
    console.log(resultArray)
    return resultArray;
}

const PostEditForm = ({post}) => {
    const useInput = (initValue = null) => {
        const [value, setter] = useState(initValue);
        const handler = useCallback((e) => {
            setter(e.target.value);
        }, []);
        return [value, handler, setter];
    };

    const dispatch = useDispatch();


    const [content, onChangecontent] = useInput('');
    const [savedStatus, setSavedStatus] = useState(false);

    const onSubmit = useCallback(() => {
        setSavedStatus(true);
        console.log(content)
        dispatch(updatePostAction({
            "content" : content,
            "userNickName" : post.userNickName,
            "originalImageName" : post.originalImageName,
            "diagnosisResult" : post.diagnosisResult
        }));
        dispatch(loadPostAction());
    }, [content, savedStatus]);


    /*
[{"postId":205,
"userNickName":"hwang",
"originalImageName":"/img/20211011120517original.jpg"
,"heatmapImageName":"/img/20211011120517gradCam.jpg",
"content":""
,"diagnosisResult":"\n0.0342452,0.9891014,0.0088289,0.0015494,0.2037666,0.0155353,0.0124964,0.0077972,0.0700025,0.0085904,0.0319604,0.0224845,0.0026027,0.004623",
"comments":null},*/

    return (
        <div>
            <Form onFinish={onSubmit}>
                <Card style={{ textAlign : "center"}} >
                    <Image style={ImageStyleO} src={process.env.PUBLIC_URL+post.originalImageName}/>
                    <Image style={ImageStyleH} src={process.env.PUBLIC_URL+post.heatmapImageName}/>
                    {
                        getResultArray(post.diagnosisResult).map((v, index)=><Disease factor = {v} index = {index} key = {index}/>)
                    }
                </Card>
                <div>
                    <p></p>
                    <label htmlFor="user-content" style={{fontWeight : "bold"}} placeholder={"내용을 입력하세요."}>분석 결과에 대한 소감을 수정해 주세요!</label>
                    <br />
                    <Input style={{height : "85px"}} name="user-content" type="content" value={content} required onChange={onChangecontent} />
                </div>
                <p></p>
                <Button htmlType="submit" type={"dark"} > <SaveOutlined /> 저장</Button>
                {savedStatus &&
                <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                    <p></p>
                    <Alert type="success" message="수정되었습니다! 새로 고침하면 결과가 반영됩니다" banner />
                </div>}

            </Form>
        </div>
    );
};

export default PostEditForm;
