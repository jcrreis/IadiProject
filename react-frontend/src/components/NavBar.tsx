import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import { useStore } from 'react-redux'
import '../App.css';
import { RouteComponentProps, withRouter } from "react-router";
import {IStateStore} from "../store/types";
import {Typography} from "@material-ui/core";
import {LOGOUT_USER} from "../store/consts";



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
    const store: any = useStore()
    const storeState: IStateStore = store.getState()

    const classes = useStyles()
    useEffect(()  =>{

    })
    const redirectLogin = () => {
      props.history.push('/login');
    }
    const redirectAGrantCalls = () => {
      props.history.push('/grantcalls');
    }
    const redirectHome = () => {
      props.history.push('/');
    }

    const redirectMyApplications = () => {
        props.history.push('/myapplications');
    }

    const handleLogout = () => {
        store.dispatch({type: LOGOUT_USER, user: undefined})
        props.history.push('/')
    }

    const redirectToCV = () => {
        props.history.push('/cv')
    }

    const renderNavBar = () => {
        if(storeState.user == undefined){
            return(
              <AppBar position="static"  >
                <Toolbar className={classes.appBar}>
                    <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectAGrantCalls()}>Grant Calls</Button>
                    <div className={classes.login}>
                        <Button color="inherit" onClick={() => redirectLogin()}>Login</Button>
                    </div>
                </Toolbar>
            </AppBar>)
        }
        else if(storeState.user.type == 'Student'){
           return(
             <AppBar position="static"  >
                <Toolbar className={classes.appBar}>
                    <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectAGrantCalls()}>Grant Calls</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectMyApplications()}>My Applications</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectToCV()}>CV</Button>
                    <div className={classes.login}>
                        <Typography className="userNameNav" title={storeState.user.name}/>
                        <Button color="inherit" onClick={() => handleLogout()}>Logout</Button>
                    </div>
                </Toolbar>
            </AppBar>)
        }
        else if(storeState.user.type == 'Reviewer'){
           return(<AppBar position="static"  >
                <Toolbar className={classes.appBar}>
                    <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectAGrantCalls()}>Grant Calls</Button>
                    <div className={classes.login}>
                        <Button color="inherit" onClick={() => handleLogout()}>Logout</Button>
                    </div>
                </Toolbar>
            </AppBar>)
        }
    }
  return(
      <>
          {renderNavBar()}
      </>
    )
    ;
}


export default  withRouter(NavBar)