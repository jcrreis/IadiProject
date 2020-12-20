import React, {Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI, StudentI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, Link, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    application: ApplicationI
    grantCall: GrantCallI
    student: StudentI | undefined
}


class ApplicationsView extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            application: this.props.location.state.application,
            grantCall: this.props.location.state.grantCall,
            student: undefined
        }
    }

    componentDidMount() {
        axios.get(`/students/${this.state.application.studentId}`).then((r: AxiosResponse) =>{
            console.log(r)
            const student: StudentI = r.data
            console.log(student)
            this.setState({
                ...this.state,
                student: student
            })
        })
    }

    render() {
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent>

                  </CardContent>
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