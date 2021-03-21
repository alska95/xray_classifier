import React, { useState } from 'react';
import styled from 'styled-components';
import images from '../../images/sample';

const style = {};

style.Container = styled.div`
    display: flex;
    padding: 10px;
`;

style.InputImage = styled.input`
    width: 600px;
    height: 600px;
    border: 1px solid #000000;
    margin-right: 10px;
`;

style.OutputImage = styled.img`
    width: 600px;
    height: 600px;
    margin-right: 10px;
`;

style.InfoContainer = styled.div`
    width: 200px;
    height: 600px;
`;

style.Info = styled.div`
    height 300px;
    border: 1px solid #000000;
    padding: 5px;
    margin-bottom: 10px;
`;

style.InfoItem = styled.label`
    display: block;
`;

style.FilterContainer = styled.div`
    height: 300px;
`;

style.FilterItem = styled.input`
    color: black;
`;

let boxChecked = false;
let heatmapChecked = false;

const Content = () => {
    const [image, setImage] = useState("");

    const check = (type) => {
        if(type==="box") boxChecked = !boxChecked;
        if(type==="heatmap") heatmapChecked = !heatmapChecked;

        if(!boxChecked&&!heatmapChecked) setImage(images.chest_default);
        if(boxChecked&&!heatmapChecked) setImage(images.chest_box);
        if(!boxChecked&&heatmapChecked) setImage(images.chest_heatmap);
        if(boxChecked&&heatmapChecked) setImage(images.chest_both);
    }

    console.log(boxChecked + " / " + heatmapChecked);

    const red_label = {
        color: "red",
    };

    const blue_label = {
        color: "blue",
    };

    return (
        <style.Container>
            {image==="" ? (
                <style.InputImage type="file" accept="img/*" onChange={(e) => setImage(images.chest_default)}/>
            ) : (
                <style.OutputImage src={image}/> 
            )}
            {image!=="" && (
                <style.InfoContainer>
                    <style.Info>
                        <style.InfoItem style={red_label}>Pneumonia 82%</style.InfoItem>
                        <style.InfoItem style={blue_label}>Nodule 51%</style.InfoItem>
                    </style.Info>
                    <style.FilterContainer>
                        <style.FilterItem type="checkbox" name="box" onChange={() => check("box")}/>Box
                        <style.FilterItem type="checkbox" name="heatmap" onChange={() => check("heatmap")}/>Heatmap
                    </style.FilterContainer>
                </style.InfoContainer>
            )}
            
        </style.Container>
    );
}

export default Content;