#!/bin/sh
psql -U et -W -h localhost qa < $1
