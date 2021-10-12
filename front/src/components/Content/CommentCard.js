import React, {useState} from "react";
import {Alert, Avatar, Button, Card, Popover} from "antd";
import {CommentOutlined, DeleteOutlined} from "@ant-design/icons";
import {deleteCommentAction, deletePostAction} from "../../reducers";
import {useDispatch, useSelector} from "react-redux";


const CommentCard = ({comment}) =>{
    const dispatch = useDispatch();
    const [deletedStatus , setDeletedStatus] = useState();

    const logInUser = useSelector((state)=>state.index.logInUser);

    const onClickDelete = () =>{
        setDeletedStatus(true);
        dispatch(deleteCommentAction(comment))
    }

    return (
        <>
            <Card>
                <Card.Meta
                    avatar={<Avatar size={32} style={{backgroundColor : "#343a40"}}>{comment.userNickName}</Avatar>}
                    description={comment.content == "" ?
                        "댓글을 입력해 주세요!"
                        :
                        <div style={{fontWeight: "bold" , color : "black"}}>
                            <CommentOutlined />{" " +comment.content}
                        </div>
                    }
                />
                <Popover placement="topRight"
                         content={
                             <div>
                                 <Button type={"danger"} onClick={onClickDelete}><DeleteOutlined />
                                     정말 삭제 하시겠습니까?
                                 </Button>
                                 {deletedStatus &&
                                 <div style={{fontWeight: "bold" ,fontSize : "20px" }}>
                                     <p></p>
                                     <Alert type="error" message="삭제 되었습니다!" banner/>
                                 </div>
                                 }
                             </div>

                         }
                         trigger="click">

                    <Button style={{fontWeight: "bold"  , marginLeft : "90%"}}>댓글 삭제</Button>
                </Popover>
            </Card>

        </>
    )
}

export default CommentCard;