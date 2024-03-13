/*
 * Copyright 2024 HM Revenue & Customs
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
import uk.gov.hmrc.perftests.bai.InsightsRequests.baseUrlFor
import uk.gov.hmrc.perftests.testdata.Relationships.{ATTRIBUTE_VALUE_ONE, ATTRIBUTE_VALUE_TWO}
import uk.gov.hmrc.perftests.testdata.RisklistResponseCodes.ACCOUNT_ON_WATCH_LIST

object GatewayRequests {
  val baseUrl: String = baseUrlFor("bank-account-gateway")

  val checkWatchListViaGateway: HttpRequestBuilder =
    http("Check if account is on watch list")
      .post(s"$baseUrl/check/insights")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "bai-performance-tests")
      .body(StringBody("""|{
           |  "sortCode": "${sortCode}",
           |  "accountNumber": "${accountNumber}"
           |}
           |""".stripMargin))
      .asJson
      .check(status.is(200))
      .check(jsonPath("$.riskScore").is("${riskScore}"))
      .check(jsonPath("$.reason").is("${reason}"))

  val checkIppAccountOnWatchListViaGateway: HttpRequestBuilder =
    http("Check if account is on watch list")
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
      .check(jsonPath("$.insights.relationships[0].attribute").is("PayeReference"))
      .check(jsonPath("$.insights.relationships[0].count").is("1018"))
      .check(jsonPath("$.insights.relationships[0].attributeValues[0]").is(ATTRIBUTE_VALUE_ONE))
      .check(jsonPath("$.insights.relationships[1].attribute").is("paye_ref"))
      .check(jsonPath("$.insights.relationships[1].count").is("1270"))
      .check(jsonPath("$.insights.relationships[1].attributeValues[0]").is(ATTRIBUTE_VALUE_TWO))
}
