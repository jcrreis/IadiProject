import React, {Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, Link, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    grantCallId: number
    applications: ApplicationI[]
    grantCall: GrantCallI
    applicationsReviewed: number[]
}


class ApplicationsView extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            grantCallId: Number(this.props.match.params.id),
            applications: [],
            grantCall: this.props.location.state.grantCall,
            applicationsReviewed: []
        }
    }

    componentDidMount() {
        axios.get(`/applications/grantcall/${this.state.grantCallId}`).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                applications: r.data
            })
        })

        axios.get(`/reviewers/${this.props.user?.id}/applications`).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                applicationsReviewed: r.data
            })
        })

    }

    handleAddReview(application: ApplicationI){
        this.props.history.push(`/addreview/application/${application.id}`,{
            application: application,
            grantCall: this.state.grantCall
        })
    }

    handleRedirectToReviews(application: ApplicationI){
        this.props.history.push(`/application/${application.id}/reviews`,{
            application: application,
            grantCall: this.state.grantCall
        })
    }

    renderButton(a: ApplicationI): JSX.Element{
        if(!this.state.applicationsReviewed.includes(a.id)){
            return(
              <Button style={{color: 'white',backgroundColor: 'green',width: '109px',height: '22px'}}
                           onClick={() => this.handleAddReview(a)}>
                Add Review
              </Button>)
        }
        else
            return(
              <Button style={{color: 'white',backgroundColor: 'blue',width: '109px',height: '22px'}}
                         onClick={() => this.handleRedirectToReviews(a)}>
                Reviews
              </Button>)

    }
    handleOnClick(a: ApplicationI){
        this.props.history.push(`/application/${a.id}`,{
            application: a,
            grantCall: this.state.grantCall
        })
    }


    render() {
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent>
                      {this.state.applications.map((a: ApplicationI) => {
                          return (
                            <Card key={a.id} className="object">
                                <CardContent  key={a.id +"content"} style={{display: 'flex'}}>
                                    <Link
                                      component="button"
                                      variant="body2"
                                      style={{color: "#62ddfa"}}
                                      onClick={() => this.handleOnClick(a)}>
                                        <Typography  key={a.id +"t1"} variant="body2" component="h2">
                                            {a.id}
                                        </Typography>
                                    </Link>
                                    <div style={{flexDirection: 'row-reverse',marginLeft:'500px'}}>
                                        {this.renderButton(a)}
                                    </div>
                                </CardContent>
                            </Card>)
                      })}
                  </CardContent>
                  <Button className='backButton'
                          style={{
                              marginTop: '247px',
                              marginLeft: '25px',
                              marginBottom: '25px'
                          }}
                          onClick={() => this.props.history.goBack()}>BACK
                  </Button>
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