import React, {ChangeEvent,MouseEvent, Component} from 'react';
import axios, {AxiosResponse} from 'axios';
import '../App.css';
import Card from '@material-ui/core/Card';
import {CardContent, CardHeader, FormControl, Snackbar} from "@material-ui/core";
import { WithStyles } from "@material-ui/core/styles";

import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import {withRouter, RouteComponentProps} from "react-router";
import {connect} from "react-redux";
import {UserLoginI} from "../DTOs";
import {IStateStore} from "../store/types";
import {store} from "../index";
import {LOGIN_USER} from "../store/consts";
import {Alert} from "@material-ui/lab";



interface IProps extends WithStyles {

}

interface IState {
    username: string
    password: string
    token: string
    showError: boolean
}

class Login extends Component<IProps & RouteComponentProps<{}> & IStateStore, IState> {

    constructor(props: IProps & RouteComponentProps<{}> & IStateStore) {
        super(props);
        this.state = {
            username: "",
            password: "",
            token: "",
            showError: false
        }
        console.log(this.props.user)
        console.log(this.props.institutions)
    }

    handleUsernameChange = (e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
       e.preventDefault()
        this.setState({
            username: e.target.value
        })
    }

    handlePasswordChange = (e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        e.preventDefault()
       this.setState({
            password: e.target.value
        })
    }

    loginHandler = (e: MouseEvent<HTMLAnchorElement | HTMLButtonElement>) => {
        e.preventDefault()
        axios.post('/login',{
            "name": this.state.username,
            "password": this.state.password
        }).then((r:AxiosResponse) => {
            this.setState({
                token: r.headers.authorization
            })
            axios.get('/users/current',{
                headers: {
                    Authorization: this.state.token
                }
            }).then( (r: AxiosResponse) => {
                const user: UserLoginI = {
                    id: r.data.id,
                    name: r.data.name,
                    email: r.data.email,
                    token: this.state.token,
                    address: r.data.address,
                    type: r.data.type
                }
                console.log(user)
                store.dispatch({type: LOGIN_USER,user: user})
                localStorage.setItem('LOGIN_USER',JSON.stringify(user))
                this.props.history.push('/')
            })
        }).catch(() => {
            this.setState({
                ...this.state,
                token: "",
                showError: true
            })
        })
    }

    handleClose = () => {
        this.setState({
            ...this.state,
            showError: false
        })
    }
    
    render(){
        return(
          <Card className="loginCard">
              <CardHeader className="cardHeader" title={"Login"}></CardHeader>
              <Snackbar open={this.state.showError} autoHideDuration={3000} onClose={this.handleClose}
                        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                  <Alert onClose={this.handleClose} severity="error">
                      Invalid credentials, please try again with another credentials!
                  </Alert>
              </Snackbar>
              <CardContent className="loginContent">
              <Container component="main" maxWidth="xs" className="loginContainer">
                  <FormControl className="formControl">
                      <TextField  variant="outlined" id="username" label="Username"  value={this.state.username} onChange={(e) => this.handleUsernameChange(e)} />
                  </FormControl>
                  <FormControl className="formControl">
                      <TextField variant="outlined" id="password" label="Password" type="password" value={this.state.password} onChange={(e) => this.handlePasswordChange(e)} />
                  </FormControl>
                  <div className="lsubmit">
                      <Button type="submit" variant="contained" color="primary" onClick={(e) => this.loginHandler(e)}>Login</Button>
                  </div>
              </Container>

            </CardContent>
          </Card>
        )
    }
}

const mapStateToProps = (state: IStateStore) => ({
    user: state.user,
    institutions: state.institutions
});

export default withRouter(connect(mapStateToProps)(Login))