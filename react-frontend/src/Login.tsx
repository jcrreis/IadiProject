import React, {ChangeEvent,MouseEvent, Component} from 'react';
import axios from 'axios';
import './App.css';


interface IProps {
}

interface IState {
    username: string
    password: string
    token: string
}

class Login extends Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);
        this.state = {
            username: "",
            password: "",
            token: ""
        }
    }

    handleUsernameChange = (e: ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        this.setState({
            username: e.target.value
        })
        console.log(this.state.username)
    }

    handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        this.setState({
            password: e.target.value
        })
        console.log(this.state.password)
    }

    loginHandler = (e: MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        axios.post('/login',{
            "name": this.state.username,
            "password": this.state.password
        }).then((r:any) => {
            this.setState({
                token: r.headers.authorization
            })
        }).catch(() => {
            alert("Invalid credentials.")
            this.setState({
                token: ""
            })
        })
    }





    render(){
        return(
            <form>
                <input placeholder="username" onChange={(e) => this.handleUsernameChange(e)}></input>
                <input placeholder="password" onChange={(e) => this.handlePasswordChange(e)}></input>
                <button onClick={(e) => this.loginHandler(e)}> LOGIN </button>
                {this.state.token}
            </form>
        )
    }
}

export default Login;