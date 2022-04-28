import express from 'express';
import getUI from '../controllers/ui';

const router = express.Router();

router.get("/*", getUI);

export = router;