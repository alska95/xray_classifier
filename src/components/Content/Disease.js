import React from 'react';


const label = ['Cardiomegaly',
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

const Disease = ({factor , index}) => {
    let unFoundCount = 0;
    const checkThreshold = () =>{
        if(fac > threshold[index]){
            return true;
        }else{
            unFoundCount += 1;
            return false;
        }
    }
    const fac = parseFloat(factor);
    return (
        <>
            { checkThreshold() &&
            <div>{label[index]} : {factor}</div>}
            {unFoundCount >= 14 &&
            <div>unFound</div>}
        </>
    );
};

export default Disease;
