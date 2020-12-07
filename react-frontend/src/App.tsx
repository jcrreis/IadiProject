import React, {Component} from 'react';
import axios from 'axios';
import './App.css';
import Login from "./pages/Login";
import {InstitutionI} from "./DTOs";
import SignUp from "./pages/SignUp";

/*
interface IProps {
}
/*
interface IState {
  institutions: InstitutionI[];
}*/

/*
class App extends Component<IProps, IState>{

  constructor(props:any) {
    super(props);
    this.state = {
      institutions: []
    }
  }
    componentDidMount(){
      let token = undefined;

      axios.post('/login',{
        "name": "sponsor22",
        "password": "sponsor22"
      }).then(r => {
        token = r.headers.authorization

        axios.get('/institutions',{
          headers: {
            Authorization: token
          }
        }).then(r => {
          console.log(r.data)
          this.setState({
            institutions: r.data
          })
        })
      }).catch(e => {
        console.log(e)
      })

      axios.get('/users/current',{ headers: {
          Authorization: token
        }}).then(r => {
        console.log(r)
      }).catch(e => {
        console.log(e)
      })
    }

  render(){
    return(<>
          <div className="App">
            {this.state.institutions.map(institution => (
                <div className="institution" key={institution.id}>{institution.name}</div>
            ))}
          </div>
          <Login/>
          <SignUp type={"Student"}/>
          </>
    );
    }




}
export default App;
*/