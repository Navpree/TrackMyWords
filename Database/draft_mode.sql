SET SQL_SAFE_UPDATES=0;

UPDATE Song SET draft_mode = 0 WHERE draft_mode is null;
SET SQL_SAFE_UPDATES=1;