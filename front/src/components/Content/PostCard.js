import React, {useState} from "react";
import styled from 'styled-components'
import {Alert, Avatar, Button, Card, Image, Popover} from "antd";
import {useDispatch, useSelector} from "react-redux";


import {deletePostAction, loadPostAction} from "../../reducers";
import Disease from "./Disease";
import {FileAddOutlined , DeleteOutlined, CommentOutlined} from "@ant-design/icons";
import PostCreateForm from "./PostCreateForm";
import PostEditForm from "./PostEditForm";
import CommentEditForm from "./CommentEditForm";
import CommentCard from "./CommentCard";

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
    const logInUser = useSelector((state)=>state.index.logInUser)
    const [deletedStatus, setDeletedStatus] = useState(false);
    /*
    * nickName(pin):"hwang"
email(pin):"abc@naver.com"*/


    const onClickDelete = () =>{
        setDeletedStatus(true);
        dispatch(deletePostAction(post))
        setTimeout(function (){dispatch(loadPostAction())}, 200);
    }
    return(
        <div style={{margin : 20 , width : "100%"} }>
            <Card style={{background : "#343a40"}}
                cover={
                    <Card style={{background : "#343a40" , textAlign : "center"}} >
                        <Image style={ImageStyleO} src={process.env.PUBLIC_URL+post.originalImageName}/>
                        <Image style={ImageStyleH} src={process.env.PUBLIC_URL+post.heatmapImageName}/>
                        {
                            getResultArray(post.diagnosisResult).map((v, index)=><Disease factor = {v} index = {index} key = {index}/>
                            )
                        }
                    </Card>

                }
            >
                <Card>
                    <Card.Meta
                        avatar={<Avatar size={64} style={{backgroundColor : "#343a40"}}>{post.userNickName}</Avatar>}
                        title={post.userNickName}
                        description={post.content == "" ?
                        "소감을 입력해 주세요!":
                        post.content}
                    />
                </Card>
                {logInUser != null && logInUser.nickName == post.userNickName &&
                <Popover placement="topRight" title={<div><FileAddOutlined /> 게시물 수정</div>} content={<PostEditForm post = {post}/>} trigger="click">
                    <Button style={{fontWeight: "bold" ,marginTop : "10px"}}>게시물 수정</Button>
                </Popover>}

                {logInUser != null &&logInUser.nickName == post.userNickName &&
                <Popover placement="topRight"
                         content={
                             <div>
                                 <Button type={"danger"} onClick={onClickDelete}><DeleteOutlined />
                                     정말 삭제 하시겠습니까?
                                 </Button>
                                 {deletedStatus &&
                                 <div style={{fontWeight: "bold" ,fontSize : "20px"}}>
                                     <p></p>
                                     <Alert type="error" message="삭제 되었습니다!" banner/>
                                 </div>
                                 }
                             </div>

                         }
                         trigger="click">

                    <Button style={{fontWeight: "bold" ,marginTop : "10px"}}>게시물 삭제</Button>
                </Popover>}
                {logInUser != null &&
                <Popover placement="topRight" title={<div><CommentOutlined /> 댓글 달기</div>} content={<CommentEditForm post = {post}/>} trigger="click">
                    <Button style={{fontWeight: "bold" ,marginTop : "10px"}}> 댓글 달기</Button>
                </Popover>}



            </Card>
            {post.comments !=null &&
                post.comments.map((v)=><CommentCard comment={v}/>)}
        </div>
    )
}

export default PostCard;