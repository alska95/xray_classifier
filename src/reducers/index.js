import {combineReducers} from "redux";

const initialState = {
    image : [],
    result : [],
    unFoundFlag : false,
    threshold : [
        0.2,
        0.17,
        0.12,
        0.1,
        0.4,
        0.1,
        0.05,
        0.05,
        0.37,
        0.2,
        0.2,
        0.1,
        0.05,
        0.1,
    ],
};

const SET_IMAGE = 'SET_IMAGE';
const SET_RESULT = 'SET_RESULT';
const SET_UNFOUND = 'SET_UNFOUND';

export const setImageAction = (data)=>({
    type:SET_IMAGE,
    data,
})
export const setUnFoundAction = (data)=>({
    type:SET_UNFOUND,
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
            case SET_RESULT:
                return{
                    ...state,
                    result: action.data,
                }
            case SET_UNFOUND:
                return{
                    ...state,
                    unFoundFlag: action.data,
                }
            default:
                return{
                    ...state,
                }
        }
    }

})

export default rootReducer;