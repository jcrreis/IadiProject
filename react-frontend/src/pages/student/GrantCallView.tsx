import React, {Component} from 'react';
import '../../App.css';
import {GrantCallI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, Container, Snackbar, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import {formatDate} from "../../utils/utils";
import axios, {AxiosResponse} from 'axios'
import {Alert} from "@material-ui/lab";
interface IProps {

}

interface IState {
    grantCall: GrantCallI | undefined
    showError: boolean
}


class GranCallView extends Component<IProps & RouteComponentProps<{id: string}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string}> & IStateStore) {
        super(props);
        let grantCall: GrantCallI|undefined = this.props.grantCalls.find((grantCall: GrantCallI) => grantCall.id.toString() == this.props.match.params.id)
        if(grantCall ==  undefined){
            /* try an http request to server */
        }
        this.state = {
            grantCall: grantCall,
            showError: false

        }
    }

    handleOnClick = () => {
        axios.get(`/students/${this.props.user?.id}`).then((r: AxiosResponse) => {
            if(r.data.cv == null){
                this.setState({
                    ...this.state,
                    showError: true
                })
            }
            else{

                this.props.history.push(`/grantcall/${this.props.match.params.id}/application`,
                  {
                      title: this.state.grantCall?.title,
                      dataItems: this.state.grantCall?.dataItems
                  })
            }
        })
    }

    handleClose = () => {
        this.setState({
            ...this.state,
            showError: false
        })
    }

    render(){
        return(
         <>
             <Card key={this.state.grantCall!!.id} className="listObjects">
                 <CardHeader title={this.state.grantCall!!.title} style={{textAlign: 'center',color: 'white'}}/>
                 <CardContent  key={this.state.grantCall!!.id +"content"} style={{display: 'flex'}}>
                     <Container style={{flexDirection: 'column'}}>
                         <Typography  key={this.state.grantCall!!.id +"t1"} variant="body1" component="h2" style={{color: 'white',marginTop:'20px'}}>
                             {this.state.grantCall!!.description}
                         </Typography>
                         <Typography key={this.state.grantCall!!.id +"t2"} variant="body1" component="h2" className="typography1" >
                             To apply to this grant call you need: {this.state.grantCall!!.requirements}
                         </Typography>
                         <Typography key={this.state.grantCall!!.id +"t3"} variant="body1" component="h2" className="typography1" >
                            Funding: {this.state.grantCall!!.funding}€
                         </Typography>
                         <Typography key={this.state.grantCall!!.id +"t4"} variant="body1" component="h2" className="typography1" >
                             OpeningDate: {formatDate(this.state.grantCall!!.openingDate)}
                         </Typography>
                         <Typography key={this.state.grantCall!!.id +"t5"} variant="body1" component="h2" className="typography1" >
                             ClosingDate: {formatDate(this.state.grantCall!!.closingDate)}
                         </Typography>
                         <Button className='grantcallsubmitB greenButton' onClick={() => this.handleOnClick()}>APPLY</Button>
                         <Snackbar open={this.state.showError} autoHideDuration={6000} onClose={this.handleClose}>
                             <Alert onClose={this.handleClose} severity="error">
                                 You need a cv in order to apply to a grant call.
                             </Alert>
                         </Snackbar>
                    </Container>
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
export default withRouter(connect(mapStateToProps)(GranCallView))