import React, {ChangeEvent, Component} from 'react';
import '../../App.css';
import {ApplicationI, GrantCallI, ReviewI, StudentI} from "../../DTOs";
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
    grantCall: GrantCallI
    application: ApplicationI
    student: StudentI | undefined
    observations: string
    score: number
    successMessage: boolean
}


class ReviewForm extends Component<IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI, student: StudentI | undefined}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{},any,{application: ApplicationI, grantCall: GrantCallI, student: StudentI | undefined}> & IStateStore) {
        super(props);
        this.state = {
            grantCall: this.props.location.state.grantCall,
            application: this.props.location.state.application,
            observations: "",
            score: 0,
            successMessage: false,
            student: undefined
        }
    }

    componentDidMount() {
        this.fetchStudent()
    }

    fetchStudent(){
        axios.get(`/students/${this.state.application.studentId}`).then((r: AxiosResponse) => {
            console.log(r.data)
            this.setState({
                ...this.state,
                student: r.data
            })
        })
    }

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
        const review: ReviewI = {
            id: 0,
            applicationId: this.state.application.id,
            reviewerId: this.props.user!!.id,
            score: this.state.score,
            observations: this.state.observations

        }

        axios.post('/reviews',review)
          .then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                successMessage: true
            })
        })
    }

    redirectToStudentPage = () => {
        this.props.history.push(`/student/${this.state.student?.id}`,{
            application: this.state.application,
            grantCall: this.state.grantCall,
            student: this.state.student
        })
    }


    render(){
        const renderSuccessMessage = <SuccessMessage
          message={`Your Review to application ${this.state.application.id} was submited with success..
          You'll be shortly redirected to the applications list.`}
          path={`/grantcall/${this.state.grantCall.id}/applications`}
          state={{grantCall: this.state.grantCall}}
        />

        if(this.state.successMessage)
            return renderSuccessMessage

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
                <div>
                    <Typography variant="h6" style={{marginLeft: '20px',color:'white'}}>ApplicationID: {this.state.application.id}</Typography>
                    <Link style={{display: 'flex',flexDirection: 'row-reverse'}} onClick={() => this.redirectToStudentPage()}>
                        <Typography variant="h6" color='primary' style={{
                            marginTop: '-31px',
                            marginRight: '20px'
                        }}>Student: {this.state.student?.name}</Typography>
                    </Link>
                </div>
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
    institutions: state.institutions,
    grantCalls: state.grantCalls
});

export default withRouter(connect(mapStateToProps)(ReviewForm))