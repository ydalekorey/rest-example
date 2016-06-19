package test.application

import play.api.http.Writeable
import play.api.libs.ws.{WSClient, WSResponse}
import play.api.test.Helpers._

object ProductsRestApi {


  def wsClient: WSClient = PlayApplication.instanceOf[WSClient]

  private lazy val port = PlayApplication.port

  private lazy val productsUrl = s"http://localhost:$port/products"

  def create[T](data: T)(implicit wrt: Writeable[T]): WSResponse = {
    await(wsClient.url(productsUrl).post(data))
  }

  def delete(productCode: String): WSResponse = {
    await(wsClient.url(productsUrl+"/"+productCode).delete())
  }

  def get(productCode: String): WSResponse = {
    await(wsClient.url(productsUrl+"/"+productCode).get())
  }

}
