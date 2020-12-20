import React, {ChangeEvent, Component} from 'react';
import '../../App.css';
import {ApplicationI, DataItemI} from "../../DTOs";
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, Checkbox, FormControlLabel, Snackbar, TextField} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import axios, {AxiosError, AxiosResponse} from 'axios'
import SuccessMessage from "../../components/SuccessMessage";
import {Alert} from "@material-ui/lab";

interface IProps {

}

interface IState {
    id: string
    title: string
    dataItems: DataItemI[]
    answers: string[]
    success: boolean
    errorField: boolean
    errorConflict: boolean
}


class ApplicationForm extends Component<IProps & RouteComponentProps<{id: string},any,{title: string,dataItems: DataItemI[]}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{title: string,dataItems: DataItemI[]}> & IStateStore) {
        super(props);
        let dataItems = this.props.location.state.dataItems
        let answers = []
        for(let i = 0; i < dataItems.length; i++){
            answers[i] = ""
            if(dataItems[i].datatype.toLowerCase() == 'boolean'){
                answers[i] = 'false'
            }
        }

        this.state = {
            id: this.props.match.params.id,
            title: this.props.location.state.title,
            dataItems: dataItems,
            answers: answers,
            success: false,
            errorField: false,
            errorConflict: false
        }
    }

    handleChange = (e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>,index: number) => {
        const newAnswersArray = this.state.answers
        newAnswersArray[index] = e.target.value
        this.setState({
            ...this.state,
            answers: newAnswersArray
        })
        console.log(this.state.answers)
    }

    handleOnClick = () => {
        for(let i = 0; i < this.state.dataItems.length; i++){
            if(this.state.dataItems[i].mandatory == true){
                console.log(this.state.answers[i])
                if(this.state.answers[i] == ""){
                    this.setState({
                        ...this.state,
                        errorField: true
                    })
                    return;
                }
            }
        }
        const application: ApplicationI = {
            id: 0,
            submissionDate: new Date(Date()),
            status: -1,
            decision: false,
            grantCallId: Number(this.state.id),
            studentId: this.props.user!!.id,
            reviews: [],
            meanScores: 0,
            answers: this.state.answers,
            justification: "adsdas"
        }
        axios.post('/applications',application).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                success: true
            })
        }).catch((e: AxiosError) => {
            this.setState({
                ...this.state,
                errorConflict: true
            })
            console.log(e)
        })
    }

    handleChangeBool = (index: number) => {
        let res;
        if(this.state.answers[index] == 'true')
            res = 'false'
        else
            res = 'true'
        let newArray = this.state.answers
        newArray[index] = res
        this.setState({
            ...this.state,
            answers: newArray
        })
    }

    handleCloseErrorField(){
        this.setState({
            ...this.state,
            errorField: false
        })
    }

    handleErrorConflict(){
        this.setState({
            ...this.state,
            errorConflict: false
        })
    }



    render() {

        const dataItems =  this.state.dataItems.map((d: DataItemI,index) => {

            if(d.datatype.toLowerCase() == 'string'){
                 return( <TextField
                      required={d.mandatory}
                      style={{marginTop: '50px'}}
                      id={index.toString()}
                      label={d.name}
                      multiline
                      rows={4}
                      defaultValue=""
                      variant="outlined"
                      onChange={(e) => this.handleChange(e,index)}
                      />)
            }

            if(d.datatype.toLowerCase() == 'boolean'){
                return(
                  <FormControlLabel style={{marginTop: '50px', color:'white'}}
                    control={
                        <Checkbox
                          color='primary'
                          icon={<CheckBoxOutlineBlankIcon fontSize="small" />}
                          checkedIcon={<CheckBoxIcon fontSize="small" />}
                          checked={this.state.answers[index] == 'true'}
                          onChange={() => this.handleChangeBool(index)}
                        />
                    }
                    label={d.name}
                  />
            )
            }
            if(d.datatype.toLowerCase() == 'number'){
                return(
                  <TextField
                  required={d.mandatory}
                  style={{marginTop: '50px',width: '125px'}}
                  id={index.toString()}
                  label={d.name}
                  multiline
                  rows={1}
                  defaultValue=""
                  variant="outlined"
                  onChange={(e) => this.handleChange(e,index)}
                />)
            }


        })
        let renderSuccessMessage = <SuccessMessage path='/myapplications' message=
                                    {`Your Application to grantCall ${this.state.title} was saved with success. You'll be redirected to your applications page shortly`}
                                    state={{}}
        />

        if(!this.state.success) {
            return (
              <Card key={this.state.id} className="listObjects">
                  <CardHeader title={this.state.title} style={{textAlign: 'center', color: 'white'}}/>
                  <CardContent key={this.state.id + "content"} style={{display: 'flex', flexDirection: 'column'}}>
                      {dataItems}
                      <Button className='greenButton applicationSaveB' onClick={this.handleOnClick}>Save</Button>
                      <Snackbar open={this.state.errorField} autoHideDuration={3000} onClose={this.handleCloseErrorField.bind(this)}>
                          <Alert onClose={this.handleCloseErrorField.bind(this)} severity="error">
                              A mandatory field was left blank, please try again..
                          </Alert>
                      </Snackbar>
                      <Snackbar open={this.state.errorConflict} autoHideDuration={3000} onClose={this.handleErrorConflict.bind(this)}>
                          <Alert onClose={this.handleErrorConflict.bind(this)} severity="error">
                              You already have an application for this grant call!
                          </Alert>
                      </Snackbar>
                  </CardContent>
              </Card>

            )
        }
        else {
            return renderSuccessMessage
        }
    }
}

const mapStateToProps = (state: IStateStore) => ({
    user: state.user,
    institutions: state.institutions,
    grantCalls: state.grantCalls
});

export default withRouter(connect(mapStateToProps)(ApplicationForm))