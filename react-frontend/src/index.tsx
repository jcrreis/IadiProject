import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { createStore, applyMiddleware, Store,compose } from "redux"
import { Provider } from "react-redux"
import thunk from "redux-thunk"
import reducer, {fetchUserFromStorage} from "./store/reducer"

import reportWebVitals from './reportWebVitals';
import Routes from "./routes"
import {IStateStore, UserLoginAction} from "./store/types";


// @ts-ignore
const composeEnhancers = window['__REDUX_DEVTOOLS_EXTENSION_COMPOSE__'] as typeof compose || compose;


export const store: Store<IStateStore, UserLoginAction> & {
    dispatch: any
} = createStore(reducer, composeEnhancers(applyMiddleware(thunk)))



ReactDOM.render(

  <Provider store={store}>
      {fetchUserFromStorage()}
      <React.StrictMode>
        <Routes/>
      </React.StrictMode>
  </Provider>
, document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
