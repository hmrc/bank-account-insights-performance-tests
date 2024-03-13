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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.bai.GatewayRequests.checkWatchListViaGateway
import uk.gov.hmrc.perftests.bai.InsightsRequests.{checkWatchListThroughProxy, checkWatchListThroughProxyWithRoute}
import uk.gov.hmrc.perftests.bai.IppRequests.{checkAccountNotOnWatchListThroughIppProxy, checkAccountNotOnWatchListThroughIppProxyWithBigResponses, checkAccountOnWatchListThroughIppProxy, checkAccountOnWatchListThroughIppProxyWithInsightsRoute}

class BAISimulation extends PerformanceTestRunner {

  setup("check-watch-list-gateway", "Check watch list via Gateway") withRequests
    checkWatchListViaGateway

  setup("check-watch-list-through-proxy", "Check watch list through proxy") withRequests
    checkWatchListThroughProxy

  setup("check-watch-list-through-proxy-with-route", "Check watch list through proxy with route") withRequests
    checkWatchListThroughProxyWithRoute

  setup("check-account-on-watch-list-through-ipp-proxy", "Check account on watch list through ipp proxy") withRequests
    checkAccountOnWatchListThroughIppProxy

  setup(
    "check-account-not-on-watch-list-through-ipp-proxy",
    "Check account not on watch list through ipp proxy"
  ) withRequests
    checkAccountNotOnWatchListThroughIppProxy

  setup(
    "check-account-on-watch-list-through-ipp-proxy-with-route",
    "Check account on watch list through ipp proxy through insights route"
  ) withRequests
    checkAccountOnWatchListThroughIppProxyWithInsightsRoute

  setup(
    "check-account-not-on-watch-list-through-ipp-proxy-with-big-responses",
    "Check account not on watch list through ipp proxy with big responses"
  ) withRequests checkAccountNotOnWatchListThroughIppProxyWithBigResponses

  runSimulation()
}
