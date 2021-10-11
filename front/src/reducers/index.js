import {combineReducers} from "redux";
import {act} from "@testing-library/react";

const initialState = {
    loadPostsError: [],
    loadPostsLoading: false,
    loadPostsDone: true,

    signInUserInformation: null,
    signInError: false,
    signInLoading: false,
    signInDone: false,
    signInSuccess : false,

    logInUser:null,
    /*
    {nickName:'hwang',email:'abc@naver.com'}
    * */
    logInError: false,
    logInLoading: false,
    logInDone: false,

    mainPosts : [],
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

export const SET_EMPTY_POST = 'SET_EMPTY_POST';
export const SET_EMPTY_POST_SUCCESS = 'SET_EMPTY_POST_SUCCESS'; //구현 예정

export const LOAD_POSTS_REQUEST = 'LOAD_POSTS_REQUEST';
export const LOAD_POSTS_SUCCESS = 'LOAD_POSTS_SUCCESS';
export const LOAD_POSTS_FAILURE = 'LOAD_POSTS_FAILURE';

export const SIGN_IN_REQUEST = 'SIGN_IN_REQUEST';
export const SIGN_IN_SUCCESS = 'SIGN_IN_SUCCESS';
export const SIGN_IN_FAILURE = 'SIGN_IN_FAILURE';

export const LOG_IN_REQUEST = 'LOG_IN_REQUEST';
export const LOG_IN_SUCCESS = 'LOG_IN_SUCCESS';
export const LOG_IN_FAILURE = 'LOG_IN_FAILURE';

export const logInAction = (data) => ({
    type: LOG_IN_REQUEST,
    data,
})

export const loadPostAction = () => ({
    type:LOAD_POSTS_REQUEST,
})

export const signInAction = (data) => ({
    type:SIGN_IN_REQUEST,
    data,
})
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
            case LOG_IN_FAILURE:
                return{
                    ...state,
                    logInError: action.error,
                    logInLoading: false,
                    logInDone: true,
                }
            case LOG_IN_SUCCESS:
                return{
                    ...state,
                    logInUser: action.data,
                    logInLoading: false,
                    logInDone: true,
                }
            case LOG_IN_REQUEST:
                return{
                    ...state,
                    logInLoading: true,
                    logInDone: false,
                }

            case SIGN_IN_SUCCESS:
                return{
                    ...state,
                    signInUserInformation: action.data,
                    signInLoading: false,
                    signInDone: true,
                    signInError: false,
                    signInSuccess: true,
                }
            case SIGN_IN_REQUEST:
                return{
                    ...state,
                    signInLoading: true,
                    signInDone: false,
                    signInSuccess: false,
                }
            case SIGN_IN_FAILURE:
                return{
                    ...state,
                    signInError: action.error,
                    signInLoading: false,
                    signInDone: true,
                    signInSuccess: false,
                }
            case LOAD_POSTS_SUCCESS:
                return{
                    ...state,
                    loadPostsLoading : false,
                    loadPostsDone : true,
                    mainPosts: action.data,
                }
            case LOAD_POSTS_REQUEST:
                return{
                    ...state,
                    loadPostsLoading: true,
                    loadPostsDone: false,
                }
            case LOAD_POSTS_FAILURE:
                return{
                    ...state,
                    loadPostsError: action.error,
                    loadPostsLoading: false,
                    loadPostsDone: true,
                }
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