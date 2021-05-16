import {combineReducers} from "redux";

const initialState = {
    image : [],
    binaryImage : [],
    result : [],
};

const SET_IMAGE = 'SET_IMAGE';
const SET_BINARY_IMAGE = 'SET_BINARY_IMAGE';
const SET_RESULT = 'SET_RESULT'

export const setImageAction = (data)=>({
    type:SET_IMAGE,
    data,
})

export const setBinaryImageAction = (data)=>({
    type:SET_BINARY_IMAGE,
    data,
})

export const setResultAction = (data)=>({
    type:SET_RESULT,
    data,
})


const rootReducer = combineReducers({
    index:(state=initialState , action)=>{
        switch(action.type){
            case SET_IMAGE:
                return{
                    ...state,
                    image: action.data,
                }
            case SET_BINARY_IMAGE:
                return{
                    ...state,
                    binaryImage: action.data,
                }
            case SET_RESULT:
                return{
                    ...state,
                    result: action.data,
                }
            default:
                return{
                    ...state,
                }
        }
    }

})

export default rootReducer;