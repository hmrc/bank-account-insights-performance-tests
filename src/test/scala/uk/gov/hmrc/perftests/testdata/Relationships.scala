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

package uk.gov.hmrc.perftests.testdata

object Relationships {
  val ATTRIBUTE_VALUE_ONE: String = """{"value":"1122334456","numOfOccurrences":1,"lastSeen":"2023-02-27T16:27:45.867"}"""
  val ATTRIBUTE_VALUE_TWO: String = """{"value":"1122334456","numOfOccurrences":1,"lastSeen":"2023-02-27T16:27:45.867"}"""
}

object RisklistResponseCodes {
  val ACCOUNT_ON_WATCH_LIST: String = "ACCOUNT_ON_WATCH_LIST"
  val ACCOUNT_NOT_ON_WATCH_LIST: String = "ACCOUNT_NOT_ON_WATCH_LIST"
}
