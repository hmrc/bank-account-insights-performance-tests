/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.bai

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.testdata.Relationships.{ATTRIBUTE_VALUE_ONE, ATTRIBUTE_VALUE_TWO}
import uk.gov.hmrc.perftests.testdata.RisklistResponseCodes.{ACCOUNT_NOT_ON_WATCH_LIST, ACCOUNT_ON_WATCH_LIST}

object IppRequests extends ServicesConfiguration {

  val bankAccountRetriever = new BankAccountRetriever()
  val BankAccount(chosenSortCode, chosenAccountNumber) = bankAccountRetriever.getABankAccount

  val riskySortCode = "999998"
  val riskyAccountNumber = "44311677"

  val baseUrl: String = baseUrlFor("bank-account-insights-proxy")

  val checkAccountOnWatchListThroughIppProxy: HttpRequestBuilder =
    http("Check if account is on watch list through ipp proxy")
      .post(s"$baseUrl/ipp")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "bai-performance-tests")
      .body(StringBody(
        """|{
           |  "sortCode": "999998",
           |  "accountNumber": "44311677"
           |}
           |""".stripMargin))
      .asJson
      .check(status.is(200))
      .check(jsonPath("$.insights.risk.riskScore").is("100"))
      .check(jsonPath("$.insights.risk.reason").is(ACCOUNT_ON_WATCH_LIST))
      .check(jsonPath("$.insights.relationships[0].attribute").is("sa_utr"))
      .check(jsonPath("$.insights.relationships[0].count").is("1"))
      .check(jsonPath("$.insights.relationships[0].attributeValues[0]").is(ATTRIBUTE_VALUE_ONE))
      .check(jsonPath("$.insights.relationships[1].attribute").is("SelfAssessmentUTR"))
      .check(jsonPath("$.insights.relationships[1].count").is("1"))
      .check(jsonPath("$.insights.relationships[1].attributeValues[0]").is(ATTRIBUTE_VALUE_TWO))

  val checkAccountNotOnWatchListThroughIppProxy: HttpRequestBuilder =
    http("Check if account is not on watch list through ipp proxy")
      .post(s"$baseUrl/ipp")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "bai-performance-tests")
      .body(StringBody(
        """|{
          "sortCode": "404784",
          "accountNumber": "70872490"
        }
        """.stripMargin))
      .asJson
      .check(status.is(200))
      .check(jsonPath("$.insights.risk.riskScore").is("0"))
      .check(jsonPath("$.insights.risk.reason").is(ACCOUNT_NOT_ON_WATCH_LIST))
      .check(jsonPath("$.insights.relationships[0].attribute").count.is(0))


  val checkAccountOnWatchListThroughIppProxyWithInsightsRoute: HttpRequestBuilder =
    http("Check if account is on watch list through ipp proxy via insights route")
      .post(s"$baseUrl/bank-account-insights/ipp")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "bai-performance-tests")
      .body(StringBody(
        """|{
           |  "sortCode": "999998",
           |  "accountNumber": "44311677"
           |}
           |""".stripMargin))
      .asJson
      .check(status.is(200))
      .check(jsonPath("$.insights.risk.riskScore").is("100"))
      .check(jsonPath("$.insights.risk.reason").is(ACCOUNT_ON_WATCH_LIST))
      .check(jsonPath("$.insights.relationships[0].attribute").is("sa_utr"))
      .check(jsonPath("$.insights.relationships[0].count").is("1"))
      .check(jsonPath("$.insights.relationships[0].attributeValues[0]").is(ATTRIBUTE_VALUE_ONE))
      .check(jsonPath("$.insights.relationships[1].attribute").is("SelfAssessmentUTR"))
      .check(jsonPath("$.insights.relationships[1].count").is("1"))
      .check(jsonPath("$.insights.relationships[1].attributeValues[0]").is(ATTRIBUTE_VALUE_TWO))
}
