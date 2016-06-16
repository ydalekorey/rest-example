import cucumber.api.scala.ScalaDsl
import play.Application
import play.api.libs.ws.WSClient
import play.inject.Injector
import play.test.Helpers._
import play.test.TestServer

trait PlaySteps extends ScalaDsl {

  var app: Application = _

  var injector: Injector = _

  var wsClient: WSClient = _

  val port = 9001

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

}
