import React, {useCallback, useRef, useState} from 'react';
import styled from 'styled-components';
import {useDispatch, useSelector} from "react-redux";
import {setImageAction} from "../../reducers";
import Classifier from "./Classifier";
import Disease from './Disease'
import { Button } from 'antd';



const style = {};

style.Container = styled.div`
    display: flex;
    padding: 10px;
`;

style.InputImage = styled.div`
    width: 600px;
    height: 600px;
    border: 1px solid #000000;
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
    width: 200px;
    height: 600px;
`;

style.Info = styled.div`
    width: 222px;
    height: 300px;
    border: 1px solid #000000;
    padding: 5px;
    margin-bottom: 10px;
`;

style.InfoItem = styled.label`
    display: block;
`;

style.FilterContainer = styled.div`
    height: 50px;
`;

style.FilterItem = styled.input`
    color: black;
`;

const OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
    border: 1px solid black;
    object-fit: cover;
`;



let heatmapChecked = false;



const Content = () => {
    const [loading , setLoading] = useState(0);
    const imageInput = useRef();
    const onClickImageUpload = useCallback(() => {
        imageInput.current.click();
    }, []);
    const gradCamImage = useSelector((state)=>state.index.gradImage);
    const unFoundFlag = useSelector((state)=>state.index.unFoundFlag);
    const dispatch = useDispatch();
    const result = useSelector((state)=>state.index.result);
    const [image, setImage] = useState("");

    const targetImage = useSelector((state)=>state.index.image);
    const check = (type) => {
        if(type==="heatmap") heatmapChecked = !heatmapChecked;
        if(!heatmapChecked) setImage(targetImage);
        if(heatmapChecked) setImage(gradCamImage);

    }


    const handleFileOnChange = (event) => {
        event.preventDefault();
        setLoading(1);

        let reader = new FileReader();
        let file = event.target.files[0];
        reader.onloadend = () => {
            setImage(reader.result);
            console.log(reader.result);
            dispatch(setImageAction(reader.result));
        }

        reader.readAsDataURL(file);
        setLoading(0);
    }

    return (
        <style.Container>
            {image==="" ? (
                    <style.InputImage>
                        <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                        <Button type={"dark"} style={{width : "200px" , margin : "10px" , fontWeight : "bold"}} loading={loading}
                                onClick={onClickImageUpload}>이미지 업로드</Button>
                    </style.InputImage>
            ) : (
                <style.InputImage>
                    <OutputImage src = {image}/>
                </style.InputImage>

            )}
            {image!=="" && (
                <style.InfoContainer>
                    <style.Info>
                        {!unFoundFlag && result[0] ?
                            (
                                result.map((v , index)=>
                                    <Disease factor = {v} index = {index} key = {index}/>
                                )
                            ):
                            (
                                <div style={{fontWeight : "bold"}}>unFound</div>
                            )
                        }

                    </style.Info>
                    {result[0] &&
                    <style.FilterContainer>
                        <style.FilterItem type="checkbox" name="heatmap" onChange={() => check("heatmap")}/>heatmap
                    </style.FilterContainer>
                    }

                    <Classifier/>
                    <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                    <Button type={"lightdark"} style={{width : "233px" , marginTop : "10px" , fontWeight : "bold"}} onClick={onClickImageUpload}
                            loading={loading}>새로운 이미지 업로드</Button>
                </style.InfoContainer>
            )}

        </style.Container>
    );
}

export default Content;