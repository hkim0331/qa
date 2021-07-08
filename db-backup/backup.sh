#!/bin/sh
pg_dump -h localhost -U postgres -W qa > qa-`date +%F`.sql

