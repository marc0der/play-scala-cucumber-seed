package steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import play.api.libs.json.Json
import steps.support.World

import scalaj.http.Http

class HealthSteps extends ScalaDsl with EN with Matchers with World {

  When("""^a request is made to the (.*) endpoint$""") { (endpoint: String) =>
    response = Http(s"$host$endpoint")
      .timeout(connTimeoutMs = 1000, readTimeoutMs = 10000)
      .asString
  }

  Then("""^a (\d+) status code is received$""") { (status: Int) =>
    response.code shouldBe status
  }

  Then("""^the payload has a "(.*)" of "(.*)"$""") { (key: String, value: String) =>
    val json = Json.parse(response.body)
    val status = (json \ key).as[String]
    status shouldBe value
  }
}
