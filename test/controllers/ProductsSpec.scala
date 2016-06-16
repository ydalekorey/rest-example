package controllers

import akka.stream.Materializer
import dal.ProductsRepository
import models.Product

import scala.concurrent.Future
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter

class ProductsSpec extends PlaySpec with Results with MockitoSugar with OneAppPerSuite with BeforeAndAfter {

  implicit lazy val materializer: Materializer = app.materializer

  private var productsRepository: ProductsRepository = _

  private val product = Product("FG001", "Red Umbrella", 12.59)

  before {
    productsRepository = mock[ProductsRepository]
    when(productsRepository.create(product)).thenReturn(Future.successful())
  }

  "Products controller" should {
    "return appropriate success message" in {

      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, validRequest)

      val bodyText: String = contentAsString(result)
      bodyText mustBe "Product successfully saved"

    }

    "return appropriate status code" in {
      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, validRequest)

      val responseStatus: Int = status(result)
      responseStatus mustBe OK

    }
  }

  after {
    verify(productsRepository).create(product)
  }

  private def validRequest = FakeRequest(POST, "/create").withJsonBody(Json.toJson(product))
}