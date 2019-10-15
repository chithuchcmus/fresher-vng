import React from 'react';
import PropTypes from 'prop-types';
import '../css/square.css';

class Square extends React.Component {

  getValueFromType() {
    const value = this.props.type;
    if (value === this.props.mine)
      return "💣";
    return value;
  }
  render() {
    let ClassName = "square";
    return (
      <div
        className={ClassName}
      >
        {this.getValueFromType()}
      </div>
    );
  }
}

// Specifies the default values for props:
Square.defaultProps = {
  mine: -1,
  empty: 0
};
Square.propTypes = {
  type: PropTypes.number
}


export default Square;