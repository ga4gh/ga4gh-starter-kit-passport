import express from 'express';
import controller from '../../controllers/api/config';

const router = express.Router();

router.get("/", controller.getConfig);

export = router;