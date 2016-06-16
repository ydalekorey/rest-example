package features

import cucumber.api.scala.{EN, ScalaDsl}
import dal.ProductsRepository
import models.Product
import org.scalatest.ShouldMatchers
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class CreateProductStepDefinitions extends ScalaDsl with EN with PlaySteps with ShouldMatchers {

  private var productToCreate: Product = _

  private var createResponse: WSResponse = _

  Given("""^that I am passing valid (.*) , (.*) and (.+)$""") { (productCode: String, productName: String, price: Double) =>
    productToCreate = new Product(productCode, productName, price)
  }

  When("""^I attempt to add this data to the product catalogue$"""){ () =>
    createResponse = post("/create", Json.toJson(productToCreate))
  }

  Then("""^I receive a success message$"""){ () =>
    createResponse.status shouldBe OK
    createResponse.body shouldBe "Product successfully saved"
  }

  Then("""^the data has been entered into the database\.$"""){ () =>
    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode(productToCreate.code), Duration.Inf).get

    savedProduct should equal(productToCreate)
  }

}
