package dal.slick

import models.Product
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, TestData}
import org.scalatestplus.play._
import play.api.Application
import play.api.test.FakeApplication

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ProductRepositorySpec extends PlaySpec with OneAppPerTest with BeforeAndAfter {

  var productsRepository: ProductsRepository = _

  /**
    * Constructs a in-memory (h2) database configuration to add to a FakeApplication.
    */
  def inMemorySlickDatabase(): Map[String, String] = {
    Map(
      "slick.dbs.default.db.driver" -> "org.h2.Driver",
      "slick.dbs.default.db.url" -> ("jdbc:h2:mem:play-test-" + scala.util.Random.nextInt+";DATABASE_TO_UPPER=FALSE")
    )
  }

  override def newAppForTest(testData: TestData): Application = FakeApplication(additionalConfiguration = inMemorySlickDatabase())

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
