import React from 'react';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Image from 'react-bootstrap/Image';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';
import Board from '../component/Board';
import '../css/game.css';
import PropTypes from 'prop-types';


class Game extends React.Component {
    constructor() {
        super();
        this.state = {
            board: null,
            height: 8,
            width: 8,
            mines: 10
        }
        this.inputHeight = React.createRef(); 
        this.inputWidth = React.createRef(); 
        this.inputBombs = React.createRef(); 
        this.handleClick = this.handleClick.bind(this);
    }

    handleChangeWidth=()=> 
    {
        // this.setState({width:parseInt(this.inputWidth.current.value)})
    }
    handleChangeBombs=()=>
    {
        // this.setState({mines:parseInt(this.inputBombs.current.value)})
    }

    handleChangeHeight=()=>
    {
        // this.setState({height:parseInt(this.inputHeight.current.value)})
    }

    handleClick() {
        // console.log(this.state)
        // let newBoard = <Board height = {this.state.height} width = {this.state.width} mines = {this.state.mines}/>;
        // this.setState ({
        //     board: newBoard
        // })
        // console.log("handleClick",this.state)
        this.setState({
            height: parseInt(this.inputHeight.current.value),
            width: parseInt(this.inputWidth.current.value),
            mines: parseInt(this.inputBombs.current.value)
        })
    }
    getBoard(){
        return this.state.board;
    }
    render() {
        return (
            <Container id="header" >
                <Container className="d-flex justify-content-center">
                    <Image src="http://www.mayansoftware.com/minesweeperisland/header1.png" ></Image>
                </Container>
                <Container>
                    <Row >
                        <Col >
                            <InputGroup >
                                <InputGroup.Prepend>
                                    <InputGroup.Text className="label">Height</InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl ref={this.inputHeight}  type="number" onChange={() => this.handleChangeHeight()}
  />
                            </InputGroup>
                        </Col>
                        <Col>
                            <InputGroup >
                                <InputGroup.Prepend>
                                    <InputGroup.Text className="label">Width</InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl  ref={this.inputWidth}  type="number" onChange={() => this.handleChangeWidth()}  />
                            </InputGroup>
                        </Col>
                        <Col>
                            <InputGroup >
                                <InputGroup.Prepend>
                                    <InputGroup.Text className="label">Bombs</InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl  ref={this.inputBombs} type="number"  onChange={() => this.handleChangeBombs()}/>
                            </InputGroup>
                        </Col>
                        <Col  className="d-flex justify-content-center">
                            <Button variant="primary" onClick={this.handleClick}>Generate</Button>
                        </Col>
                    </Row>
                </Container>
                <Container className="mt-5 mb-auto" >
                    {/* {this.state.board} */}
                    <Board height = {this.state.height} width = {this.state.width} mines = {this.state.mines}></Board>
                </Container>
            </Container>

        );
    }

}

export default Game;
Game.propTypes = {
    height: PropTypes.number,
    width: PropTypes.number,
    mines: PropTypes.number
  }
  