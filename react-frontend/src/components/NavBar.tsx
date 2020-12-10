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
    const store: IStateStore = useStore().getState()

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
        alert("not implemented")
    }

    const renderNavBar = () => {
        if(store.user == undefined){
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
        else if(store.user.type == 'Student'){
           return(
             <AppBar position="static"  >
                <Toolbar className={classes.appBar}>
                    <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
                    <Button color="inherit" className={classes.title} onClick={() => alert("notimplemented")}>Grant Calls</Button>
                    <Button color="inherit" className={classes.title} onClick={() => redirectMyApplications()}>My Applications</Button>
                    <div className={classes.login}>
                        <Typography className="userNameNav" title={store.user.name}/>
                        <Button color="inherit" onClick={() => handleLogout()}>Logout</Button>
                    </div>
                </Toolbar>
            </AppBar>)
        }
        else if(store.user.type == 'Reviewer'){
           return(<AppBar position="static"  >
                <Toolbar className={classes.appBar}>
                    <Button color="inherit" onClick={() => redirectHome()}>Home</Button>
                    <Button color="inherit" className={classes.title} onClick={() => alert("notimplemented")}>Grant Calls</Button>
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