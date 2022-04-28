import React from 'react';
import ReactDOMServer from 'react-dom/server';
import { Request, Response, NextFunction } from 'express';
import path from 'path';
import fs from "fs";
import App from '../ui/App';

const getUI = async (req: Request, res: Response, next: NextFunction) => {
    const reactApp = ReactDOMServer.renderToStaticMarkup(<App />);
    const indexFile = path.join(__dirname, "public", "react.html");
    fs.readFile(indexFile, 'utf8', (err, data) => {
        if (err) {
            console.error('Could not load React App', err);
            return res.status(500).send('Could not load React App');
        }

        return res.send(
            data.replace(`<div id="root"></div>`, `<div id="root">${reactApp}</div>`)
        );
    });
};

export default getUI;