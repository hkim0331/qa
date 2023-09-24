#!/bin/sh
if [ -z "$1" ]; then
    echo "usage: $0 <version>"
    exit
fi

if [ -x "${HOMEBREW_PREFIX}/bin/gsed" ]; then
    SED="${HOMEBREW_PREFIX}/bin/gsed -E"
else
    SED="/usr/bin/sed -E"
fi

set -eu

${SED} -E -i "s|^(\(defproject .+) .+|\1 \"$1\"|" project.clj

now=`date '+%F %T'`
echo ${now}
${SED} -E -i \
       -e "s|(\(def \^:private version).*|\1 \"$1\")|" \
       -e "s|(\(def \^:private updated-at).*|\1 \"$now\")|"  src/qa/view/page.clj
