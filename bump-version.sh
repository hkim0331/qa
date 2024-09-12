#!/bin/sh
if [ -z "$1" ]; then
    echo "usage: $0 <version>"
    exit
fi

if [ -x "${HOMEBREW_PREFIX}/bin/gsed" ]; then
    SED="${HOMEBREW_PREFIX}/bin/gsed -E -i"
else
    SED="/usr/bin/sed -E -i"
fi

set -eu

${SED} "s|^(\(defproject .+) .+|\1 \"$1\"|" project.clj

now=`date '+%F %T'`
${SED} -e "s|(\(def \^:private version).*|\1 \"$1\")|" \
       -e "s|(\(def \^:private updated).*|\1 \"$now\")|"  src/qa/view/page.clj

# CHANGELOG.md
VER=$1
TODAY=`date +%F`
${SED} -i -e "/SNAPSHOT/c\
## ${VER} / ${TODAY}" CHANGELOG.md
