import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import * as tf from '@tensorflow/tfjs';
import styled from 'styled-components';
import {setGradImageAction, setResultAction, setUnFoundAction} from "../../reducers";
import {gradClassActivationMap} from "./gradCam/cam"
import {createCanvas} from "canvas";

const ClassifyButton = styled.button`
    width: 233px;
    height: 100px;
    font-size: 40px;
    font-weight: bolder;
`;

const OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
    border: 1px solid black;
`;

const modelURL = 'https://cdn.jsdelivr.net/gh/stuart-park/temp@main/model/model.json'

async function ImageTensorToImage(imageTensor) {
    var canvas = createCanvas(224, 224 );

    const ctx = canvas.getContext('2d');
    const imageH = imageTensor.shape[1];
    const imageW = imageTensor.shape[2];

    const imgdata = new ImageData(imageH , imageW);

    const imageData = imageTensor.dataSync();

    const bufferLen = imageH * imageW * 4;
    const buffer = new Uint8Array(bufferLen);
    let index = 0;
    for (let i = 0; i < imageH; ++i) {
        for (let j = 0; j < imageW; ++j) {
            const inIndex = 3 * (i * imageW + j);
            buffer.set([Math.floor(imageData[inIndex])], index++);
            buffer.set([Math.floor(imageData[inIndex + 1])], index++);
            buffer.set([Math.floor(imageData[inIndex + 2])], index++);
            buffer.set([255], index++);
        }
    }
    imgdata.data.set(buffer);
    ctx.putImageData(imgdata , 0 , 0)

    return canvas.toDataURL("image/png")
}

async function preprocess(img)
{
    let im = new Image();
    im.src = img;
    im.width = 224;
    im.height = 224;

    let tensor = await tf.browser.fromPixels(im).toFloat();
    //We add a dimension to get a batch shape [1,224,224,3]
    const batched = await tensor.expandDims(0)
    return batched
}



const Classifier = () => {

    const dispatch = useDispatch();
    const threshold = useSelector((state)=>state.index.threshold);
    const gradCamImage = useSelector((state)=>state.index.gradImage);
    let imageFile = useSelector((state)=> state.index.image);
    let gradCam = "";


    const checkUnFound = async (ra) => {
        let target = [];
        dispatch(setUnFoundAction(true));
        for(let i = 0 ; i < 14 ; i++){
            if(ra[i] > threshold[i]) {
                target.push(i);
                dispatch(setUnFoundAction(false));
            }
        }
        return target;
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
        const target = await checkUnFound(resultArray);

        gradCam = await gradClassActivationMap(model  , target ,   img);
        gradCam = await ImageTensorToImage(gradCam);
        await dispatch(setGradImageAction(gradCam));
    }

    return (
        <>
            <div style={{textAlign : "center"}}>
                <ClassifyButton onClick={startClassify} >분석 시작</ClassifyButton>
            </div>
{/*            {gradCamImage && <OutputImage src={gradCamImage}/>}*/}
        </>
    );
};

export default Classifier;