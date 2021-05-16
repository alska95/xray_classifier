import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import * as tf from '@tensorflow/tfjs';
import styled from 'styled-components';
import {setResultAction} from "../../reducers";

const ClassifyButton = styled.button`
    width: 200px;
    height: 100px;
    font-size: 40px;
    font-weight: bolder;
`;

const modelURL = 'https://cdn.jsdelivr.net/gh/stuart-park/temp@main/model/model.json'

const Classifier = () => {
    const dispatch = useDispatch();
    let imageFile = useSelector((state)=> state.index.image);
    let im = new Image();
    let resultArray;

    async function preprocess(img)
    {
        im.src = img;
        im.width = 224;
        im.height = 224;

        let tensor = await tf.browser.fromPixels(im).toFloat();

        //We add a dimension to get a batch shape [1,224,224,3]
        const batched = await tensor.expandDims(0)
        return batched
    }


    const startClassify = async ()=>{
        const model = await tf.loadLayersModel(modelURL);
        const img = await preprocess(imageFile);
        const result = await model.predict(img);
        console.log(result.toString());

        const rs = await result.toString().replace(/( )|(\[)|\]|(Tensor)/gi,'')
        resultArray = rs.split(',');
        resultArray.pop();

        dispatch(setResultAction(resultArray));
        console.log(resultArray);
    }

    return (
        <>
            <div style={{textAlign : "center"}}>
                <ClassifyButton onClick={startClassify} >분석 시작</ClassifyButton>
            </div>
        </>
    );
};

export default Classifier;
