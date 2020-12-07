import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { createStore, applyMiddleware, Store,compose } from "redux"
import { Provider } from "react-redux"
import thunk from "redux-thunk"
import reducer from "./store/reducer"

import reportWebVitals from './reportWebVitals';
import Routes from "./routes"
import {IStateStore, UserLoginAction} from "./store/types";

export const store: Store<IStateStore, UserLoginAction> & {
    dispatch: any
} = createStore(reducer, applyMiddleware(thunk))



ReactDOM.render(
  <Provider store={store}>
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
