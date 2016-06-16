package features

import cucumber.api.scala.ScalaDsl
import play.Application
import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSResponse}
import play.inject.Injector
import play.test.Helpers._
import play.test.TestServer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait PlaySteps extends ScalaDsl {

  var app: Application = _

  var injector: Injector = _

  var wsClient: WSClient = _

  val port = 9001

  val testServerAddress = s"http://localhost:$port"

  private var server: TestServer = _

  Before { scenario =>
    app = fakeApplication()

    injector = app.injector

    wsClient = injector.instanceOf(classOf[WSClient])

    server = testServer(port, app)
    server.start()
  }

  After {
    scenario => server.stop()
  }

  def post(path: String, data: JsValue):WSResponse =  {
    Await.result(wsClient.url(testServerAddress + path).post(data), Duration.Inf)
  }

}
