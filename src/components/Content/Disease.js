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

    const fac = parseFloat(factor);

    return (
        <>
            {fac > threshold[index] &&
            <div>{label[index]} : {factor}</div>}
        </>
    );
};

export default Disease;
