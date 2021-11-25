import { Request, Response, NextFunction } from 'express';
import axios, { AxiosResponse } from 'axios';

const getUI = async (req: Request, res: Response, next: NextFunction) => {
    return res.status(200).json({
        message: 'future home of the UI'
    })
};

export default {
    getUI
};