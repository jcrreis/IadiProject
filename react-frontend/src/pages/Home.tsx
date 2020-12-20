import React, {Component} from 'react';
import '../App.css';
import {GrantCallI, InstitutionI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, Checkbox, FormControlLabel, FormGroup, Switch, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import {ArrowForwardRounded} from "@material-ui/icons";
import AGrantCalls from "./anonymous/AGrantCalls";
import {formatDate, isCallOpen} from "../utils/utils";
import axios, {AxiosResponse} from 'axios'

interface IProps {
}

interface IState {
  institutions: InstitutionI[]
  reviewerCalls: GrantCallI[]
  panelChairCalls: GrantCallI[]
  showPanelChair: boolean
}


class Home extends Component<IProps & RouteComponentProps<{}> & IStateStore, IState>{

  constructor(props: IProps & RouteComponentProps<{}> & IStateStore) {
    super(props);
    this.state = {
      institutions: [],
      reviewerCalls: [],
      panelChairCalls: [],
      showPanelChair: false
    }
  }

  redirectSignup = (type: string) => {
    this.props.history.push('/signup',{ type: type });
  }

  fetchReviewerCalls = () => {

    if(this.props.user?.type == 'Reviewer') {
      axios.get(`grantcalls/reviewer/${this.props.user?.id}`).then((r: AxiosResponse) => {
        const grantCalls: GrantCallI[] = r.data
        console.log(grantCalls)
        grantCalls.forEach((call: GrantCallI) => {
          axios.get(`/evaluationpanels/${call.evaluationPanelId}`).then((r: AxiosResponse) => {
            if(r.data.panelChairId == this.props.user!!.id){
              this.setState({
                ...this.state,
                panelChairCalls: [...this.state.panelChairCalls,call]
              })
            }
            else{
              this.setState({
                ...this.state,
                reviewerCalls: [...this.state.reviewerCalls,call]
              })
            }
          })
        })
      })
    }
  }

  componentDidMount() {
    this.fetchReviewerCalls()
  }

  annonymousUserView = () => {
    if(this.props.user === undefined) {
      return (
        <>
        <Card className="homeCard" onClick={() => this.redirectSignup('Student')}>
          <CardContent className="homeCardContent">
            <Typography className="tCardHome" variant="h4">Join us as a Student!</Typography>
            <ArrowForwardRounded className="arrowIcon"/>
          </CardContent>
        </Card>
          <Card className="homeCard" onClick={() => this.redirectSignup('Reviewer')}>
            <CardContent className="homeCardContent">
            <Typography className="tCardHome" variant="h4">Join us as a Reviewer!</Typography>
              <ArrowForwardRounded className="arrowIcon"/>
            </CardContent>
          </Card>
        </>)
    }
  }

  handleGrantCallClickStudent(id: number) {
    this.props.history.push(`/grantcall/${id}`)
  }

  handleGrantCallClickReviewer(grantCall: GrantCallI){
    this.props.history.push(`/grantcall/${grantCall.id}/applications`,{grantCall: grantCall})
  }

  studentUserView = () => {
    if(this.props.user?.type == 'Student'){
      return(
        <>
          <Card className="listObjects">
            <CardHeader title="Open GrantCalls" style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
            </CardHeader>
            <CardContent>
              {this.props.grantCalls.map((grantCall: GrantCallI) => {
                if(isCallOpen(grantCall.openingDate,grantCall.closingDate)){
                  return(
                    <Card key={grantCall.id} className="object" onClick={() => this.handleGrantCallClickStudent(grantCall.id)}>
                      <CardContent  key={grantCall.id +"content"} style={{display: 'flex'}}>
                      <Typography  style={{overflowX: 'hidden',whiteSpace: 'nowrap'}}key={grantCall.id +"t1"} variant="body2" component="h2">                      {grantCall.title}
                      </Typography>
                      <Typography key={grantCall.id +"t2"} variant="body2" component="h2" style={{marginLeft:'333px'}}>
                        {formatDate(grantCall.openingDate)}
                      </Typography>
                      <div  key={grantCall.id + "div"} style={{flexDirection:'row-reverse', marginLeft:'60px'}}>
                        <Typography key={grantCall.id +"t3"} variant="body2" component="h2">
                          {formatDate(grantCall.closingDate)}
                        </Typography>
                      </div>
                    </CardContent>
                  </Card>)
                }
              })}
            </CardContent>
          </Card>
        </>
      )
    }

  }

  handleGrantCallClickPanelChair = (grantCall: GrantCallI) => {
    this.props.history.push(`/grantcall/${grantCall.id}/applications/panelchair`,{
      grantCall: grantCall
    })
  }

  handleSelectOnChange = () => {
    this.setState({
      ...this.state,
      showPanelChair: !this.state.showPanelChair
    })
  }


  reviewerUserView = () => {
    if(this.props.user?.type !== 'Reviewer') return

    const reviewerList = this.state.reviewerCalls.map((grantCall: GrantCallI) => {
      return(
        <>
          <Card key={grantCall.id} className="object" onClick={() => this.handleGrantCallClickReviewer(grantCall)}>
            <CardContent  key={grantCall.id +"content"} style={{display: 'flex'}}>
              <Typography  key={grantCall.id +"t1"} variant="body2" component="h2">
                {grantCall.title}
              </Typography>
              <Typography key={grantCall.id +"t2"} variant="body2" component="h2" style={{marginLeft:'333px'}}>
                {formatDate(grantCall.openingDate)}
              </Typography>
              <div  key={grantCall.id + "div"} style={{flexDirection:'row-reverse', marginLeft:'60px'}}>
                <Typography key={grantCall.id +"t3"} variant="body2" component="h2">
                  {formatDate(grantCall.closingDate)}
                </Typography>
              </div>
            </CardContent>
          </Card>
        </>)
    })

    const panelChairList = this.state.panelChairCalls.map((grantCall: GrantCallI) => {
      return(
        <>
          <Card key={grantCall.id} className="object" onClick={() => this.handleGrantCallClickPanelChair(grantCall)}>
            <CardContent  key={grantCall.id +"content"} style={{display: 'flex'}}>
              <Typography  key={grantCall.id +"t1"} variant="body2" component="h2">
                {grantCall.title}
              </Typography>
              <Typography key={grantCall.id +"t2"} variant="body2" component="h2" style={{marginLeft:'333px'}}>
                {formatDate(grantCall.openingDate)}
              </Typography>
              <div  key={grantCall.id + "div"} style={{flexDirection:'row-reverse', marginLeft:'60px'}}>
                <Typography key={grantCall.id +"t3"} variant="body2" component="h2">
                  {formatDate(grantCall.closingDate)}
                </Typography>
              </div>
            </CardContent>
          </Card>
        </>)
      })

      let renderContent

      if(this.state.showPanelChair){
        renderContent = panelChairList
      }
      else{
        renderContent = reviewerList
      }

      return(
      <>
        <Card className="listObjects">
          <CardHeader title="Assigned Grant Calls" style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
          </CardHeader>
          <FormControlLabel
            style={{
              marginLeft: '10px',
              color: 'white'
            }}
            control={
              <Checkbox
                checked={this.state.showPanelChair}
                onChange={() => this.handleSelectOnChange()}
                name="checkedB"
                color="primary"
              />
            }
            label="Show Panel Chair Calls"
          />
          <CardContent>
            {renderContent}
          </CardContent>
        </Card>
      </>
      )
  }

  render(){

    return(<>
            {this.annonymousUserView()}
            {this.studentUserView()}
            {this.reviewerUserView()}
          </>
    );
    }




}

const mapStateToProps = (state: IStateStore) => ({
  user: state.user,
  institutions: state.institutions,
  grantCalls: state.grantCalls
});

export default withRouter(connect(mapStateToProps)(Home))