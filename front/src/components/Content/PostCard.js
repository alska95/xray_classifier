import React from "react";
import styled from 'styled-components'
import {Avatar, Button, Card, Image} from "antd";
import {useDispatch} from "react-redux";


import {loadPostAction} from "../../reducers";
import Disease from "./Disease";

/*
[{"postId":205,
"userNickName":"hwang",
"originalImageName":"/img/20211011120517original.jpg"
,"heatmapImageName":"/img/20211011120517gradCam.jpg",
"content":""
,"diagnosisResult":"\n0.0342452,0.9891014,0.0088289,0.0015494,0.2037666,0.0155353,0.0124964,0.0077972,0.0700025,0.0085904,0.0319604,0.0224845,0.0026027,0.004623",
"comments":null},*/
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
const PostCard = ({post})=>{
    const dispatch = useDispatch();


    return(
        <div style={{margin : 20 , width : "80%"} }>
            <Card style={{background : "#343a40"}}
                cover={
                    <Card style={{background : "#343a40" , textAlign : "center"}} >
                        <Image style={ImageStyleO} src={process.env.PUBLIC_URL+post.originalImageName}/>
                        <Image style={ImageStyleH} src={process.env.PUBLIC_URL+post.heatmapImageName}/>{
                        getResultArray(post.diagnosisResult).map((v, index)=><Disease factor = {v} index = {index} key = {index}/>
                        )
                    }
                    </Card>

                }
            >
                <Card>
                    <Card.Meta
                        avatar={<Avatar>{post.userNickName}</Avatar>}
                        title={post.userNickName}
                        description={"아야아야아야아야"}
                    />
                </Card>

            </Card>

        </div>
    )
}

export default PostCard;