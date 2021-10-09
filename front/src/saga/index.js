import axios from 'axios';
import {all,fork,take,put ,call} from 'redux-saga/effects';
import {takeLatest} from "@redux-saga/core/effects";
import {
    SET_EMPTY_POST,
    SET_EMPTY_POST_SUCCESS,
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

    }
}

function* watchAddEmptyPost(action){
    yield takeLatest(SET_EMPTY_POST, addEmptyPost);

}
export default function* rootSaga(){
    yield all([
        fork(watchAddEmptyPost),
    ]);
}