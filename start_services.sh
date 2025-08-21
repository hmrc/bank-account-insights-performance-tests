#!/usr/bin/env bash

sm2 --start BANK_ACCOUNT_INSIGHTS_PROXY BANK_ACCOUNT_INSIGHTS BANK_ACCOUNT_GATEWAY INTERNAL_AUTH --appendArgs '{
        "BANK_ACCOUNT_INSIGHTS_PROXY": [
            "-J-Dauditing.consumer.baseUri.port=6001",
            "-J-Dauditing.consumer.baseUri.host=localhost",
            "-J-Dmicroservice.services.access-control.enabled=true",
            "-J-Dmicroservice.services.access-control.allow-list.0=bank-account-gateway",
            "-J-Dmicroservice.services.access-control.allow-list.1=bai-performance-tests"
        ],
        "BANK_ACCOUNT_INSIGHTS": [
            "-J-Dmicroservice.bank-account-insights.database.dbName=postgres",
            "-J-Dmicroservice.bank-account-insights.database.use-canned-data=true",
            "-J-Dauditing.enabled=true"
        ]
    }'