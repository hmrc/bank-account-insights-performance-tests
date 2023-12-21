
# bank-account-insights-performance-tests

Performance tests for the bank-account-insights suite of services.

NOTE: These test rely on specific data in `staging` environment.
If the data is missing then it can be reloaded using the data files in `src/test/resources/data/`.
Specifically

|file|type of data|
|----|------------|
|bank_account_ipp.csv| ipp relationship data|
|bank_account_reject.csv| watchlist data|
|accounts.csv|used to drive the watchlist simulation|

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
