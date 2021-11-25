import express from "express";
import configRouter from './api/config';

const router = express.Router();

router.use("/config", configRouter);

export = router;
