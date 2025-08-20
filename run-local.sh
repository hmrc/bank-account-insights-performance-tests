#!/usr/bin/env bash

SMOKE=${1:-true}

sbt -DrunLocal=true -Dperftest.runSmokeTest=$SMOKE gatling:test \
  -DjourneysToRun.0=bank-account-insights-simulation \
  -DjourneysToRun.1=bank-account-ipp-simulation-local-stubbed
