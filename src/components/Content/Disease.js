import React, {useCallback, useState} from 'react';
import styled from "styled-components";

const colorStyle = [
    {color: "red"},
    {color: "green"},
    {color: "blue"},
    {color: "gray"},
    {color: "purple"},
    {color: "pink"},
    {color: "black"},
    ]
const label = [
    'Cardiomegaly',
    'Emphysema',
    'Effusion',
    'Hernia',
    'Infiltration',
    'Mass',
    'Nodule',
    'Atelectasis',
    'Pneumothorax',
    'Pleural_Thickening',
    'Pneumonia',
    'Fibrosis',
    'Edema',
    'Consolidation']
const threshold = [
    0.17,
    0.1,
    0.4,
    0.05,
    0.4,
    0.2,
    0.2,
    0.2,
    0.1,
    0.1,
    0.05,
    0.05,
    0.1,
    0.12
];

const DiseaseFactor = styled.div`
  font-weight: bold;
`

const Disease = ({factor , index}) => {
    let unFoundCount = 0;
    const fac = parseFloat(factor);

    const checkThreshold = () =>{
        if(fac > threshold[index]){
            return true;
        }else{
            unFoundCount += 1;
            return false;
        }
    }

    return (
        <>
            { checkThreshold() &&
            <DiseaseFactor style={colorStyle[index%7]}>{label[index]} : {fac}</DiseaseFactor>}
            {unFoundCount >= 14 &&
            <div>unFound</div>}
        </>
    );
};

export default Disease;
