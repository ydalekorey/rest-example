import cucumber.api.scala.ScalaDsl
import play.api.libs.ws.WSClient
import play.test.Helpers._
import play.test.TestServer

trait PlaySteps extends ScalaDsl {

  val app = fakeApplication()

  val injector = app.injector

  val wsClient = injector.instanceOf(classOf[WSClient])

  val port = 9001

  private val server: TestServer = testServer(port, app)

  Before {
    scenario => server.start()
  }

  After {
    scenario => server.stop()
  }

}
