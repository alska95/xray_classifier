import React from 'react';
import styled from 'styled-components';

const style = {};

style.Header = styled.header`
    display: flex;
    padding: 10px;
    background-color: #343a40;
`;

style.Title = styled.label`
    font-size: 20px;
    font-weight: bold;
    color: #ffffff;
`;

const Header = () => {
    return (
        <style.Header>
            <style.Title>X-ray Classifier</style.Title>
        </style.Header>
    )
};

export default Header;