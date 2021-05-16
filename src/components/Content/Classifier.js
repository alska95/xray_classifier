import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import * as tf from '@tensorflow/tfjs';
import styled from 'styled-components';
import { createCanvas} from 'canvas'
import {setResultAction} from "../../reducers";


const OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
`;

/*const modelURL = 'file://I:\\react\\X-ray-Classifie\\src\\components\\Content\\js_model\\model.json'*/
const modelURL = 'https://cdn.jsdelivr.net/gh/stuart-park/temp@main/model/model.json'
/*const modelURL = require('./js_model/model.json')*/
/*https://github.com*/

const Classifier = () => {
    const dispatch = useDispatch();
    let imageFile = useSelector((state)=> state.index.image);
    console.log(imageFile);
    let im = new Image();

    async function preprocess(img)
    {
        im.src = img;
        im.width = 224;
        im.height = 224;

        let tensor = await tf.browser.fromPixels(im).toFloat();

        const offset = await tf.scalar(127.5);
        // Normalize the image
        const normalized = await tensor.sub(offset).div(offset);

        //We add a dimension to get a batch shape [1,224,224,3]
        const batched = await normalized.expandDims(0)
        return batched
    }


    const startClassify = async ()=>{
        const model = await tf.loadLayersModel(modelURL);
        const img = await preprocess(imageFile);
        const result = await model.predict(img);
        await result.print();

        console.log(result);
    }
/*    const showResult = ()=>{
        dispatch(setResultAction(result));
    }*/
    return (
        <>
            <OutputImage src = {imageFile}/>
            <div>
                <button onClick={startClassify} >분석 시작</button>
            </div>
        </>
    );
};

export default Classifier;
