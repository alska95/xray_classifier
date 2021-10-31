import axios from 'axios';
import {all,fork,take,put ,call} from 'redux-saga/effects';
import {takeLatest} from "@redux-saga/core/effects";
import {
    SET_EMPTY_POST_REQUEST,
    SET_EMPTY_POST_SUCCESS,
    LOAD_POSTS_REQUEST,
    LOAD_POSTS_SUCCESS,
    LOAD_POSTS_FAILURE,
    SIGN_IN_REQUEST,
    SIGN_IN_SUCCESS,
    SIGN_IN_FAILURE,
    LOG_IN_SUCCESS,
    LOG_IN_FAILURE,
    LOG_IN_REQUEST,
    LOG_OUT_SUCCESS,
    LOG_OUT_FAILURE,
    LOG_OUT_REQUEST,
    UPDATE_POST_SUCCESS,
    UPDATE_POST_FAILURE,
    UPDATE_POST_REQUEST,
    LOG_IN_CHECK_SUCCESS,
    LOG_IN_CHECK_FAILURE,
    LOG_IN_CHECK_REQUEST,
    DELETE_POST_SUCCESS,
    DELETE_POST_FAILURE,
    DELETE_POST_REQUEST,
    ADD_COMMENT_SUCCESS, ADD_COMMENT_FAILURE, ADD_COMMENT_REQUEST, DELETE_COMMENT_SUCCESS, DELETE_COMMENT_REQUEST
} from "../reducers/index"
/*http://3.144.200.16:8000*/
axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true;

function deleteCommentAPI(data){
    return axios.delete('/comment/id/' + data);
}
function* deleteComment(action){
    try{
        yield call(deleteCommentAPI, action.data);
        yield put({
            type:DELETE_COMMENT_SUCCESS,
        })
    }catch (err){
        yield put({
            type:DELETE_POST_FAILURE,
        })
    }
}
function addCommentAPI(data){
    return axios.post('/comment', data)
}
function* addComment(action){
    try{
        const result = yield call(addCommentAPI, action.data);
        yield put({
            type:ADD_COMMENT_SUCCESS,
        })
    }catch (err){
        yield put({
            type:ADD_COMMENT_FAILURE,
        })
    }
}
function deletePostAPI(data){
    return axios.delete('/post/id/' + data.postId)
}

function* deletePost(action){
    try{
        const result = yield call(deletePostAPI, action.data);
        yield put({
            type: DELETE_POST_SUCCESS,
        })
    }catch (err){
        yield put({
            type: DELETE_POST_FAILURE,
        })
    }
}
function loginCheckAPI(){
    return axios.get('/login-status')
}
function* loginCheck(action){
    try{
        const result = yield call(loginCheckAPI, action.data);
        if(result.data == ""){
            yield put({
                type:LOG_IN_CHECK_SUCCESS,
                data:null,
            })
        }else{
            yield put({
                type:LOG_IN_CHECK_SUCCESS,
                data:result.data,
            })
        }
    }catch (err){
        yield put({
            type: LOG_IN_CHECK_FAILURE,
            data:err,
        })
    }
}
function updatePostAPI(data){
    return axios.put('/post', data , {
        headers: new Headers({
            'Content-Type': 'application/json',
        })
    })
}
function* updatePost(action){
    try{
        const result = yield call(updatePostAPI, action.data );
        yield put({
            type:UPDATE_POST_SUCCESS,
            data:result.data,
        })
    }catch (err){
        yield put({
            type:UPDATE_POST_FAILURE,
            data:err.response.data,
        })
    }
}
function logoutAPI(data){
    return axios.get('/user/logout', data)
}
function* logout(){
    try{
        yield call(logoutAPI);
            yield put({
                type:LOG_OUT_SUCCESS,
            })
    }catch (err){
        yield put({
            type:LOG_OUT_FAILURE,
            data:err.response.data,
        })
    }
}

function loginAPI(data){
    return axios.post('/user/login', data)
}
function* login(action){
    try{
        const result = yield call(loginAPI, action.data);
        if(result.data == null || result.data == ''){
            yield put({
                type:LOG_IN_FAILURE,
                data:result.data,
            })
        }else{
            yield put({
                type:LOG_IN_SUCCESS,
                data:result.data,
            })
        }


    }catch (err){
        yield put({
            type:LOG_IN_FAILURE,
            data:err.response.data,
        })
    }
}

function addEmptyPostAPI(data){
    return axios.post('/image', data);
}
function* addEmptyPost(action){
    try{
        const result = yield call(addEmptyPostAPI, action.data);
        yield put({
            type:SET_EMPTY_POST_SUCCESS,
            data:result.data,
        })
    }catch (err){
        console.log(err);
    }
}

function loadPostAPI(){
    return axios.get('/posts');
}

function* loadAllPost(){
    try{
        const result = yield call(loadPostAPI);
        yield put({
            type:LOAD_POSTS_SUCCESS,
            data:result.data,
        })
    }catch (err){
        yield put({
            type: LOAD_POSTS_FAILURE,
            error: err.response.data,
        })
    }
}

function signInAPI(data){
    console.log("signInData =  " + data);
    return axios.post('/user' , data , {
        headers: new Headers({
            'Content-Type': 'application/json',
        })
    });
}

function* signIn(action){
    try{
        console.log(action.data);
        const result = yield call(signInAPI, action.data);
        yield put({
            type:SIGN_IN_SUCCESS,
            data:result.data,
        })
    }catch (err){
        yield put({
            type:SIGN_IN_FAILURE,
            data:err.response.data,
        })
    }
}

function* watchAddEmptyPost(){
    yield takeLatest(DELETE_POST_REQUEST, deletePost)
    yield takeLatest(SET_EMPTY_POST_REQUEST, addEmptyPost);
    yield takeLatest(LOAD_POSTS_REQUEST, loadAllPost);
    yield takeLatest(SIGN_IN_REQUEST, signIn);
    yield takeLatest(LOG_IN_REQUEST, login);
    yield takeLatest(LOG_OUT_REQUEST, logout);
    yield takeLatest(UPDATE_POST_REQUEST, updatePost);
    yield takeLatest(LOG_IN_CHECK_REQUEST, loginCheck);
    yield takeLatest(ADD_COMMENT_REQUEST, addComment);
    yield takeLatest(DELETE_COMMENT_REQUEST, deleteComment);
}
export default function* rootSaga(){
    yield all([
        fork(watchAddEmptyPost),
    ]);
}