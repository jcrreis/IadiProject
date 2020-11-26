import React, {ChangeEvent,MouseEvent, Component} from 'react';
import {AddUserI} from "./DTOs";
import axios from 'axios';
import {resolveAny} from "dns";

interface IProps {
    type: string
}

interface IState {
    user: AddUserI | undefined
    loading: boolean

}



class SignUp extends Component<IProps, IState> {

    constructor(props: IProps) {
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
                type: this.props.type
            },
            loading: false


        }
    }

    componentWillMount() {
        console.log(this.state.user)
    }

    handleUsernameChange = (e:ChangeEvent<HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                name: e.target.value
            }
        }))
    }
    handlePasswordChange = (e:ChangeEvent<HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                password: e.target.value
            }
        }))
    }

    handleEmailChange = (e:ChangeEvent<HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                email: e.target.value
            }
        }))
    }

    handleInstitutionChange = (e:ChangeEvent<HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                institutionId: e.target.value
            }
        }))
    }

    handleAddressChange = (e:ChangeEvent<HTMLInputElement>)  => {
        this.setState((prevState: any)=> ({
            user:{
                ...prevState.user,
                address: e.target.value
            }
        }))
    }

    signUpHandler = (e:MouseEvent<HTMLButtonElement>) => {
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
            <form>
                <input placeholder="username" onChange={(e) => this.handleUsernameChange(e)}></input>
                <input placeholder="password" onChange={(e) => this.handlePasswordChange(e)}></input>
                <input placeholder="email" onChange={(e) => this.handleEmailChange(e)}></input>
                <input placeholder="institutionId" onChange={(e) => this.handleInstitutionChange(e)}></input>
                <input placeholder="address" onChange={(e) => this.handleAddressChange(e)}></input>


                <button onClick={(e) => this.signUpHandler(e)}> SignUp </button>
            </form>
        )
    }
}

export default SignUp;