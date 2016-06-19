package dal.slick

import models.Product
import org.scalatest.Matchers._
import org.scalatest.TestData
import org.scalatestplus.play._
import play.api.Application
import play.api.test.FakeApplication

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import test.application.DbHelpers._

class ProductRepositorySpec extends PlaySpec with OneAppPerTest {

  override def newAppForTest(testData: TestData): Application = FakeApplication(additionalConfiguration = inMemoryDb())

  def productsRepository = app.injector.instanceOf[ProductsRepository]

  "ProductsRepository" must {

    "find Product by by code" in {

      val expectedProduct = Product("FG001", "Red Umbrella", 12.59)

      Await.result(productsRepository.create(expectedProduct), Duration.Inf)

      val actualProduct = Await.result(productsRepository.findByCode(expectedProduct.code), Duration.Inf).get

      actualProduct should equal (expectedProduct)
    }

    "delete Product by by code" in {

      val productToRemove = Product("FG001", "Red Umbrella", 12.59)
      Await.result(productsRepository.create(productToRemove), Duration.Inf)


      Await.result(productsRepository.delete(productToRemove.code), Duration.Inf)

      val actualProduct = Await.result(productsRepository.findByCode(productToRemove.code), Duration.Inf)

      actualProduct should be(None)
    }

    "update Product by by code" in {

      val productToUpdate = Product("FG001", "Red Umbrella", 12.59)
      val productWithUpdatedPrice = productToUpdate.copy(price = 66.66)
      Await.result(productsRepository.create(productToUpdate), Duration.Inf)


      Await.result(productsRepository.updateByCode(productToUpdate.code, productWithUpdatedPrice), Duration.Inf)

      val actualProduct = Await.result(productsRepository.findByCode(productToUpdate.code), Duration.Inf)

      actualProduct should be(Some(productWithUpdatedPrice))
    }

  }

}
