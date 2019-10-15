import React from 'react';
import Square from './Square';
import PropTypes from 'prop-types';
import '../css/board.css';




class Board extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      height: this.props.height,
      width: this.props.width,
      mines: this.props.mines
    }
    this.createBoardData = this.createBoardData.bind(this);
  }

  renderBoard() {
    if(this.props.height <=0 || this.props.width <=0 || this.props.height > 50  || this.props.width > 50)
    {
      alert("So Cot va So Dong khong the am hoac lon hon 50");
      return;
    }
    if(this.props.height * this.props.width <= this.props.mines)
    {
      alert("Bom vuot qua pham vi");
      return;
    }
    return this.createBoardData(this.props.height, this.props.width, this.props.mines).map((datarow, i) => (
      <div key={i} className="game-row">
        {
          datarow.map((dataItem, j) => (
            <Square key={i * datarow.length + j} type={dataItem} ></Square>
          ))
        }
      </div>
    ));
  }
  render() {
    return (
      <div>
        {this.renderBoard()}
      </div>
    );
  }
  createBoardData(height, width, mines) {
    let data = this.createEmptyData(height, width);
    data = this.createMinesData(data, height, width, mines);
    data = this.getNeighboursMines(data, height, width);
    return data;
  }

  createEmptyData(height, width) {
    let data = [];
    for (let i = 0; i < width; i++) {
      data.push([]);
      for (let j = 0; j < height; j++) {
        data[i][j] = 0;
      }
    }
    return data;
  }
  createMinesData(data, height, width, mines) {
    let countMines = 1;
    let h, w = 0;
    while (countMines <= mines) {
      h = Math.floor(Math.random() * height);
      w = Math.floor(Math.random() * width);
      if (data[w][h] !== this.props.mine) {
        countMines = countMines + 1;
        data[w][h] = this.props.mine;
      }
    }
    return data;
  }
  getNeighboursMines(data, height, width) {
    for (let i = 0; i < width; i++) {
      for (let j = 0; j < height; j++) {
        if (data[i][j] !== this.props.mine) {
          data[i][j] = this.getNumberMine(data, i, j);
        }
      }
    }
    return data;
  }
  getNumberMine(data, w, h) {
    let countMines = 0;
    //up
    if (w > 0) {
      if (data[w - 1][h] === this.props.mine)
        countMines = countMines + 1;
    }
    //down
    if (w < this.props.height - 1) {
      if (data[w + 1][h] === this.props.mine)
        countMines = countMines + 1;
    }

    //left
    if (h > 0) {
      if (data[w][h - 1] === this.props.mine)
        countMines = countMines + 1;
    }

    //right
    if (h < this.props.width - 1) {
      if (data[w][h + 1] === this.props.mine)
        countMines = countMines + 1;
    }

    // top left
    if (w > 0 && h > 0) {
      if (data[w - 1][h - 1] === this.props.mine)
        countMines = countMines + 1;
    }

    // top right
    if (w > 0 && h < this.props.width - 1) {
      if (data[w - 1][h + 1] === this.props.mine)
        countMines = countMines + 1;
    }

    // bottom right
    if (w < this.props.height - 1 && h < this.props.width - 1) {
      if (data[w + 1][h + 1] === this.props.mine)
        countMines = countMines + 1;
    }

    // bottom left
    if (w < this.props.height - 1 && h > 0) {
      if (data[w + 1][h - 1] === this.props.mine)
        countMines = countMines + 1;
    }
    return countMines;
  }
}
Board.propTypes = {
  height: PropTypes.number,
  width: PropTypes.number,
  mines: PropTypes.number,
}
Board.defaultProps = {
  mine: -1
};
export default Board;