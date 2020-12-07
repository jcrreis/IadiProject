import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';

import '../App.css';
import { RouteComponentProps, withRouter } from "react-router";


interface IProps {
}

interface IState {

}
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    marginLeft: '10px',

  },
  appBar: {
    backgroundColor: 'black',
  },
  login:{
    marginLeft: 'auto',
  },
  hover:{
    marginLeft: 'auto',
  }
}));




function NavBar(props: RouteComponentProps<{}>) {

    const classes = useStyles()
    useEffect(()  =>{

    })
    const redirectLogin = () => {
      props.history.push('/login');
    }
    const redirectSignUp = () => {
      props.history.push('/signup',{ type: 'Student' });
    }
    const redirectHome = () => {
      props.history.push('/');
    }
  return(
      <>
        <AppBar position="static"  >
          <Toolbar className={classes.appBar}>
            <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
            <Button color="inherit" className={classes.title} onClick={() => redirectSignUp()}>Grant Calls</Button>
            <div className={classes.login}>
              <Button color="inherit" onClick={() => redirectLogin()}>Login</Button>
            </div>
          </Toolbar>
        </AppBar>
      </>
    )
    ;
}


export default  withRouter(NavBar)