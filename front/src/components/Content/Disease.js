import React from 'react';
import styled from "styled-components";
import {useSelector} from "react-redux";

const colorStyle = [
    {color: "lightsalmon"},
    {color: "green"},
    {color: "blue"},
    {color: "lightseagreen"},
    {color: "purple"},
    {color: "pink"},
    {color: "white"},
]

const label = [
    'Atelectasis',
    'Cardiomegaly',
    'Consolidation',
    'Edema',
    'Effusion',
    'Emphysema',
    'Fibrosis',
    'Hernia',
    'Infiltration',
    'Mass',
    'Nodule',
    'Pleural_Thickening',
    'Pneumonia',
    'Pneumothorax',
]

const DiseaseFactor = styled.div`
  font-weight: bold;
`


const Disease = ({factor , index}) => {
    const threshold = useSelector((state) => state.index.threshold);
    const fac = parseFloat(factor);

    const checkThreshold = () =>{
        return fac > threshold[index];
    }

    return (
        <>
            {checkThreshold() &&
            <DiseaseFactor style={colorStyle[index%7]}>{label[index]} : {fac}</DiseaseFactor>}
        </>
    );
};

export default Disease;