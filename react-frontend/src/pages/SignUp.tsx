import React, {ChangeEvent,MouseEvent, Component} from 'react';
import {AddUserI} from "../DTOs";
import axios from 'axios';
import {resolveAny} from "dns";
import {RouteComponentProps, withRouter} from "react-router";
import Card from "@material-ui/core/Card";
import {CardContent, FormControl} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

interface IProps {
    type: string
}

interface IState {
    user: AddUserI | undefined
    loading: boolean

}



class SignUp extends Component<IProps & RouteComponentProps<{}>, IState> {

    constructor(props: IProps & RouteComponentProps<{}>) {
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

        }
    }

    componentWillMount() {

        console.log()
        console.log(this.state.user)
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



    render(){
        return(
          <Card className="loginCard">
              <CardContent className="loginContent">
                  <Container component="main" maxWidth="xs" className="loginContainer">
                      <FormControl className="formControl">
                          <TextField  variant="outlined" id="username" label="Username"  value={this.state.user?.name} onChange={(e) => this.handleUsernameChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="password" label="Password" type="password" value={this.state.user?.password} onChange={(e) => this.handlePasswordChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="email" label="Email" type="email" value={this.state.user?.email} onChange={(e) => this.handleEmailChange(e)} />
                      </FormControl>
                      <FormControl className="formControl">
                          <TextField variant="outlined" id="address" label="Address" type="address" value={this.state.user?.address} onChange={(e) => this.handleAddressChange(e)} />
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

export default SignUp