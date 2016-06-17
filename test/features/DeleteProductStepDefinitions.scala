package features

import cucumber.api.scala.{EN, ScalaDsl}
import dal.ProductsRepository
import models.Product
import org.scalatest.ShouldMatchers
import play.api.http.Status._
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.libs.ws.WSResponse

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DeleteProductStepDefinitions extends ScalaDsl with EN with PlaySteps with ShouldMatchers {

  private var productCodeToRemove: String = _

  private var createResponse: WSResponse = _

  Given("""^that a valid product with (.*) , (.*) and (.+) exists in catalogue$"""){ (productCode: String, productName: String, price: Double) =>
    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    Await.result(productsRepository.create(new Product(productCode, productName, price)), Duration.Inf)
  }

  Given("""^that I am passing valid (.*)$""") { (productCode: String) =>
    productCodeToRemove = productCode
  }

  When("""^I attempt to delete the related data in the product catalogue$"""){ () =>
    createResponse = delete(s"/products/$productCodeToRemove")
  }

  Then("""^I receive a success message$"""){ () =>
    createResponse.status shouldBe OK
    createResponse.body shouldBe "Product successfully removed"
  }

  Then("""^the data has been deleted in the database\.$"""){ () =>
    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode(productCodeToRemove), Duration.Inf)

    savedProduct should be(None)
  }

}
