#!/bin/sh

P="psql -U postgres -W -h localhost qa "
${P} -c "drop table drills"
${P} -c "drop table results"
${P} -c "drop table exam_mode"
${P} -c "drop table users"
${P} -c "drop table status"
