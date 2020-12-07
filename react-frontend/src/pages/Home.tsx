import React, {Component} from 'react';
import '../App.css';
import Login from "./Login";
import {InstitutionI} from "../DTOs";
import SignUp from "./SignUp";


interface IProps {
}

interface IState {
  institutions: InstitutionI[];
}


class Home extends Component<IProps, IState>{

  constructor(props:any) {
    super(props);
    this.state = {
      institutions: []
    }
  }

  render(){
    return(<>
          

          </>
    );
    }




}
export default Home;
