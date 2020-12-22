import React, {ChangeEvent, Component} from 'react';
import '../../App.css';
import {IStateStore} from "../../store/types";
import {RouteComponentProps, withRouter} from "react-router";
import {connect} from "react-redux";
import {CurriculumI, CvItemI, StudentI} from "../../DTOs";
import axios, {AxiosError, AxiosResponse} from "axios";
import {Button, Card, CardHeader, Modal, Snackbar, TextField} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import { IconButton } from '@material-ui/core';
import AddCircleOutlineRoundedIcon from '@material-ui/icons/AddCircleOutlineRounded';
import EditIcon from '@material-ui/icons/Edit';
import { Alert } from '@material-ui/lab';
import DeleteIcon from '@material-ui/icons/Delete';

interface IProps {

}

interface IState {
    student: StudentI | undefined
    createClicked: boolean
    curriculum: CurriculumI | undefined | null
    openModal: boolean
    fieldName: string
    canEdit: boolean
    updateSuccess: boolean
    invalidCV: boolean
    cvCreated: boolean
}


class StudentCV extends Component<IProps & RouteComponentProps<{id: string}> & IStateStore, IState>{

    constructor(props: IProps & RouteComponentProps<{id: string}> & IStateStore) {
        super(props);

        this.state = {
            student: undefined,
            createClicked: false,
            curriculum: undefined,
            openModal: false,
            fieldName: "",
            canEdit: false,
            updateSuccess: false,
            invalidCV: false,
            cvCreated: false
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

    componentDidUpdate(prevProps: Readonly<IProps & RouteComponentProps<{ id: string }> & IStateStore>, prevState: Readonly<IState>, snapshot?: any) {

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

    handleOnClickOK() {
        let newItem: CvItemI = {
            id: 0,
            item: this.state.fieldName,
            value: ""
        }
        this.setState({
            ...this.state,
            curriculum: {
                id: this.state.curriculum!!.id,
                items: [...this.state.curriculum!!.items, newItem]
            },
            openModal: false
        })
    }

    handleOnClickAddItem = () => {

        this.setState({
            ...this.state,
            openModal: true
        })
    }

    handleClose(){
        this.setState({
            ...this.state,
            openModal: false
        })
    }

    handleFieldNameChange(e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>){
        this.setState({
            ...this.state,
            fieldName: e.target.value
        })
    }

    handleOnChange(e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>, index: number){
        const newArray: CvItemI[] = this.state.curriculum!!.items
        newArray[index] = {
            ...newArray[index],
            value: e.target.value
        }

        let id
        if(this.state.curriculum == undefined){
            id = 0
        }
        else {
            id = this.state.curriculum.id
        }

        this.setState({
            curriculum: {
                id: id,
                items: newArray
            }
        })
    }

    handleCreateCVClick() {
        if(!this.verifyIfCvIsValid()){
            this.setState({
                ...this.state,
                invalidCV: true
            })
            return
        }
        axios.post(`/students/${this.props.user!!.id}/cv`,this.state.curriculum).then((r: AxiosResponse) => {
            this.setState({
                ...this.state,
                cvCreated: true
            })
        }).catch((e: AxiosError) => {
            console.log(e)
        })
    }

    handleOnClickEdit(){
        this.setState({
            canEdit: !this.state.canEdit
        })
    }

    handleUpdateCV() {
        if(!this.verifyIfCvIsValid()){
            this.setState({
                ...this.state,
                invalidCV: true
            })
            return
        }
        axios.put(`/students/${this.props.user!!.id}/cv`,this.state.curriculum).then((r:AxiosResponse) => {
            this.setState({
                ...this.state,
                updateSuccess: true
            })
        })
    }

    handleOnCloseSuccessToast(){
        this.setState({
            ...this.state,
            updateSuccess: false
        })
    }

    handleDelete(index: number){
        const newArray: CvItemI[] = this.state.curriculum!!.items
        newArray.splice(index,1)
        this.setState({
            ...this.state,
            curriculum:{
                id: this.state.curriculum!!.id,
                items: newArray
            }
        })
    }

    verifyIfCvIsValid(): boolean{
        if(this.state.curriculum == undefined || this.state.curriculum.items.length == 0){
            return false
        }
        else return true
    }

    handleCloseInvalidCV(){
        this.setState({
            ...this.state,
            invalidCV: false
        })
    }

    handleCloseCvCreated(){
        this.setState({
            ...this.state,
            cvCreated: false
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

        const modalFieldName =
          <Modal
            open={this.state.openModal}
            onClose={() => this.handleClose()}
            aria-labelledby="simple-modal-title"
            aria-describedby="simple-modal-description"
           >
              <Card style={{backgroundColor: '#353535',margin: 'auto',width: '500px',marginTop: '200px',textAlign: "center"}}>
                  <CardHeader style={{color:'white'}} title="Insert field name"></CardHeader>
                  <CardContent>
                      <TextField id="outlined-basic" label="Item Name" variant="outlined"
                                 onChange={(e) => this.handleFieldNameChange(e)}/>
                  </CardContent>
                  <div style={{marginBottom: '30px', marginTop: '30px'}}>
                      <Button className="greenButton" style={{}}
                        onClick={() => this.handleOnClickOK()}
                      >Ok</Button>
                      <Button style={{backgroundColor:'red', color:'white',marginLeft: '20px'}}
                        onClick={() => this.handleClose()}
                      >Cancel</Button>
                  </div>
              </Card>
          </Modal>

        const renderCvCreateForm =
          <>
              <IconButton onClick={() => this.handleOnClickAddItem()}  style={{width: '100px'}}>
                  <AddCircleOutlineRoundedIcon color='primary'/>
               </IconButton>
              {this.state.curriculum?.items.map((i: CvItemI,index) => {
                  return(
                    <>
                        <DeleteIcon color='secondary' onClick={() => this.handleDelete(index)}
                            style={{
                            marginLeft: '647px',
                            marginTop: '13px'
                        }} />
                        <TextField
                          style={{marginTop: '20px'}}
                          id={i.item + index}
                          label={i.item}
                          multiline
                          rows={4}
                          defaultValue={i.value}
                          variant="outlined"
                          onChange={(e) => this.handleOnChange(e, index)}
                        />
                    </>
                  )
              })}
              <div style={{marginTop: '30px',marginLeft: 'auto',marginRight: 'auto'}}>
                  <Button className="greenButton" onClick={ () => this.handleCreateCVClick()}>CREATE CV</Button>
              </div>
          </>

        const updateButton =
          <>
          <div style={{marginTop: '30px',marginLeft: 'auto',marginRight: 'auto'}}>
              <Button className="greenButton" onClick={() => this.handleUpdateCV()}>Update</Button>
          </div>
          </>

        let button = <></>

        if(this.state.canEdit)
            button = updateButton

        const renderCvNotNull =
        <>
            <EditIcon color='primary' style={{ marginBottom: '10px'}} onClick={() => this.handleOnClickEdit()}/>
            {this.state.canEdit ? (<IconButton onClick={() => this.handleOnClickAddItem()}  style={{width: '100px'}}>
                <AddCircleOutlineRoundedIcon color='primary'/>
            </IconButton>): <></>}
            {this.state.curriculum?.items.map((i: CvItemI, index) => {
                return(
                <>
                    {this.state.canEdit? (<DeleteIcon color='secondary' onClick={() => this.handleDelete(index)} style={{
                        marginLeft: '647px',
                        marginTop: '13px'

                    }}/>): <></>}
                    <TextField
                          id={i.item + index}
                          style={{
                              marginTop: '10px'
                          }}
                          label={i.item}
                          InputProps={{
                              readOnly: !this.state.canEdit,
                          }}
                          multiline
                          rows={4}
                          defaultValue={i.value}
                          variant="outlined"
                          onChange={(e) => this.handleOnChange(e, index)}
                    />
                </>
                )
            })}
            {button}
        </>
        let content
        if(this.doesStudentHaveCV()){
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
                      {modalFieldName}
                      <Snackbar open={this.state.cvCreated} autoHideDuration={3000} onClose={() => this.handleCloseCvCreated()}>
                          <Alert onClose={() => this.handleCloseCvCreated()} severity="success">
                              CV was created successfully!
                          </Alert>
                      </Snackbar>
                      <Snackbar open={this.state.updateSuccess} autoHideDuration={3000} onClose={() => this.handleOnCloseSuccessToast()}>
                          <Alert onClose={() => this.handleOnCloseSuccessToast()} severity="success">
                              CV was updated successfully!
                          </Alert>
                      </Snackbar>
                      <Snackbar open={this.state.invalidCV} autoHideDuration={3000} onClose={() => this.handleCloseInvalidCV()}>
                          <Alert onClose={() => this.handleCloseInvalidCV()} severity="error">
                              Invalid CV !!!!
                          </Alert>
                      </Snackbar>
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

