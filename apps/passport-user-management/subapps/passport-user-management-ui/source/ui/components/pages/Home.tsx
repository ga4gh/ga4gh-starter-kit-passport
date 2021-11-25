import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div>
            <h1>A new intro message</h1>
            <h2>A second intro message</h2>
            <Link to="/">Home</Link>
            <Link to="/a">Page A</Link>
            <Link to="/b">Page B</Link>
        </div>
    )
}

export default Home;
