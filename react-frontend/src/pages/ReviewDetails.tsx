import React, {Component} from 'react';
import '../App.css';
import {ApplicationI, GrantCallI, ReviewerI, ReviewI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, TextField, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    application: ApplicationI
    grantCall: GrantCallI
    review: ReviewI
    reviewer: ReviewerI | undefined
}


class ReviewDetails extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI, review: ReviewI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI,review: ReviewI}> & IStateStore) {
        super(props);
        this.state = {
            application: this.props.location.state.application,
            grantCall: this.props.location.state.grantCall,
            review: this.props.location.state.review,
            reviewer: undefined
        }
    }

    componentDidMount() {

        axios.get(`/reviewers/${this.state.review.reviewerId}`).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                reviewer: r.data
            })
        })
    }

    render() {
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center', color: 'white'}}/>
                  <Typography variant='h6' style={{marginLeft: '20px', color:'white',marginTop: '20px'}}>{`ApplicationId: ${this.state.application.id}`}</Typography>
                  <CardContent key={"content"} style={{display: 'flex', flexDirection: 'column'}}>
                      <div className="reviewDetails" >
                                    <Typography  variant="body2" component="h2">
                                        ReviewId: {this.state.review.id}
                                    </Typography>
                                    <Typography  variant="body2" component="h2">
                                          Reviewer: {this.state.reviewer?.name}
                                    </Typography>
                                    <TextField
                                        id="outlined-read-only-input"
                                        label="Observations"
                                        defaultValue={this.state.review.observations}
                                        multiline
                                        rows={4}
                                        InputProps={{
                                            readOnly: true,
                                        }}
                                        variant="outlined"
                                        style={{marginTop: '50px'}}
                                    />
                                    <Typography  variant="body2" component="h2" style={{marginTop: '30px'}}>
                                        Score: {this.state.review.score}
                                    </Typography>
                      </div>
                      <div style={{marginTop:'50px', marginLeft: '580px',marginBottom: '20px'}}>
                          <Button className='backButton' onClick={() => this.props.history.goBack()}>BACK</Button>
                      </div>
                  </CardContent>
              </Card>
          </>
        )
    }
}

const mapStateToProps = (state: IStateStore) => ({
    user: state.user,
    counter: state.counter,
    institutions: state.institutions,
    grantCalls: state.grantCalls
});

export default withRouter(connect(mapStateToProps)(ReviewDetails))