import React, {ChangeEvent, Component} from 'react';
import '../App.css';
import {ApplicationI, DataItemI, GrantCallI} from "../DTOs";
import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {Button, Card, CardHeader, Checkbox, FormControlLabel, TextField} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import axios, {AxiosResponse} from 'axios'
import SuccessMessage from "../components/SuccessMessage";

interface IProps {

}

interface IState {
    id: string
    title: string
    dataItems: DataItemI[]
    application: ApplicationI
    success: boolean
}


class EditApplicationForm extends Component<IProps & RouteComponentProps<{id: string},any,{title: string,dataItems: DataItemI[], application: ApplicationI}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string},any,{title: string,dataItems: DataItemI[],application: ApplicationI}> & IStateStore) {
        super(props);


        this.state = {
            id: this.props.match.params.id,
            title: "",
            dataItems: [],
            application: this.props.location.state.application,
            success: false
        }
    }

    componentDidMount() {
        axios.get(`/grantcalls/${this.state.application.grantCallId}`).then( (r: AxiosResponse) => {
            const grantCall: GrantCallI = r.data
            this.setState({
                ...this.state,
                dataItems: grantCall.dataItems,
                title: grantCall.title
            })
        })
    }

    handleChange = (e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>,index: number) => {
        const newAnswersArray = this.state.application.answers
        newAnswersArray[index] = e.target.value
        this.setState({
            ...this.state,
            application: {...this.state.application, answers: newAnswersArray}
        })
        console.log(this.state.application.answers)
    }

    handleOnClick = () => {
        for(let i = 0; i < this.state.dataItems.length; i++){
            if(this.state.dataItems[i].mandatory == true){
                console.log(this.state.application.answers[i])
                if(this.state.application.answers[i] == ""){
                    alert("A mandatory field is empty")
                    return;
                }
            }
        }
        const application: ApplicationI = {
            id: this.state.application.id,
            submissionDate: this.state.application.submissionDate,
            status: this.state.application.status,
            decision: this.state.application.decision,
            grantCallId: this.state.application.grantCallId,
            studentId: this.state.application.studentId,
            reviews: this.state.application.reviews,
            meanScores: this.state.application.meanScores,
            answers: this.state.application.answers,
            justification: ""
        }

        axios.put(`/applications/${application.id}`,{
            id: application.id,
            submissionDate: application.submissionDate,
            decision: application.decision,
            status: application.status,
            grantCallId: application.grantCallId,
            studentId: application.studentId,
            reviews: application.reviews,
            meanScores: application.meanScores,
            answers: application.answers,
            justification: application.justification
        }).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                success: true
            })
            console.log(r)
        }).catch((e: AxiosResponse) => {
            console.log(e)
        })
    }

    handleChangeBool = (index: number) => {
        let res;
        if(this.state.application.answers[index] == 'true')
            res = 'false'
        else
            res = 'true'
        let newArray = this.state.application.answers
        newArray[index] = res
        this.setState({
            ...this.state,
           application: {...this.state.application, answers: newArray}
        })
    }

    handleOnClickDelete = () => {
        axios.delete(`/applications/${this.state.application.id}`).then((r: AxiosResponse) => {
            this.props.history.goBack()
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
                  defaultValue={this.state.application.answers[index]}
                  variant="outlined"
                  onChange={(e) => this.handleChange(e,index)}
                />)
            }

            if(d.datatype.toLowerCase() == 'boolean'){
                return(
                  <FormControlLabel
                    control={
                        <Checkbox
                          color='primary'
                          icon={<CheckBoxOutlineBlankIcon fontSize="small" />}
                          checkedIcon={<CheckBoxIcon fontSize="small" />}
                          defaultValue={this.state.application.answers[index]}
                          checked={this.state.application.answers[index] == 'true'}
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
                    defaultValue={this.state.application.answers[index]}
                    variant="outlined"
                    onChange={(e) => this.handleChange(e,index)}
                  />)
            }


        })
        let renderSuccessMessage = <SuccessMessage path='/myapplications' message=
          {`Your Application to grantCall ${this.state.title} was edited with success. You'll be redirected to your applications page shortly`}/>

        if(!this.state.success) {
            return (
              <Card key={this.state.id} className="listObjects">
                  <CardHeader title={this.state.title} style={{textAlign: 'center', color: 'white'}}/>
                  <CardContent key={this.state.id + "content"} style={{display: 'flex', flexDirection: 'column'}}>
                      {dataItems}
                      <div style={{marginTop: '40px',display: 'flex', flexDirection: 'row-reverse'}}>
                          <Button className='greenButton' onClick={this.handleOnClick}>SAVE</Button>
                          <Button style={{backgroundColor: 'red',color: 'white',width: '65px',marginLeft:'20px',marginRight:'20px'}} onClick={this.handleOnClickDelete}>DELETE</Button>
                          <Button className='backButton' style={{marginRight:'433px'}} onClick={() => this.props.history.goBack()}>BACK</Button>
                      </div>
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

export default withRouter(connect(mapStateToProps)(EditApplicationForm))