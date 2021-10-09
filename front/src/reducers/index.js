import {combineReducers} from "redux";

const initialState = {
    postComponent :[],
    image : [],
    result : [],
    gradImage : [],
    unFoundFlag : false,
    threshold : [
        0.4,
        0.35,
        0.35,
        0.3,
        0.45,
        0.3,
        0.3,
        0.3,
        0.45,
        0.4,
        0.4,
        0.35,
        0.35,
        0.35,
    ],
};

export const SET_IMAGE = 'SET_IMAGE';
export const SET_RESULT = 'SET_RESULT';
export const SET_UNFOUND = 'SET_UNFOUND';
export const SET_GRAD_IMAGE = 'SET_GRAD_IMAGE';



export const SET_EMPTY_POST = 'SET_GRAD_IMAGE';
export const SET_EMPTY_POST_SUCCESS = 'SET_EMPTY_POST_SUCCESS'; //구현 예정

export const setPostComponentAction = (data) =>({
    type:SET_EMPTY_POST,
    data,
})

export const setImageAction = (data)=>({
    type:SET_IMAGE,
    data,
})
export const setGradImageAction = (data)=>({
    type:SET_GRAD_IMAGE,
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
            case SET_EMPTY_POST:
                return{
                    ...state,
                    postComponent: action.data,
                }
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
            case SET_GRAD_IMAGE:
                return{
                    ...state,
                    gradImage: action.data,
                }
            default:
                return{
                    ...state,
                }
        }
    }

})

export default rootReducer;