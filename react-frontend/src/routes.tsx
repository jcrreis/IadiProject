import React from 'react';
// @ts-ignore
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Home from './pages/Home'
import NavBar from './components/NavBar'
import Login from './pages/Login'
import SignUp from "./pages/SignUp";
import {createMuiTheme, MuiThemeProvider} from "@material-ui/core/styles";
import AGrantCalls from "./pages/AGrantCalls";
import GrantCallView from "./pages/GrantCallView";
import ApplicationForm from "./pages/ApplicationForm";
import MyApplications from "./pages/MyApplications";
import ApplicationsView from "./pages/ApplicationsView";
import ReviewForm from "./pages/ReviewForm";


const theme = createMuiTheme({palette: {
        primary: {main: '#0081b8',contrastText: "#fff"
        },
    }})
export default function Routes()
{

  return(<>
       <Router>
         <MuiThemeProvider theme={theme}>
           <NavBar/>
         <Switch>
           <Route path='/addreview/application/:id' component={ReviewForm}/>
           <Route path="/grantcall/:id/applications" component={ApplicationsView}/>
           <Route path="/myapplications" component={MyApplications}/>
           <Route path="/grantcall/:id/application" component={ApplicationForm}/>
           <Route path="/grantcall/:id" component={GrantCallView}/>
           <Route path="/grantcalls" component={AGrantCalls}/>
           <Route path="/login" component={Login}/>
           <Route path="/signup" component={SignUp}/>
           <Route path="/" component={Home}/>
         </Switch>
         </MuiThemeProvider>
       </Router>
    </>)
}
