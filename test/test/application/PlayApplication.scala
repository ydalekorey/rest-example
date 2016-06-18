package test.application

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{Helpers, TestServer}
import test.application.DbHelpers._

import scala.reflect.ClassTag

object PlayApplication {

  lazy val port: Int = Helpers.testServerPort

  private var application: Application = _

  private var testServer: TestServer = _

  def instanceOf[T](implicit ct: ClassTag[T]) = application.injector.instanceOf(ct)

  def startTestServer() = {
    application = new GuiceApplicationBuilder()
      .configure(inMemoryDb())
      .build()
    testServer = TestServer(port, application)
    testServer.start()
  }

  def stopTestServer() = {
    testServer.stop()
  }

}
