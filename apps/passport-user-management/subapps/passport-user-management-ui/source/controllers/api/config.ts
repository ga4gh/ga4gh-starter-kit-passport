import { Request, Response, NextFunction } from 'express';

const getConfig = async (req: Request, res: Response, next: NextFunction) => {
    return res.status(200).json({
        message: 'future home of the API config endpoint'
    })
};

export default {
    getConfig
};
