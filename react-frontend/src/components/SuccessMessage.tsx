import React, {Component} from 'react';
import '../App.css';
import {RouteComponentProps, withRouter} from "react-router";
import {Button, Card, CardHeader, FormControlLabel, FormGroup, Switch, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import CheckBoxOutlinedIcon from '@material-ui/icons/CheckBoxOutlined';

interface IProps {
    message: string
    path: string
}


class SuccessMessage extends Component<IProps & RouteComponentProps<{}>>{

    constructor(props: IProps & RouteComponentProps<{}>) {
        super(props);

    }

    componentDidMount() {
        setTimeout(() => this.props.history.push(this.props.path), 3000)
    }

    render(){
        return(<>
            <Card className="listObjects">
                <CardContent style={{ height: '500px',textAlign: 'center'}}>
                    <CheckBoxOutlinedIcon style={{color: 'green'}} className='successIcon'/>
                    <Typography variant='h5' style={{color: 'white'}}>{this.props.message}</Typography>
                </CardContent>
            </Card>
               </>)
    }

}

export default  withRouter(SuccessMessage)