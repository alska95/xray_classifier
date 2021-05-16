import React, {useCallback, useRef, useState} from 'react';
import styled from 'styled-components';
import images from '../../images/sample';
import {useDispatch, useSelector} from "react-redux";
import {setImageAction} from "../../reducers";
import Classifier from "./Classifier";
import Disease from './Disease'


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
`;


let boxChecked = false;
let heatmapChecked = false;



const Content = () => {
    const imageInput = useRef();
    const onClickImageUpload = useCallback(() => {
        imageInput.current.click();
    }, []);

    const dispatch = useDispatch();
    const result = useSelector((state)=>state.index.result);
    const [image, setImage] = useState("");

    const check = (type) => {
        if(type==="box") boxChecked = !boxChecked;
        if(type==="heatmap") heatmapChecked = !heatmapChecked;

        if(!boxChecked&&!heatmapChecked) setImage(images.chest_default);
        if(boxChecked&&!heatmapChecked) setImage(images.chest_box);
        if(!boxChecked&&heatmapChecked) setImage(images.chest_heatmap);
        if(boxChecked&&heatmapChecked) setImage(images.chest_both);
    }


    const handleFileOnChange = (event) => {
        event.preventDefault();

        let reader = new FileReader();
        let file = event.target.files[0];
        reader.onloadend = () => {
            setImage(reader.result);
            console.log(file);
            dispatch(setImageAction(reader.result));
        }

        reader.readAsDataURL(file);
    }

    return (
        <style.Container>
            {image==="" ? (
                    <style.InputImage>
                        <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                        <button style={{width : "200px" , margin : "10px" , fontWeight : "bold"}} onClick={onClickImageUpload}>이미지 업로드</button>
                    </style.InputImage>
            ) : (
                <style.InputImage>
                    <OutputImage src = {image}/>
                </style.InputImage>

            )}
            {image!=="" && (
                <style.InfoContainer>
                    <style.Info>
                        {result &&
                        result.map((v , index)=>
                            <Disease factor = {v} index = {index} key = {index}/>
                        )}
                    </style.Info>

                    <style.FilterContainer>
                        <style.FilterItem type="checkbox" name="box" onChange={() => check("box")}/>Box
                        <style.FilterItem type="checkbox" name="heatmap" onChange={() => check("heatmap")}/>Heatmap
                    </style.FilterContainer>
                    <Classifier/>
                    <input multiple hidden ref ={imageInput} type="file" accept="img/*" onChange={handleFileOnChange}/>
                    <button style={{width : "200px" , marginTop : "10px" , fontWeight : "bold"}} onClick={onClickImageUpload}>새로운 이미지 업로드</button>
                </style.InfoContainer>
            )}

        </style.Container>
    );
}

export default Content;