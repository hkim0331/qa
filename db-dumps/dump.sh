#!/bin/sh
pg_dump -h localhost -U postgres -W qa > `date +qa-%F.sql`
