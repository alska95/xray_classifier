import React from "react";
import styled from 'styled-components'
import {Avatar, Button, Card, Image} from "antd";
import {useDispatch} from "react-redux";


import {loadPostAction} from "../../reducers";

/*
* postId(pin):13
userNickName(pin):"hwang"
originalImageName(pin):"I:\programming\xray_classifier\image\Effusion (2).jpg"
heatmapImageName(pin):"I:\programming\xray_classifier\image\Fibrosis (1).jpg"
content(pin):""
diagnosisResult(pin):""
comments(pin):null*/
const ImageStyle = {
    width: "224px",
    height: "224px",
    marginLeft : "28px"
}
const PostCard = ({post})=>{
    const dispatch = useDispatch();


    return(
        <div style={{margin : 20 } }>
            <Card style={{background : "#343a40"}}
                cover={
                    <Card style={{background : "#343a40"}} >
                        <Image style={ImageStyle} src={process.env.PUBLIC_URL+post.originalImageName}/>
                        <Image style={ImageStyle} src={process.env.PUBLIC_URL+post.heatmapImageName}/>
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