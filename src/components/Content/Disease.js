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

const Disease = ({factor , index}) => {
    const threshold = 0.4;
    const fac = parseFloat(factor);
    return (
        <>
            {fac > threshold &&
            <div>{label[index]} : {factor}</div>}
        </>
    );
};

export default Disease;
