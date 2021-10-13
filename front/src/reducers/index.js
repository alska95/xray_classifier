import {combineReducers} from "redux";
import {act} from "@testing-library/react";

const initialState = {
    setLogInForm : true,
    loadPostsError: [],
    loadPostsLoading: false,
    loadPostsDone: true,

    deletePostError : null,
    deletePostLoading:false,
    deletePostDone:true,

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
    logInFailure : false,

    loginCheckError: null,
    loginCheckLoading:false,
    loginCheckDone:true,

    mainPosts : [],
    postComponent :null,
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

export const SET_EMPTY_POST_REQUEST = 'SET_EMPTY_POST';
export const SET_EMPTY_POST_SUCCESS = 'SET_EMPTY_POST_SUCCESS';

export const UPDATE_POST_REQUEST = 'UPDATE_POST_REQUEST';
export const UPDATE_POST_SUCCESS = 'UPDATE_POST_SUCCESS';
export const UPDATE_POST_FAILURE = 'UPDATE_POST_FAILURE';

export const DELETE_POST_REQUEST = 'DELETE_POST_REQUEST';
export const DELETE_POST_SUCCESS = 'DELETE_POST_SUCCESS';
export const DELETE_POST_FAILURE = 'DELETE_POST_FAILURE';

export const LOAD_POSTS_REQUEST = 'LOAD_POSTS_REQUEST';
export const LOAD_POSTS_SUCCESS = 'LOAD_POSTS_SUCCESS';
export const LOAD_POSTS_FAILURE = 'LOAD_POSTS_FAILURE';

export const SIGN_IN_REQUEST = 'SIGN_IN_REQUEST';
export const SIGN_IN_SUCCESS = 'SIGN_IN_SUCCESS';
export const SIGN_IN_FAILURE = 'SIGN_IN_FAILURE';

export const LOG_IN_REQUEST = 'LOG_IN_REQUEST';
export const LOG_IN_SUCCESS = 'LOG_IN_SUCCESS';
export const LOG_IN_FAILURE = 'LOG_IN_FAILURE';

export const LOG_OUT_REQUEST = 'LOG_OUT_REQUEST';
export const LOG_OUT_SUCCESS = 'LOG_OUT_SUCCESS';
export const LOG_OUT_FAILURE = 'LOG_OUT_FAILURE';

export const LOG_IN_CHECK_REQUEST = 'LOG_IN_CHECK_REQUEST';
export const LOG_IN_CHECK_SUCCESS = 'LOG_IN_CHECK_SUCCESS';
export const LOG_IN_CHECK_FAILURE = 'LOG_IN_CHECK_FAILURE';

export const ADD_COMMENT_REQUEST = 'ADD_COMMENT_REQUEST';
export const ADD_COMMENT_SUCCESS = 'ADD_COMMENT_SUCCESS';
export const ADD_COMMENT_FAILURE = 'ADD_COMMENT_FAILURE';

export const DELETE_COMMENT_REQUEST = 'DELETE_COMMENT_REQUEST';
export const DELETE_COMMENT_SUCCESS = 'DELETE_COMMENT_SUCCESS';
export const DELETE_COMMENT_FAILURE = 'DELETE_COMMENT_FAILURE';

export const deleteCommentAction = (data) => ({
    type: DELETE_COMMENT_REQUEST,
    data,
})

export const addCommentAction = (data) => ({
    type : ADD_COMMENT_REQUEST,
    data,
})
export const deletePostAction = (data) => ({
    type : DELETE_POST_REQUEST,
    data
})
export const loginCheckAction = () => ({
    type : LOG_IN_CHECK_REQUEST,
})
export const updatePostAction = (data) =>({
    type : UPDATE_POST_REQUEST,
    data
})
export const logOutAction = () => ({
    type: LOG_OUT_REQUEST
})

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
    type:SET_EMPTY_POST_REQUEST,
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
            case DELETE_COMMENT_FAILURE:
                return{
                    ...state,
                    deleteCommentError: action.err,
                    deleteCommentLoading:false,
                    deleteCommentDone:true,
                }
            case DELETE_COMMENT_SUCCESS:
                return{
                    ...state,
                    deleteCommentLoading:false,
                    deleteCommentDone:true,
                }
            case DELETE_COMMENT_FAILURE:
                return{
                    ...state,
                    deleteCommentError: action.err,
                    deleteCommentLoading:false,
                    deleteCommentDone:true,
                }
            case ADD_COMMENT_FAILURE:
                return{
                    ...state,
                    addCommentError: action.err,
                    addCommentLoading:false,
                    addCommentDone:true,
                }
            case ADD_COMMENT_SUCCESS:
                return{
                    ...state,
                    addCommentLoading:false,
                    addCommentDone:true,
                }
            case ADD_COMMENT_REQUEST:
                return{
                    ...state,
                    addCommentLoading:true,
                    addCommentDone:false,
                }

            case DELETE_POST_FAILURE:
                return{
                    ...state,
                    deletePostError : action.error,
                    deletePostLoading:false,
                    deletePostDone:true,
                }
            case DELETE_POST_SUCCESS:
                return{
                    ...state,
                    deletePostLoading:false,
                    deletePostDone:true,
                }
            case DELETE_POST_REQUEST:
                return{
                    ...state,
                    deletePostLoading:true,
                    deletePostDone:false,
                }
            case LOG_IN_CHECK_FAILURE:
                return{
                    ...state,
                    logInUser: null,
                    loginCheckError: action.error,
                    loginCheckLoading:false,
                    loginCheckDone:true,
                    setLogInForm : true,
                }
            case LOG_IN_CHECK_SUCCESS:
                if(action.data == null){
                    return{
                        ...state,
                        setLogInForm : true,
                        logInUser: action.data,
                        loginCheckLoading:false,
                        loginCheckDone:true,
                    }
                }
                else
                    return{
                        ...state,
                        setLogInForm : false,
                        logInUser: action.data,
                        loginCheckLoading:false,
                        loginCheckDone:true,
                    }

            case LOG_IN_CHECK_REQUEST:
                return{
                    ...state,
                    loginCheckLoading:true,
                    loginCheckDone:false,
                }
            case UPDATE_POST_SUCCESS:
                return{
                    ...state,
                    postComponent :action.data,
                    updatePostLoading: false,
                    updatePostDone: true,
                }
            case UPDATE_POST_REQUEST:
                return{
                    ...state,
                    updatePostLoading: true,
                    updatePostDone: false,
                }
            case UPDATE_POST_FAILURE:
                return{
                    ...state,
                }

            case LOG_OUT_FAILURE:
                return{
                    ...state,
                    logOutError: action.error,
                    logOutLoading: false,
                    logOutDone: true,

                }
            case LOG_OUT_SUCCESS:
                return{
                    ...state,
                    logInUser: null,
                    logOutLoading: false,
                    logOutDone: true,
                    setLogInForm : true,
                }
            case LOG_OUT_REQUEST:
                return{
                    ...state,
                    logOutLoading: true,
                    logOutDone: false,
                }

            case LOG_IN_FAILURE:
                return{
                    ...state,
                    logInUser: null,
                    logInError: action.error,
                    logInLoading: false,
                    logInDone: true,
                    logInFailure: true,
                    setLogInForm : true,
                }
            case LOG_IN_SUCCESS:
                return{
                    ...state,
                    logInUser: action.data,
                    logInLoading: false,
                    logInDone: true,
                    logInFailure: false,
                    setLogInForm : false,
                }
            case LOG_IN_REQUEST:
                return{
                    ...state,
                    logInLoading: true,
                    logInDone: false,
                    logInFailure : false,
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
            case SET_EMPTY_POST_REQUEST:
                return{
                    ...state,
                }
            case SET_EMPTY_POST_SUCCESS:
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