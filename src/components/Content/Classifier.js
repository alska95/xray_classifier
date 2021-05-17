import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import * as tf from '@tensorflow/tfjs';
import styled from 'styled-components';
import {setResultAction, setUnFoundAction} from "../../reducers";

const ClassifyButton = styled.button`
    width: 233px;
    height: 100px;
    font-size: 40px;
    font-weight: bolder;
`;

const modelURL = 'https://cdn.jsdelivr.net/gh/stuart-park/temp@main/model/model.json'

const Classifier = () => {
    const dispatch = useDispatch();
    const threshold = useSelector((state)=>state.index.threshold);
    let imageFile = useSelector((state)=> state.index.image);


    async function preprocess(img)
    {
        let im = await new Image();
        im.src = img;
        im.width = 224;
        im.height = 224;

        let tensor = await tf.browser.fromPixels(im).toFloat();
        //We add a dimension to get a batch shape [1,224,224,3]
        const batched = await tensor.expandDims(0)
        return batched
    }

    const checkUnFound = (ra) => {
        dispatch(setUnFoundAction(true));
        for(let i = 0 ; i < 14 ; i++){
            if(ra[i] > threshold[i]) {
                dispatch(setUnFoundAction(false));
            }
        }
    }

    const startClassify = async ()=>{
        const model = await tf.loadLayersModel(modelURL);
        const img = await preprocess(imageFile);
        const result = await model.predict(img);

        console.log(result.toString());

        const rs = await result.toString().replace(/( )|(\[)|\]|(Tensor)/gi,'')
        let resultArray = await rs.split(',');
        resultArray.pop();

        dispatch(setResultAction(resultArray));
        console.log(resultArray);
        await checkUnFound(resultArray);
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
