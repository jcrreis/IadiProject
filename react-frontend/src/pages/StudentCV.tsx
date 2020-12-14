import React, {Component} from 'react';
import '../App.css';

import {IStateStore} from "../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {CurriculumI, CvItemI, StudentI} from "../DTOs";
import axios, {AxiosResponse} from "axios";
import {Button, Card, CardHeader, TextField} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import { IconButton } from '@material-ui/core';
import AddCircleOutlineRoundedIcon from '@material-ui/icons/AddCircleOutlineRounded';

interface IProps {

}

interface IState {
    student: StudentI | undefined
    createClicked: boolean
    curriculum: CurriculumI | undefined | null
}


class StudentCV extends Component<IProps & RouteComponentProps<{id: string}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string}> & IStateStore) {
        super(props);

        this.state = {
            student: undefined,
            createClicked: false,
            curriculum: undefined
        }
    }

    componentDidMount() {
        axios.get(`/students/${this.props.user?.id}`).then((r: AxiosResponse) => {
            this.setState({
                student: r.data,
                curriculum: r.data.cv
            })
        })
    }



    doesStudentHaveCV(): boolean{
        return this.state.student?.cv !== null
    }

    handleOnClickCreate() {
        this.setState({
            ...this.state,
            createClicked: true,
            curriculum: {
                id: 0,
                items: []
            }
        })
    }

    handleOnClickAddItem = (item: string) => {
        let newItem: CvItemI = {
            id: 0,
            item: item,
            answer: ""
        }
        this.setState({
            ...this.state,
            curriculum: {
                id: this.state.curriculum!!.id,
                items: [...this.state.curriculum!!.items, newItem]
            }
        })
    }


    render(){

        const renderCvNull =
        <Card style={{backgroundColor: '#505050', textAlign:'center',marginTop:'30px'}}>
            <CardHeader title={"You don't have any CV yet"} style={{color: 'white', marginTop:'30px'}} />
            <CardContent>
                <Button className='greenButton' style={{marginBottom: '30px'}} onClick={() => this.handleOnClickCreate()}>Create a CV</Button>
            </CardContent>
        </Card>
        const renderCvCreateForm =
          <>
              <IconButton onClick={() => this.handleOnClickAddItem("NEW ITEM")}  style={{width: '100px'}}>
                  <AddCircleOutlineRoundedIcon color='primary'/>
               </IconButton>
              {this.state.curriculum?.items.map((i: CvItemI) => {
                  return(<TextField label={i.item}/>)
              })}
          </>

        const renderCvNotNull = <>NOT NULL</>
        let content
        if(this.doesStudentHaveCV()){
            console.log(this.state.curriculum)
            content = renderCvNotNull
        }
        else{
            if(this.state.createClicked){
                content = renderCvCreateForm
            }
            else content = renderCvNull
        }
        return(
          <>
              <Card key={"objects"} className="listObjects">
                  <CardHeader title={"CV"} style={{textAlign: 'center', color: 'white'}}/>
                  <CardContent key={"content"} style={{display: 'flex', flexDirection: 'column'}}>
                      {content}
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
})
export default withRouter(connect(mapStateToProps)(StudentCV))

