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

import play.api.libs.json.{Format, Json}
import sttp.client3.quick._
import sttp.client3.HttpClientSyncBackend
import uk.gov.hmrc.perftests.bai.IppRequests.baseUrlFor

import scala.util.Random

final case class BankAccount(sortCode: String, accountNumber: String)

private object BankAccount {
  implicit val bankAccountFormats: Format[BankAccount] = Json.format[BankAccount]
}

final class BankAccountRetriever() {
  private val testBankAccountsUrl: String = s"${baseUrlFor("bank-account-insights")}/test/ipp/bank-accounts"


  private val testBankAccounts = {
    import BankAccount._

    val backend = HttpClientSyncBackend()

    val bodyE = basicRequest
      .header("Accept", "application/json")
      .header("User-Agent", "bai-performance-tests")
      .get(uri"$testBankAccountsUrl")
      .send(backend)
      .body

    bodyE.fold(
      e => throw new IllegalStateException(s"Could not get bank accounts: $e"),
      body => Json.fromJson[List[BankAccount]](Json.parse(body))
    ).fold(
      err => throw new IllegalStateException(s"Could not parse bank accounts json: $err"),
      bas => {
        println(s"|>>> got $bas")
        bas
      }
    )
  }

  def getBankAccounts: List[BankAccount] = testBankAccounts
  def getABankAccount: BankAccount = testBankAccounts(Random.between(0, testBankAccounts.length - 1))
}
