#!/bin/sh
if [ -z "$1" ]; then
    echo "usage: $0 <version>"
    exit
fi

SED="/bin/sed"
if [ -x "${HOMEBREW_PREFIX}/bin/gsed" ]; then
    SED=${HOMEBREW_PREFIX}/bin/gsed
fi

${SED} -E -i "s/^\(defproject (.+) .+/(defproject \1 \"$1\"/" project.clj

${SED} -E -i "s/^\(def version .*/(def version \"$1\")/" src/qa/view/page.clj