import React, {Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI, StudentI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, Checkbox, FormControlLabel, Link, TextField, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    application: ApplicationI
    grantCall: GrantCallI
    student: StudentI | undefined
}


class ApplicationsView extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI, student: StudentI | undefined}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI, student: StudentI | undefined}> & IStateStore) {
        super(props);
        this.state = {
            application: this.props.location.state.application,
            grantCall: this.props.location.state.grantCall,
            student: undefined
        }
    }

    componentDidMount() {
        axios.get(`/students/${this.state.application.studentId}`).then((r: AxiosResponse) =>{
            const student: StudentI = r.data
            this.setState({
                ...this.state,
                student: student
            })
        })
    }
    redirectToStudentPage(){
        this.props.history.push(`/student/${this.state.student?.id}`,{
            application: this.state.application,
            student: this.state.student,
            grantCall: this.state.grantCall
        })
    }

    render() {
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent style={{display: 'flex', flexDirection: 'column'}}>
                      <Link onClick={() => this.redirectToStudentPage()} style={{marginBottom: '20px'}}>
                          <Typography>Student: {this.state.student?.name}</Typography>
                      </Link>
                      {this.state.application.answers.map((a: string,index: number) => {
                        if(a == "true" || false){
                            return <FormControlLabel
                              style={{
                              color:'white'
                              }}
                              control={
                                  <Checkbox
                                    inputProps={{
                                        checked: Boolean(a),
                                        readOnly: true,
                                    }}
                                    style={{
                                    color: '#0081b8'
                                    }}
                                    name="checkedB"
                                    color="primary"
                                  />
                              }
                              label={this.state.grantCall.dataItems[index].name}
                            />
                        }
                        else return(
                          <TextField
                            style={{marginTop: '10px'}}
                            id={a}
                            label={this.state.grantCall.dataItems[index].name}
                            multiline
                            rows={4}
                            defaultValue={a}
                            variant="outlined"
                          />
                          )
                      })}
                  </CardContent>
                  <Button style={{marginLeft: '25px',marginBottom: '30px'}} className='backButton' onClick={() => this.props.history.goBack()}>BACK</Button>

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

export default withRouter(connect(mapStateToProps)(ApplicationsView))