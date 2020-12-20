import React, {ChangeEvent, Component} from 'react';
import '../../App.css';
import {ApplicationI, CvItemI, GrantCallI, ReviewI, StudentI} from "../../DTOs";
import {IStateStore} from "../../store/types";

import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {
    Button,
    Card,
    CardHeader, Divider,
    Slider,
    TextField,
    Typography
} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import axios, {AxiosResponse} from 'axios'
import SuccessMessage from "../../components/SuccessMessage";
import { Link } from '@material-ui/core';



interface IProps {

}

interface IState {
    student: StudentI | undefined
}


class StudentDetailPage extends Component<IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI, student: StudentI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI, student: StudentI}> & IStateStore) {
        super(props);
        this.state = {
            student: this.props.location.state.student
        }
    }

    componentDidMount() {
        console.log(this.state.student)
    }


    render(){

        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.student?.name} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent>
                      <Typography variant="h6" style={{marginLeft: '20px',color:'white',marginTop: '20px'}}>
                          Email: {this.state.student?.email}
                      </Typography>
                      <Typography variant="h6" style={{marginLeft: '20px',color:'white',marginTop: '20px'}}>
                          Institution: {this.state.student?.institution.name}
                      </Typography>
                      <Divider style={{color: "black", marginTop:'20px'}}></Divider>
                      <Typography   variant="h6" style={{textAlign: 'center', color:'white',marginTop: '20px', marginBottom: '20px'}}>CV</Typography>
                       {this.state.student?.cv.items.map((d: CvItemI) => {
                           return(
                           <Typography variant="h6" style={{marginLeft: '20px',color:'white',marginTop: '20px'}}>
                             {d.item}: {d.value}
                           </Typography>)
                       })}

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

export default withRouter(connect(mapStateToProps)(StudentDetailPage))