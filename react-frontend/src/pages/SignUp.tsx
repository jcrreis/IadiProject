import React, {ChangeEvent,MouseEvent, Component} from 'react';
import {AddUserI, InstitutionI} from "../DTOs";
import axios from 'axios';
import {RouteComponentProps, withRouter} from "react-router";
import Card from "@material-ui/core/Card";
import {CardContent, CardHeader, FormControl} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import {IStateStore} from "../store/types";
import {connect} from "react-redux";

interface IProps {
    type: string
}

interface IState {
    user: AddUserI | undefined
    loading: boolean
    passwordConfirm: string

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
            passwordConfirm: ""

        }
    }

    componentWillMount() {
        console.log(this.props.institutions)
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
        console.log(this.state.user)
        axios.post('/signup',this.state.user).then((r:any) => {
            console.log(r)
        }).catch((e:any) => {
            console.log(e)
        })
    }

    handleChange(e: React.ChangeEvent<{ name?: string; value: unknown }>){
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                institutionId: e.target.value
            }
        }))
    }

    handlePasswordConfirmChange = (e:ChangeEvent<HTMLTextAreaElement | HTMLInputElement>)  =>{
        e.preventDefault();
        this.setState((prevState: any)=> ({
            passwordConfirm: e.target.value
        }))
    }

    render(){
        return(
          <Card className="registerCard">
              <CardHeader className="cardHeader" title={"Creating an account as " + this.state.user?.type}></CardHeader>
              <CardContent className="registerContent">
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
                          <InputLabel htmlFor="outlined-age-native-simple">Institution</InputLabel>
                          <Select
                            native
                            value={this.props.institutions[this.state.user!!.institutionId]}
                            onChange={(e) => this.handleChange(e) }
                            label="Institution"
                            inputProps={{
                                name: 'age',
                                id: 'outlined-age-native-simple',
                            }}
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
    counter: state.counter,
    institutions: state.institutions
});


export default withRouter(connect(mapStateToProps)(SignUp))
