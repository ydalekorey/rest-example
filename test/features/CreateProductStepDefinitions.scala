package features

import cucumber.api.scala.{EN, ScalaDsl}
import dal.ProductsRepository
import dto.{ProductData, ProductData$}
import models.Product
import org.scalatest.ShouldMatchers
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class CreateProductStepDefinitions extends ScalaDsl with EN with PlaySteps with ShouldMatchers {

  private var productData: ProductData = _

  private var createResponse: WSResponse = _

  Given("""^that I am passing valid (.*) , (.*) and (.+)$""") { (productCode: String, productName: String, price: Double) =>
    productData = new ProductData(productCode, productName, price.toString)
  }

  When("""^I attempt to add this data to the product catalogue$"""){ () =>
    createResponse = post("/create", Json.toJson(productData))
  }

  Then("""^I receive a success message$"""){ () =>
    createResponse.status shouldBe OK
    createResponse.body shouldBe "Product successfully saved"
  }

  Then("""^the data has been entered into the database\.$"""){ () =>
    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode(productData.code), Duration.Inf).get

    savedProduct should equal(productData.toProdut())
  }

  Given("""^that I am passing valid (.*) and (.*) but invalid (.*)$"""){ (productCode: String, productName: String, price: String) =>
    productData = new ProductData(productCode, productName, price)
  }
  Then("""^I receive an appropriate error response$"""){ () =>
    createResponse.status shouldBe BAD_REQUEST
    createResponse.body shouldBe "Invalid product data"
  }
  Then("""^the data has NOT been entered into the database\.$"""){ () =>

    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode(productData.code), Duration.Inf).get

    savedProduct should be(None)
  }

}
