import axios from 'axios';
import {all,fork,take,put ,call} from 'redux-saga/effects';
import {takeLatest} from "@redux-saga/core/effects";
import {
    SET_EMPTY_POST,
    SET_EMPTY_POST_SUCCESS,
    LOAD_POSTS_REQUEST,
    LOAD_POSTS_SUCCESS,
    LOAD_POSTS_FAILURE,
} from "../reducers/index"
axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true;

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

function* watchAddEmptyPost(){
    yield takeLatest(SET_EMPTY_POST, addEmptyPost);
    yield takeLatest(LOAD_POSTS_REQUEST, loadAllPost);
}
export default function* rootSaga(){
    yield all([
        fork(watchAddEmptyPost),
        fork(watchAddEmptyPost),
    ]);
}