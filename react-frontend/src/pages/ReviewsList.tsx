import React, {Component} from 'react';
import '../App.css';
import {ApplicationI, GrantCallI, ReviewI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    application: ApplicationI
    reviews: ReviewI[]
    grantCall: GrantCallI
}


class ReviewsList extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI, review: ReviewI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI, review: ReviewI}> & IStateStore) {
        super(props);
        this.state = {
            application: this.props.location.state.application,
            grantCall: this.props.location.state.grantCall,
            reviews: []
        }
        console.log(this.props.location.state.grantCall)
    }

    componentDidMount() {
      const reviews = this.state.application.reviews
        for(let i in reviews){
            axios.get(`/reviews/${reviews[i]}`).then((r: AxiosResponse) => {
                const review: ReviewI = r.data
                this.setState({
                    ...this.state,
                    reviews: [...this.state.reviews, review]
                })
            })
        }

    }
    handleOnClick = (r: ReviewI) =>  {
        this.props.history.push(`/review/${r.id}`,{
            application: this.state.application,
            grantCall: this.state.grantCall,
            review: r
        })
    }


    render() {
        return(
          <>
              <Card  className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center', color: 'white'}}/>
                  <Typography variant='h6' style={{marginLeft: '20px', color:'white',marginTop: '20px'}}>{`ApplicationId: ${this.state.application.id}`}</Typography>
                  <CardContent key={"content"} style={{display: 'flex', flexDirection: 'column'}}>
                      {this.state.reviews.map((r: ReviewI) => {
                          return(
                            <Card key={r.id} className="object" onClick={() => this.handleOnClick(r)}>
                                <CardContent  key={r.id +"content"} style={{display: 'flex'}}>
                                    <Typography  key={r.id +"t1"} variant="body2" component="h2">
                                        {r.id}
                                    </Typography>
                                    <div style={{flexDirection: 'row-reverse',marginLeft:'600px'}}>
                                        {r.score}
                                    </div>
                                </CardContent>
                            </Card>
                          )
                      })}
                      <div style={{marginTop:'50px', marginLeft: '605px'}}>
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
    institutions: state.institutions,
    grantCalls: state.grantCalls
});

export default withRouter(connect(mapStateToProps)(ReviewsList))