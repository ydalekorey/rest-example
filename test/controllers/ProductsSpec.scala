package controllers

import models.Product

import scala.concurrent.Future
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class ProductsSpec extends PlaySpec with Results {

  "Products controller" should {
    "return appropriate success message" in {
      val controller = new Products()

      val result: Future[Result] = controller.create().apply(validRequest)

      val bodyText: String = contentAsString(result)
      bodyText mustBe "Product successfully saved"
    }

    "return appropriate status code" in {
      val controller = new Products()

      val result: Future[Result] = controller.create().apply(validRequest)
      val responseStatus: Int = status(result)
      responseStatus mustBe OK
    }
  }

  private def validRequest = FakeRequest(POST, "/create").withJsonBody(Json.toJson(Product("FG001", "Red Umbrella", 12.59)))
}