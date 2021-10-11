import React, {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import * as tf from '@tensorflow/tfjs';
import {setGradImageAction, setPostComponentAction, setResultAction, setUnFoundAction} from "../../reducers";
import {gradClassActivationMap} from "./gradCam/cam"
import {createCanvas} from "canvas";
import {Button} from 'antd';
import {CaretRightOutlined} from '@ant-design/icons';


const ClassifyButton = {
    width : "233px",
    fontWeight: "bold",
};

const modelURL = 'https://cdn.jsdelivr.net/gh/alska95/xray_classifier@master/front/src/components/Content/js_model/model.json';


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


async function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

const Classifier = () => {
    const [classifierOnLoading , setClassifierOnLoading] = useState(0)
    const dispatch = useDispatch();
    const threshold = useSelector((state)=>state.index.threshold);
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
        setClassifierOnLoading(1);
        const model = await tf.loadLayersModel(modelURL);
        const img = await preprocess(imageFile);
        const result = await model.predict(img);

        const rs = await result.toString().replace(/( )|(\[)|\]|(Tensor)/gi,'')
        let resultArray = await rs.split(',');
        await resultArray.pop();

        await dispatch(setResultAction(resultArray));
        console.log(resultArray);
        const target = await checkUnFound(resultArray);
        try{
            gradCam = await gradClassActivationMap(model  , target ,  img);
            gradCam = await ImageTensorToImage(gradCam);
            await dispatch(setGradImageAction(gradCam));
/*            const postComponent = {
                "gradCamImage" : gradCam,
                "originalImage" : img,
                "diagnosisResult" : resultArray
            };*/
            var tmpBlobO = await dataURLtoBlob(imageFile);
            var tmpBlobH = await dataURLtoBlob(gradCam);
            const formData = new FormData();
/*            const gradCamBlob = new Blob([tmpBlobO]);
            const originalBlob = new Blob([urlH], {type:"image/jpg"});*/
            var originalFile = new File([tmpBlobO] , "Original");
            var heatMapFile = new File([tmpBlobH] , "HeatMap");
            formData.append('file', originalFile , "original.jpg");
            formData.append('file', heatMapFile, "gradCam.jpg");
            formData.append('src', "is working");
            formData.append('userNickName' , "hwang"); //수정 필요
            formData.append('result', resultArray);
            console.log("form Data = " + formData);
            await dispatch(setPostComponentAction(formData));
        }catch(err){
            console.error(err);
        }

        setClassifierOnLoading(0);
    }

    return (
        <>
            <div style={{textAlign : "center"}}>
                <Button icon={<CaretRightOutlined />} type = {'dark'} style={ClassifyButton} onClick={()=>startClassify()} loading={classifierOnLoading} >분석 시작</Button>
            </div>
        </>
    );
};

export default Classifier;