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

const PostCreateForm = () => {
    const useInput = (initValue = null) => {
        const [value, setter] = useState(initValue);
        const handler = useCallback((e) => {
            setter(e.target.value);
        }, []);
        return [value, handler, setter];
    };

    const dispatch = useDispatch();
    const postComponent = useSelector((state) => state.index.postComponent);


    const [content, onChangecontent] = useInput('');
    const [savedStatus, setSavedStatus] = useState(false);

    const onSubmit = async () => {
        setSavedStatus(true);
        console.log(content)
        await dispatch(updatePostAction({
            "content" : content,
            "userNickName" : postComponent.userNickName,
            "originalImageName" : postComponent.originalImageName,
            "diagnosisResult" : postComponent.diagnosisResult
        }));
        setTimeout(function (){dispatch(loadPostAction())}, 200);
    };


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
                    <Image style={ImageStyleO} src={process.env.PUBLIC_URL+postComponent.originalImageName}/>
                    <Image style={ImageStyleH} src={process.env.PUBLIC_URL+postComponent.heatmapImageName}/>{
                    getResultArray(postComponent.diagnosisResult).map((v, index)=><Disease factor = {v} index = {index} key = {index}/>)
                }
                </Card>
                <div>
                    <p></p>
                    <label htmlFor="user-content" style={{fontWeight : "bold"}} placeholder={"내용을 입력하세요."}>분석 결과에 대한 소감을 입력해 주세요! 언제든 수정, 삭제 가능합니다.</label>
                    <br />
                    <Input style={{height : "85px"}} name="user-content" type="content" value={content} required onChange={onChangecontent} />
                </div>
                <p></p>
                <Button htmlType="submit" type={"dark"} > <SaveOutlined /> 저장</Button>
                {savedStatus &&
                <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                    <p></p>
                    <Alert type="success" message="저장 되었습니다!" banner />
                </div>}

            </Form>
        </div>
    );
};

export default PostCreateForm;
