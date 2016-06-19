package controllers

import akka.stream.Materializer
import dal.ProductsRepository
import models.Product

import scala.concurrent.Future
import play.api.libs.json.{JsValue, Json}
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

  private val validProductJson = Json.obj("code"->"FG001","name"-> "Red Umbrella", "price"->12.59)

  private val validProduct = validProductJson.as[Product]

  private val notValidProductJson = Json.obj("code"->"FG001","name"-> "Red Umbrella", "price"->"12a.59")

  before {
    productsRepository = mock[ProductsRepository]
    when(productsRepository.create(validProduct)).thenReturn(Future.successful(1))
    when(productsRepository.delete(anyString())).thenReturn(Future.successful(1))
  }

  "Products controller" should {
    "return appropriate success message" in {

      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, FakeRequest().withJsonBody(validProductJson))

      val bodyText: String = contentAsString(result)
      bodyText mustBe "Product successfully saved"

      verify(productsRepository).create(validProduct)

    }

    "return appropriate status code" in {
      val controller = new Products(productsRepository)

      val result: Future[Result] = call(controller.create, FakeRequest().withJsonBody(validProductJson))

      val responseStatus: Int = status(result)
      responseStatus mustBe CREATED
      verify(productsRepository).create(validProduct)

    }

    "return appropriate error message" when  {
      "submitted product is not valid" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = call(controller.create, FakeRequest().withJsonBody(notValidProductJson))

        val bodyText: String = contentAsString(result)
        bodyText mustBe "Invalid product data"
        verify(productsRepository, never()).create(any(classOf[Product]))
      }
    }

    "return appropriate status code" when  {
      "submitted product is not valid" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = call(controller.create, FakeRequest().withJsonBody(notValidProductJson))

        val responseStatus: Int = status(result)
        responseStatus mustBe BAD_REQUEST

        verify(productsRepository, never()).create(any(classOf[Product]))

      }
    }

    "return delete success message" when {
      "valid product code is passed to delete" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = controller.delete("PCODE").apply(FakeRequest())

        val bodyText: String = contentAsString(result)
        bodyText mustBe "Product successfully deleted"

        verify(productsRepository).delete(anyString())
      }
    }

    "return delete success status" when {
      "valid product code is passed to delete" in {
        val controller = new Products(productsRepository)

        val result: Future[Result] = controller.delete("PCODE").apply(FakeRequest())

        val deleteStatus: Int = status(result)
        deleteStatus mustBe OK

        verify(productsRepository).delete(anyString())
      }
    }
  }

}