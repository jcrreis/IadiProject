import React, {Component} from 'react';
import '../App.css';
import {GrantCallI, InstitutionI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, FormControlLabel, FormGroup, Switch, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import CloseIcon from '@material-ui/icons/Close';
import CheckIcon from '@material-ui/icons/Check';
import {formatDate, isCallOpen} from "../utils/utils";

interface IProps {

}

interface IState {
    showAll: boolean
}


class AGrantCalls extends Component<IProps & RouteComponentProps<{}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{}> & IStateStore) {
        super(props);
        this.state = {
            showAll: false
        }
    }


    renderCloseOrOpen(openingDate: Date,closingDate: Date): JSX.Element {
        if(isCallOpen(openingDate,closingDate)){
            return(
              <CheckIcon style={{ color: 'green',
                marginLeft: 'auto',marginRight: '10px'}}
              />)
        }
        else{
            return(
              <CloseIcon style={{ color: 'red',
                marginLeft: 'auto',marginRight: '10px'}}
              />)
        }
    }

    handleChange = () => {
        this.setState({
            ...this.state,
            showAll: !this.state.showAll
        })
    };

    render(){
        const renderAll = this.props.grantCalls.map((grantCall: GrantCallI) => {
                return (
                  <Card key={grantCall.id} className="object">
                    <CardContent  key={grantCall.id +"content"} style={{display: 'flex'}}>
                        <Typography  key={grantCall.id +"t1"} variant="body2" component="h2">
                            {grantCall.title}
                        </Typography>
                        <Typography key={grantCall.id +"t2"} variant="body2" component="h2" style={{marginLeft:'230px'}}>
                            {formatDate(grantCall.openingDate)}
                        </Typography>
                        <div  key={grantCall.id + "div"} style={{flexDirection:'row-reverse', marginLeft:'60px'}}>
                            <Typography key={grantCall.id +"t3"} variant="body2" component="h2">
                                {formatDate(grantCall.closingDate)}
                            </Typography>
                        </div>
                        {this.renderCloseOrOpen(grantCall.openingDate,grantCall.closingDate)}
                    </CardContent>
                  </Card>)
                })

        const renderOpen = this.props.grantCalls.map((grantCall: GrantCallI)=>{
            if(isCallOpen(grantCall.openingDate,grantCall.closingDate)){
                return(
                  <Card key={grantCall.id} className="object">
                      <CardContent  key={grantCall.id +"content"} style={{display: 'flex'}}>
                          <Typography  key={grantCall.id +"t1"} variant="body2" component="h2">
                              {grantCall.title}
                          </Typography>
                          <div  key={grantCall.id + "div"} style={{flexDirection:'row-reverse', marginLeft:'auto',marginRight: '34px'}}>
                              <Typography  key={grantCall.id +"t1"} variant="body2" component="h2">
                                  {grantCall.applications.length}
                              </Typography>
                          </div>
                      </CardContent>
                  </Card>
                )
            }
        })
        let cardContent
        if(this.state.showAll)
            cardContent = renderAll
        else
            cardContent = renderOpen
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title="GrantCalls" style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <FormGroup>
                      <FormControlLabel
                        style={{color:'white',marginLeft: 'auto'}}
                        control={<Switch color='primary' />}
                        label="ShowAll"
                        checked={this.state.showAll}
                        onChange={() => this.handleChange()}
                      />
                  </FormGroup>
                  <CardContent>
                      {cardContent}
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

export default withRouter(connect(mapStateToProps)(AGrantCalls))