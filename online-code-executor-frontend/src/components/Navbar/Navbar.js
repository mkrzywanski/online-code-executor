import React from 'react';
import PropTypes from 'prop-types';
import styles from './Navbar.module.css';

const Navbar = () => (
  <nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">
      <img src="/docs/4.1/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt=""/>
        Bootstrap
    </a>
</nav>
);

Navbar.propTypes = {};

Navbar.defaultProps = {};

export default Navbar;
