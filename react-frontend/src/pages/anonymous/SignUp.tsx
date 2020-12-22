import React, {ChangeEvent,MouseEvent, Component} from 'react';
import {AddUserI, InstitutionI, UserLoginI} from "../../DTOs";
import axios, {AxiosError, AxiosResponse} from 'axios';
import {RouteComponentProps, withRouter} from "react-router";
import Card from "@material-ui/core/Card";
import {CardContent, CardHeader, FormControl, Snackbar} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import {IStateStore} from "../../store/types";
import {connect} from "react-redux";
import {Alert} from "@material-ui/lab";
import {store} from "../../index";
import {LOGIN_USER} from "../../store/consts";

interface IProps {
    type: string
}

interface IState {
    user: AddUserI
    loading: boolean
    passwordConfirm: string
    error: {show: boolean, message: string}
    institutionName: string

}

class SignUp extends Component<IProps & RouteComponentProps<{}> & IStateStore, IState> {

    constructor(props: IProps & RouteComponentProps<{}> & IStateStore) {
        super(props);
        this.state = {
            user: {
                id: 0,
                name: "",
                password: "",
                email: "",
                address: "",
                institutionId: -1,
                contact: "",
                //@ts-ignore
                type: this.props.location.state.type
            },
            loading: false,
            passwordConfirm: "",
            error: {show: false, message: ""},
            institutionName: ""

        }
    }

    handleUsernameChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                name: e.target.value
            }
        }))
    }
    handlePasswordChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                password: e.target.value
            }
        }))
    }

    handleEmailChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                email: e.target.value
            }
        }))
    }

    handleInstitutionChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                institutionId: e.target.value
            }
        }))
    }

    handleAddressChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                address: e.target.value
            }
        }))
    }

    signUpHandler = (e:MouseEvent<HTMLAnchorElement | HTMLButtonElement>) => {
        e.preventDefault();
        if(this.state.user.password !== this.state.passwordConfirm){
            this.setState({
                ...this.state,
                error: {show: true, message: "Passwords don't match"}
            })
            return
        }

        if(this.state.user.name == "" || this.state.user.address == "" || this.state.user.email == "" ){
            this.setState({
                ...this.state,
                error: {show: true, message: "A mandatory field was left blank..."}
            })
            return
        }

        if(this.state.user.institutionId < 0 || this.state.user.institutionId == Number("")){
            this.setState({
                ...this.state,
                error: {show: true, message: "You need to select a valid institution"}
            })
            return
        }

        axios.post('/signup',this.state.user).then((r:AxiosResponse) => {
            const token = r.headers.authorization
            axios.get('/users/current').then((r: AxiosResponse) =>{
                const user: UserLoginI = {...r.data, token: token}
                store.dispatch({type: LOGIN_USER,user: user})
                localStorage.setItem('LOGIN_USER',JSON.stringify(user))
                this.props.history.push('/')
            })
        }).catch((e: AxiosError) => {
            this.setState({
                ...this.state,
                error: {show: true, message: "This email or username is already registered in the system.Please try again..."}
            })
        })
    }

    handleChange(e: React.ChangeEvent<{ name?: string; value: unknown }>){
        let institutionName: string = ""
        if(e.target.value != ""){
            institutionName = this.props.institutions[Number(e.target.value)-1].name
        }

        this.setState((prevState: any)=> ({
            ...prevState,
            user:{
                ...prevState.user,
                institutionId: e.target.value,
            },
            institutionName: institutionName
        }))
    }

    handlePasswordConfirmChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  =>{
        e.preventDefault();
        this.setState((prevState: any)=> ({
            passwordConfirm: e.target.value
        }))
    }

    handleClose = () => {
        this.setState({
            ...this.state,
            error: {show: false, message: ""}
        })
    }
    render(){
        return(
          <Card className="registerCard">
              <CardHeader className="cardHeader" title={"Creating an account as " + this.state.user?.type}></CardHeader>
              <CardContent className="registerContent">
                  <Snackbar open={this.state.error.show} autoHideDuration={3000} onClose={this.handleClose}
                            anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
                      <Alert onClose={this.handleClose} severity="error">
                          {this.state.error.message}
                      </Alert>
                  </Snackbar>
                  <Container component="main" maxWidth="xs" className="loginContainer">
                      <FormControl className="formControl">
                          <TextField  variant="outlined" id="username" label="Username"  value={this.state.user?.name} onChange={(e) => this.handleUsernameChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="email" label="Email" type="email" value={this.state.user?.email} onChange={(e) => this.handleEmailChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="password" label="Password" type="password" value={this.state.user?.password} onChange={(e) => this.handlePasswordChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="passwordConfirmation" label="PasswordConfirmation" type="password" value={this.state.passwordConfirm} onChange={(e) => this.handlePasswordConfirmChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="address" label="Address" type="address" value={this.state.user?.address} onChange={(e) => this.handleAddressChange(e)} />
                      </FormControl>
                      <FormControl variant="outlined" className="formControl institutionDrop">
                          <InputLabel htmlFor="outlined-age-native-simple" >Institution</InputLabel>
                          <Select
                            native
                            defaultValue=""
                            onChange={(e) => this.handleChange(e) }
                            label="Institution"
                          >
                              <option></option>
                              {this.props.institutions.map( (institution: InstitutionI) => {
                                  return <option key= {institution.id} value={institution.id}>{institution.name}</option>
                              })}
                          </Select>
                      </FormControl>
                      <div className="lsubmit">
                          <Button type="submit" variant="contained" color="primary" onClick={(e) => this.signUpHandler(e)}>Sign Up</Button>
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


export default withRouter(connect(mapStateToProps)(SignUp))
