package controllers

import akka.stream.Materializer
import dal.ProductsRepository
import dto.ProductData
import models.Product

import scala.concurrent.Future
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter

class ProductsSpec extends PlaySpec with Results with MockitoSugar with OneAppPerSuite with BeforeAndAfter {

  implicit lazy val materializer: Materializer = app.materializer

  private var productsRepository: ProductsRepository = _

  private val validProduct = Product("FG001", "Red Umbrella", 12.59)

  private val notValidProduct = ProductData("FG001", "Red Umbrella", "12.a59")

  before {
    productsRepository = mock[ProductsRepository]
    when(productsRepository.create(validProduct)).thenReturn(Future.successful(1))
  }

  "Products controller" should {
    "return appropriate success message" in {

      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, validRequest)

      val bodyText: String = contentAsString(result)
      bodyText mustBe "Product successfully saved"

      verify(productsRepository).create(validProduct)

    }

    "return appropriate status code" in {
      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, validRequest)

      val responseStatus: Int = status(result)
      responseStatus mustBe OK
      verify(productsRepository).create(validProduct)

    }

    "return appropriate error message" when  {
      "submitted product is not valid" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = call(controller.create, notValidRequest)

        val bodyText: String = contentAsString(result)
        bodyText mustBe "Invalid product data"
        verify(productsRepository, never()).create(any(classOf[Product]))
      }
    }

    "return appropriate status code" when  {
      "submitted product is not valid" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = call(controller.create, notValidRequest)

        val responseStatus: Int = status(result)
        responseStatus mustBe BAD_REQUEST

        verify(productsRepository, never()).create(any(classOf[Product]))

      }
    }
  }

  private def validRequest = FakeRequest(POST, "/create").withJsonBody(Json.toJson(validProduct))

  private def notValidRequest = FakeRequest(POST, "/create").withJsonBody(Json.toJson(notValidProduct))
}