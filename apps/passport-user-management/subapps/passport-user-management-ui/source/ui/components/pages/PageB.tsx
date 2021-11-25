import React from 'react';
import { Link } from 'react-router-dom';

const PageB = () => {
    return (
        <div>
            <h1>This is Page B</h1>
            <Link to="/">Home</Link>
            <Link to="/a">Page A</Link>
            <Link to="/b">Page B</Link>
        </div>
    )
}

export default PageB;
