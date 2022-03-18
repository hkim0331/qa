#!/bin/sh
PORT=3024 \
DATABASE_URL=jdbc:postgresql://db/l22?user=postgres \
lein run

