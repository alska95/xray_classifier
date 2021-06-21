import React, {useCallback, useRef, useState} from 'react';
import styled from 'styled-components';
import {useDispatch, useSelector} from "react-redux";
import {setGradImageAction, setImageAction, setResultAction, setUnFoundAction} from "../../reducers";
import Classifier from "./Classifier";
import Disease from './Disease'
import { Button , Menu , Dropdown } from 'antd';
import {InboxOutlined , UserOutlined , DownOutlined} from '@ant-design/icons'
import Atelctasis from '../../images/Atelectasis.jpeg'
import Cardiomegaly from '../../images/Cardiomegaly.jpg'
import Effusion from '../../images/Effusion (1).jpeg'



const style = {};

style.Container = styled.div`
    display: flex;
    padding: 10px;
`;

style.InputImage = styled.div`
    width: 600px;
    height: 600px;
    border: 1px solid dimgray;
    margin-right: 10px;
    display: flex;
    flex-direction: column;
`;

style.OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
`;

style.InfoContainer = styled.div`
    width: 233px;
    height: 600px;
`;

style.Info = styled.div`
    width: 233px;
    height: 300px;
    border: 2px solid dimgray;
    padding: 5px;
    margin-bottom: 10px;
`;

style.InfoItem = styled.label`
    display: block;
    
`;

style.FilterContainer = styled.div`
    color: black;
    font-weight: bold;
    height: 50px;
`;

style.FilterItem = styled.input`
    color: dimgray;
`;

const OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
    border: 1px solid dimgray;
    object-fit: cover;
`;

const PressClassify = styled.div`
    font-weight: bold;
    text-align: center;
    margin-top: 100px;
`

let heatmapChecked = false;


const Content = () => {

    const menu = (
        <Menu>
            <Menu.Item key="1" icon={<InboxOutlined />}
                       onClick={()=>onClickChangeImage(Atelctasis)}>
                Atelectasis X-ray
            </Menu.Item>
            <Menu.Item key="2" icon={<InboxOutlined />}
                onClick={()=>onClickChangeImage(Cardiomegaly)}>
                Cardiomegaly X-ray
            </Menu.Item>
            <Menu.Item key="3" icon={<InboxOutlined />}
                onClick={()=>onClickChangeImage(Effusion)}>
                Effusion X-ray
            </Menu.Item>
        </Menu>
    );

    const onClickChangeImage = (img) =>{
        setImage(img);
        dispatch(setImageAction(img));
        dispatch(setResultAction([]));
        dispatch(setGradImageAction([]));
        dispatch(setUnFoundAction(false));
    }


    const [image, setImage] = useState("");
    const imageInput = useRef();

    const gradCamImage = useSelector((state)=>state.index.gradImage);
    const unFoundFlag = useSelector((state)=>state.index.unFoundFlag);
    const result = useSelector((state)=>state.index.result);
    const targetImage = useSelector((state)=>state.index.image);
    const dispatch = useDispatch();


    const handleFileOnChange = (event) => {
        event.preventDefault();
        try{
            let reader = new FileReader();
            let file = event.target.files[0];
            reader.onloadend = () => {
                setImage(reader.result);
                dispatch(setImageAction(reader.result));
            }
            reader.readAsDataURL(file);
            dispatch(setResultAction([]));
            dispatch(setGradImageAction([]));
            dispatch(setUnFoundAction(false));
        }catch(err){
            console.error(err);
        }
    }

    const check = () => {
        heatmapChecked = !heatmapChecked;
        if(!heatmapChecked) setImage(targetImage);
        if(heatmapChecked) setImage(gradCamImage);

    }

    const onClickImageUpload = useCallback(() => {
        imageInput.current.click();
    }, []);

    return (
        <style.Container>
            {image==="" ? (
                <style.InputImage>
                    <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                    <Button icon={<InboxOutlined />} type={"dark"} style={{width : "200px" , margin : "10px" , fontWeight : "bold"}}
                            onClick={onClickImageUpload}>이미지 업로드</Button>

                    <Dropdown overlay={menu}>
                        <Button icon={<InboxOutlined />} style={{width : "200px" , margin : "10px" , fontWeight : "bold"}}>
                            셈플 이미지 <DownOutlined />
                        </Button>
                    </Dropdown>
                </style.InputImage>

            ) : (
                <style.InputImage>
                    <OutputImage src = {image}/>
                </style.InputImage>

            )}
            {image!=="" && (
                <style.InfoContainer>
                    <style.Info>
                        { result[0] &&!unFoundFlag?
                            (
                                result.map((v , index)=>
                                    <Disease factor = {v} index = {index} key = {index}/>
                                )
                            ):
                            (
                                !unFoundFlag ?
                                    <PressClassify>Press Classify</PressClassify>
                                    :
                                    <div style={{fontWeight : "bold"}}>unFound</div>
                            )
                        }

                    </style.Info>
                    {gradCamImage[0] &&
                    <style.FilterContainer>
                         <style.FilterItem type="checkbox" name="heatmap" onChange={()=> check()}/>Heatmap
                    </style.FilterContainer>
                    }

                    <Classifier/>
                    <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                    <Button icon={<InboxOutlined />} type={"dark"} style={{width : "233px" , marginTop : "10px" , fontWeight : "bold"}} onClick={onClickImageUpload}>새로운 이미지 업로드</Button>
                    <Dropdown overlay={menu}>
                        <Button icon={<InboxOutlined />} style={{width : "233px" , marginTop : "10px" , fontWeight : "bold"}}>
                            셈플 이미지 <DownOutlined />
                        </Button>
                    </Dropdown>
                </style.InfoContainer>
            )}

        </style.Container>
    );
}

export default Content;