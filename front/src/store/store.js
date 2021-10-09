import {createWrapper} from 'next-redux-wrapper';
import {applyMiddleware , compose } from "redux";
import {createStore} from "redux";
import reducer from '../reducers/index'
import {composeWithDevTools} from "redux-devtools-extension";
import createSagaMiddleware from 'redux-saga';
import rootSaga from '../saga'


const Store = ()=>{
    const sagaMiddleware = createSagaMiddleware();
    const middlewares = [sagaMiddleware];
    const enhancer = process.env.NODE_ENV === 'production'
        ?compose(applyMiddleware(...middlewares))
        :composeWithDevTools(applyMiddleware(...middlewares));
    const store = createStore(reducer , enhancer);
    store.sagaTask = sagaMiddleware.run(rootSaga);
    return store;
};
const wrapper = createWrapper(Store,
    {debug:process.env.NODE_ENV === 'development',
    });

export default wrapper;