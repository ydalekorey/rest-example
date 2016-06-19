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

  private var controller: Products = _

  private val validProductJson = Json.obj("code"->"FG001","name"-> "Red Umbrella", "price"->12.59)

  private val validProduct = validProductJson.as[Product]

  private val notValidProductJson = Json.obj("code"->"FG001","name"-> "Red Umbrella", "price"->"12a.59")

  before {
    productsRepository = mock[ProductsRepository]
    controller = new Products(productsRepository)
    when(productsRepository.create(validProduct)).thenReturn(Future.successful(1))
    when(productsRepository.delete(validProduct.code)).thenReturn(Future.successful(1))
    when(productsRepository.findByCode(validProduct.code)).thenReturn(Future.successful(Some(validProduct)))
  }

  "Products controller" should {
    "return appropriate success message" when {
      "valid product is passed to save" in  {
        val result = await(call(controller.create, FakeRequest().withJsonBody(validProductJson)))

        result mustBe Created("Product successfully saved")

        verify(productsRepository).create(validProduct)
      }
    }

    "return appropriate error message" when  {
      "submitted product is not valid" in {
        val result = await(call(controller.create, FakeRequest().withJsonBody(notValidProductJson)))

        result mustBe BadRequest("Invalid product data")

        verify(productsRepository, never()).create(any(classOf[Product]))

      }
    }

    "return delete success message" when {
      "valid product code is passed to delete" in {
        val result = await(controller.delete(validProduct.code).apply(FakeRequest()))

        result mustBe Ok("Product successfully deleted")

        verify(productsRepository).delete(anyString())
      }
    }

    "return needed product" when {
      "valid product code is passed to get product" in {
        val result = await(controller.get(validProduct.code).apply(FakeRequest()))

        result mustBe Ok(Json.toJson(validProduct))

        verify(productsRepository).findByCode(validProduct.code)
      }
    }
  }

  "return update success message" when {
    "product updated with new data" in {
      val productWithUpdatedPrice = validProduct.copy(price = 66.66)
      when(productsRepository.updateByCode(validProduct.code, productWithUpdatedPrice)).thenReturn(Future.successful(1))

      val result = await(call(controller.update(validProduct.code), FakeRequest().withJsonBody(Json.toJson(productWithUpdatedPrice))))

      result mustBe Ok("Product successfully updated")

      verify(productsRepository).updateByCode(validProduct.code, productWithUpdatedPrice)
    }
  }

}