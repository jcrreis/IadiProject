import React, {ChangeEvent, Component} from 'react';
import '../App.css';
import {ApplicationI, GrantCallI} from "../DTOs";
import {IStateStore} from "../store/types";
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

interface IProps {

}

interface IState {
    grantCall: GrantCallI
    application: ApplicationI
    observations: string
    score: number
}


class ReviewForm extends Component<IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI}> & IStateStore) {
        super(props);
        this.state = {
            grantCall: this.props.location.state.grantCall,
            application: this.props.location.state.application,
            observations: "",
            score: 0
        }
    }


/* <TextField
              id="outlined-read-only-input"
              label="Read Only"
              defaultValue="Hello World"
              InputProps={{
                  readOnly: true,
              }}
              variant="outlined"
            />*/

    handleOnChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        this.setState({
            ...this.state,
            observations: e.target.value
        })
    }

    handleSliderChange = (value: number) => {
        this.setState({
            ...this.state,
            score: value
        })
    }

    handleAddReview = () => {
        axios.post('/reviews',
          {
              id: 0,
              applicationId: this.state.application.id,
              reviewerId: this.props.user?.id,
              score: '1',
              observations: this.state.observations
          })
          .then((r: AxiosResponse) => {
            console.log(r)
        })
    }

    render(){

        const renderDataItemAnswers = this.state.application.answers.map((a: string,index) => {
           return(
                 <TextField
                  id="outlined-read-only-input"
                  label={this.state.grantCall.dataItems[index].name}
                  defaultValue={a}
                  multiline
                  rows={4}
                  InputProps={{
                      readOnly: true,
                  }}
                  variant="outlined"
                  style={{marginTop: '20px'}}
                />)
        })

        return(
        <>
            <Card className="listObjects">
                <CardHeader title={this.state.grantCall.title} style={{textAlign: 'center',color: 'white',marginTop: '10px'}}>
                </CardHeader>
                <Typography variant="h6" style={{marginLeft: '20px',color:'white'}}>ApplicationID: {this.state.application.id}</Typography>
                <CardContent style={{display: 'grid'}}>
                    {renderDataItemAnswers}
                    <Divider  style={{backgroundColor: 'black', marginTop:'20px'}}/>
                    <TextField
                      id="outlined-read-only-input"
                      label="Observations"
                      defaultValue=""
                      multiline
                      rows={4}
                      variant="outlined"
                      style={{marginTop: '30px'}}
                      onChange={(e) => this.handleOnChange(e)}
                    />
                    <div style={{width: '250px',marginTop: '20px',marginLeft: '20px', color: 'white'}}>
                        <Typography id="discrete-slider" gutterBottom>
                            Score
                        </Typography>
                        <Slider
                          defaultValue={0}
                          aria-labelledby="discrete-slider"
                          valueLabelDisplay="auto"
                          step={1}
                          marks
                          min={0}
                          max={5}
                          onChange={(e,value ) => this.handleSliderChange(Number(value))}
                        />
                    </div>
                    <div style={{marginTop: '20px', marginBottom: '20px'}}>
                        <Button className='backButton' onClick={() => this.props.history.goBack()}>BACK</Button>
                        <Button className="greenButton" style={{marginLeft: '490px'}} onClick={() => this.handleAddReview()}>ADD REVIEW</Button>
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

export default withRouter(connect(mapStateToProps)(ReviewForm))