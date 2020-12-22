import React, {Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, CircularProgress, Container, Snackbar, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import axios, {AxiosError, AxiosResponse} from 'axios'
import DoneIcon from '@material-ui/icons/Done';
import MuiAlert, { AlertProps } from '@material-ui/lab/Alert'
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import {CheckCircle} from "@material-ui/icons";
import CancelIcon from '@material-ui/icons/Cancel';

interface IProps {

}

interface IState {
    myApplications: ApplicationI[]
    showToast: boolean
    loaded: boolean
    evalApplications: boolean
}

function Alert(props: AlertProps) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}


class MyApplications extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI | undefined}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            myApplications: [],
            showToast: false,
            loaded: false,
            evalApplications: false
        }
    }

    componentDidMount(){
        this.fetchApplications()
    }

    fetchApplications() {
        let myApplications: ApplicationI[]
        axios.get('/applications/student/' + this.props.user!!.id).then((r:AxiosResponse) =>{
            myApplications = r.data
            this.setState({
                ...this.state,
                myApplications: myApplications,
                loaded: true
            })

        })
    }

    updateApplicationsState(applications: ApplicationI[]){
        this.setState({
            ...this.state,
            myApplications: applications
        })
    }

    handleOnClick(id: number) {
        axios.post(`/applications/${id}`).then( (r: AxiosResponse) => {
          const applications = this.state.myApplications
          applications.forEach((a: ApplicationI) => {
              if(a.id == id){
                  a.status = 0
                  this.setState({
                      ...this.state,
                      showToast: true
                  })
                  return;
              }
          })
          this.updateApplicationsState(applications)
        }).catch((e: AxiosError) => {
            console.log(e)
        })
    }

    handleDeleteClick = (id: number) => {
        let newState =  this.state.myApplications.filter((a: ApplicationI) => a.id !== id)
        axios.delete(`/applications/${id}`).then((r: AxiosResponse) =>{
            this.setState({
                ...this.state,
                myApplications: newState
            })
        })
    }

    handleOnClickEdit = (id: number) => {
        const application: ApplicationI | undefined = this.state.myApplications.find((a: ApplicationI) => a.id == id)
        if(application == undefined){
            /* Application should never be undefined....*/
            alert("a strange error occured please try again.")
            return
        }
        this.props.history.push(`/application/${id}/edit`,{
            application: application,
            grantCall: undefined
        })
    }


    renderSubmittedOrToSubmit(id: number,status: number): JSX.Element{
        if(status == -1){
            return (
              <div style={{display: 'flex', marginRight:'20px'}}>
                  <EditIcon color='primary' style={{marginRight: '10px'}} onClick={() => this.handleOnClickEdit(id)}/>
                  <DeleteIcon onClick={() => this.handleDeleteClick(id)} style={{color: 'red',marginRight: '10px'}}/>
                  <Button onClick={()=>this.handleOnClick(id)} style={{backgroundColor: 'green',color:'white',height: '25px',width: '50px'}}>Submit</Button>
              </div>
            )
        }
        else {
            return (<DoneIcon style={{color: 'green', width: '3em',marginLeft:'80px'}}/>)
        }
    }

    handleOnClose = () => {
        this.setState({
            ...this.state,
            showToast: false
        })
    }

    handleOnClickButton  = () => {
        this.setState({
            ...this.state,
            evalApplications: !this.state.evalApplications
        })
    }

    handleOnClickEvaluatedApplication = (a: ApplicationI) => {
        const grantCall: GrantCallI | undefined = this.props.grantCalls.find(grantCall => grantCall.id == a.grantCallId)
        if(grantCall == undefined) {
            /* Grant Call should never be undefined...*/
            alert("A strange error occured.....")
            return
        }
        this.props.history.push(`/application/:id/reviews`,{
            application: a,
            grantCall: grantCall
        })
    }

    render(){
        const applicationsRender =  this.state.myApplications.map((a: ApplicationI) => {
            return(
              <Card key={a.id} className="object">
                <CardContent  key={a.id +"content"} style={{display: 'flex'}}>
                    <Typography  key={a.id +"t1"} variant="body2" component="h2">
                        {a.id}
                    </Typography>
                    <div style={{flexDirection: 'row-reverse',marginLeft:'430px'}}>
                     {this.renderSubmittedOrToSubmit(a.id,a.status)}
                    </div>
                </CardContent>
            </Card>)
        })

        const evaluatedApplicationsArray = this.state.myApplications.filter( function(a: ApplicationI){
            return a.status == 2 || a.status == 1
        })

        const evaluatedApplicationsRender = evaluatedApplicationsArray.map((a: ApplicationI) => {
            return(
                  <Card key={a.id} className="object" onClick={() => this.handleOnClickEvaluatedApplication(a)}>
                      <CardContent  key={a.id +"content"} style={{display: 'flex'}}>
                          <Typography  key={a.id +"t1"} variant="body2" component="h2">
                              {a.id}
                          </Typography>
                          <div style={{flexDirection: 'row-reverse',marginLeft:'430px'}}>
                              {a.status == 2 ? <CheckCircle style={{
                                  marginLeft: '90px',
                                  color: 'green'
                              }}/> : <CancelIcon style={{
                                  marginLeft: '90px',
                                  color: 'red'
                              }}/>}
                          </div>
                      </CardContent>
                  </Card>
              )
        })

        let progress
        if(!this.state.loaded){ progress = <CircularProgress />}

        return(
          <>
              <Card  className="listObjects">
                  <CardHeader  style={{textAlign: 'center',color: 'white'}} title="My Applications"/>
                  <Button style={{color: 'white', backgroundColor: '#0081b8',
                      marginLeft: '40px',marginTop: '25px'}}
                      onClick={() => this.handleOnClickButton()}>
                      {!this.state.evalApplications ? <> Evaluated Applications </>
                        : <> All  Applications </>}
                  </Button>
                  <CardContent  style={{display: 'flex'}}>
                      <Container style={{flexDirection: 'column'}}>
                          {!this.state.evalApplications ? applicationsRender : evaluatedApplicationsRender}
                      </Container>
                  </CardContent>
                  <Snackbar open={this.state.showToast} autoHideDuration={6000} onClose={() => this.handleOnClose()}>
                      <Alert onClose={() => this.handleOnClose()} severity="success">
                          Application submitted successfully!
                      </Alert>
                  </Snackbar>
                  {progress}
              </Card>

          </>
        )
    }

}

const mapStateToProps = (state: IStateStore) => ({
    user: state.user,
    institutions: state.institutions,
    grantCalls: state.grantCalls
});
// @ts-ignore
export default withRouter(connect(mapStateToProps)(MyApplications))