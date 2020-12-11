import React, {Component} from 'react';
import '../App.css';
import {ApplicationI, GrantCallI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import axios, {AxiosResponse} from 'axios'
import {Button, Card, CardHeader, Link, Modal, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";

interface IProps {

}

interface IState {
    grantCallId: number
    applications: ApplicationI[]
    grantCall: GrantCallI
    modal: {openModal: boolean, candidate: ApplicationI | undefined}
}


class ApplicationsViewPanelChair extends Component<IProps & RouteComponentProps<{id: string},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{application: ApplicationI,grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            grantCallId: Number(this.props.match.params.id),
            applications: [],
            grantCall: this.props.location.state.grantCall,
            modal: {openModal: false, candidate: undefined}
        }
    }

    componentDidMount() {
        axios.get(`/applications/grantcall/${this.state.grantCallId}`).then((r: AxiosResponse) => {
            console.log(r.data)
            this.setState({
                ...this.state,
                applications: r.data
            })
        })

    }

    componentDidUpdate(){
    }

    handleOnClick = (a: ApplicationI) => {
        this.props.history.push(`/application/${a.id}/reviews`,{
            application: a,
            grantCall: this.state.grantCall
        })
    }

    handleClose = () => {
        this.setState({
            ...this.state,
             modal: {openModal: false, candidate: undefined}
        })
    }

    handleOnSelectClick = (a: ApplicationI) => {
        this.setState({
            ...this.state,
            modal: {openModal: true, candidate: a}
        })
    }


    grantApplicationFunding = () =>{
        console.log(this.state.modal.candidate)
    }

    render() {
        return(
          <>
              <Card className="listObjects">
                  <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                  </CardHeader>
                  <CardContent>
                      <Modal
                        open={this.state.modal.openModal}
                        onClose={this.handleClose}
                        style={{display:'flex'}}
                        aria-labelledby="simple-modal-title"
                        aria-describedby="simple-modal-description"
                      >
                         <div style={{margin: 'auto',color:'white'}}>
                             <Typography  variant="body2" component="h2" style={{marginTop: '50px'}}>
                                 Are you sure that you want to select this application as eligible to receive funding from Grant Call
                                 {`${this.state.grantCall.title}`} ?
                             </Typography>
                            <div className="modalButtons" style={{marginTop: '50px',marginLeft:'317px',marginBottom: '50px'}}>
                                <Button className="greenButton" style={{marginRight: '30px'}} onClick={() => this.grantApplicationFunding()}>YES</Button>
                                <Button style={{backgroundColor: 'red', color:'white'}} onClick={() => this.handleClose()}>NO</Button>
                            </div>
                         </div>
                      </Modal>
                      {this.state.applications.map((a: ApplicationI) => {
                          return (
                            <Card key={a.id} className="object">
                                <CardContent  key={a.id +"content"} style={{display: 'flex'}}>
                                    <div style={{ marginRight: '7px'}}>
                                        <Link
                                          component="button"
                                          variant="body2"
                                          style={{color: "#62ddfa"}}
                                          onClick={() => this.handleOnClick(a)}
                                        >
                                            <Typography  key={a.id +"t1"} variant="body2" component="h2" >
                                                {a.id}
                                            </Typography>
                                        </Link>
                                    </div>
                                    <div style={{flexDirection: 'row-reverse',display:'flex',marginLeft:'500px'}}>
                                        <Button style={{ height:'21px'}} className="greenButton" onClick={() => this.handleOnSelectClick(a)}>SELECT</Button>
                                        <Typography  key={a.id +"t1"} variant="body2" component="h2" style={{marginRight: '20px'}}>
                                            0
                                        </Typography>
                                    </div>
                                </CardContent>
                            </Card>)
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

export default withRouter(connect(mapStateToProps)(ApplicationsViewPanelChair))