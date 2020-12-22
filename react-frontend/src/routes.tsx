import React from 'react';
// @ts-ignore
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Home from './pages/Home'
import NavBar from './components/NavBar'
import Login from './pages/anonymous/Login'
import SignUp from "./pages/anonymous/SignUp";
import {createMuiTheme, MuiThemeProvider} from "@material-ui/core/styles";
import AGrantCalls from "./pages/anonymous/AGrantCalls";
import GrantCallView from "./pages/student/GrantCallView";
import ApplicationForm from "./pages/student/ApplicationForm";
import MyApplications from "./pages/student/MyApplications";
import ApplicationsView from "./pages/reviewer/ApplicationsView";
import ReviewForm from "./pages/reviewer/ReviewForm";
import ReviewsList from "./pages/ReviewsList";
import ReviewDetails from "./pages/ReviewDetails";
import EditApplicationForm from "./pages/student/EditApplicationForm";
import ApplicationsViewPanelChair from "./pages/reviewer/ApplicationsViewPanelChair";
import StudentCV from "./pages/student/StudentCV";
import ApplicationDetails from "./pages/reviewer/ApplicationDetails";
import StudentDetailPage from "./pages/reviewer/StudentDetailPage";
import FundedApplications from "./pages/anonymous/FundedApplications";


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
           <Route path='/grantcall/:id/fundedapplications' component={FundedApplications}/>
           <Route path='/student/:id' component={StudentDetailPage}/>
           <Route path='/cv'  component={StudentCV}/>
           <Route path='/grantcall/:id/applications/panelchair' component={ApplicationsViewPanelChair}/>
           <Route path='/application/:id/edit' component={EditApplicationForm}/>
           <Route path='/review/:id' component={ReviewDetails}  />
           <Route path='/application/:id/reviews' component={ReviewsList}  />
           <Route path='/addreview/application/:id' component={ReviewForm}/>
           <Route path="/grantcall/:id/applications" component={ApplicationsView}/>
           <Route path="/myapplications" component={MyApplications}/>
           <Route path='application/:id' component={ApplicationDetails}/>
           <Route path="/grantcall/:id/application" component={ApplicationForm}/>
           <Route path="/grantcall/:id" component={GrantCallView}/>
           <Route path="/grantcalls" component={AGrantCalls}/>
           <Route path='/application/:id' component={ApplicationDetails}/>
           <Route path="/login" component={Login}/>
           <Route path="/signup" component={SignUp}/>
           <Route path="/" component={Home}/>
         </Switch>
         </MuiThemeProvider>
       </Router>
    </>)
}
