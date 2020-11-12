import React, {Component} from 'react';
import axios from 'axios';
import './App.css';

interface Institution{
  id: number,
  name: string,
  contact: string

}
interface IProps {
}

interface IState {
  institutions: Institution[];
}


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
    }

  render(){
    return(<div className="App">
      {this.state.institutions.map(institution => (
          <div className="institution" key={institution.id}>{institution.name}</div>
      ))}
    </div>

    );
    }




}
export default App;
