import React, { Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {
    Button,
    Card,
    CardHeader,
    Typography
} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import axios, {AxiosResponse} from 'axios'



interface IProps {

}

interface IState {
    grantCall: GrantCallI
    fundedApplications: ApplicationI[]
}


class StudentDetailPage extends Component<IProps & RouteComponentProps<{},any,{grantCall: GrantCallI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{},any,{grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            grantCall: this.props.location.state.grantCall,
            fundedApplications: []
        }
    }

    componentDidMount() {
        axios.get(`/grantcalls/${this.state.grantCall.id}/fundedapplications`).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                fundedApplications: r.data
            })
        })
    }


    render(){

        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={`Funded applications of ${this.state.grantCall.title}`}
                              style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent>
                      {this.state.fundedApplications.map((a: ApplicationI) => {
                          return(
                            <Card key={a.id} className="object">
                                <CardContent  key={a.id +"content"} style={{display: 'flex'}}>
                                    <Typography  key={a.id +"t1"} variant="body2" component="h2">
                                        {a.id}
                                    </Typography>
                                </CardContent>
                            </Card>
                          )
                      })}
                      <Button className='backButton' onClick={() => this.props.history.goBack()} style =
                      {{marginTop: '300px'}}>BACK</Button>
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