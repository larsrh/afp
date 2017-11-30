#!/usr/bin/env bash

set -e

git diff --exit-code > /dev/null
git diff --cached --exit-code > /dev/null
git submodule update --remote
git commit -am "update afp"
