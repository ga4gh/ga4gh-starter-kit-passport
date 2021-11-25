import express from 'express';
import controller from '../controllers/ui';

const router = express.Router();

router.get("/", controller.getUI);

export = router;