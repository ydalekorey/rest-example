package dal.slick

import models.Product
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, TestData}
import org.scalatestplus.play._
import play.api.Application
import play.api.test.FakeApplication

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import test.application.DbHelpers._

class ProductRepositorySpec extends PlaySpec with OneAppPerTest with BeforeAndAfter {

  var productsRepository: ProductsRepository = _

  override def newAppForTest(testData: TestData): Application = FakeApplication(additionalConfiguration = inMemoryDb())

  "ProductsRepository" must {

    "find Product by by code" in {

      productsRepository = app.injector.instanceOf[ProductsRepository]

      val expectedProduct = Product("FG001", "Red Umbrella", 12.59)

      Await.result(productsRepository.create(expectedProduct), Duration.Inf)

      val actualProduct = Await.result(productsRepository.findByCode(expectedProduct.code), Duration.Inf).get

      actualProduct should equal (expectedProduct)
    }

  }

}
