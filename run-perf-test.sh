#!/usr/bin/env bash

SMOKE_TEST=true
FULL=$1
LOCAL=$2

if [ -z "$FULL" ]; then
  echo "boolean value not set, defaulting to Smoke Test: $SMOKE_TEST"
  echo ""
fi

sbt -Dperftest.runSmokeTest="${FULL:=$SMOKE_TEST}" -DrunLocal="${LOCAL:=true}" gatling:test
