#!/bin/sh
echo 'alter table answers add column g int default 1' | \
  psql -U ${QA_USER} -W -h localhost qa
