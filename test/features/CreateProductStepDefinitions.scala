package features

import test.application.{ProductsRepositoryComponent, ProductsRestApi}
import cucumber.api.scala.{EN, ScalaDsl}
import models.Product
import org.scalatest.ShouldMatchers
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._

object CreateProductStepDefinitions extends ProductsRepositoryComponent with ScalaDsl with EN with ShouldMatchers {

  private var productDataJson: JsValue = _

  private var createResponse: WSResponse = _

  val testServerAddress = s"http://localhost:$testServerPort"

  Given("""^that I am passing valid (.*) , (.*) and (.+)$""") { (productCode: String, productName: String, price: Double) =>
    productDataJson = toProductDataJson(productCode, productName, price)
  }

  When("""^I attempt to add this data to the product catalogue$""") { () =>
    createResponse = ProductsRestApi.create(productDataJson)
  }

  Then("""^I receive a success message$""") { () =>
    createResponse.status shouldBe CREATED
    createResponse.body shouldBe "Product successfully saved"
  }

  Then("""^the data has been entered into the database\.$""") { () =>
    val savedProduct = await(productsRepository.findByCode((productDataJson \ "code").as[String])).get

    savedProduct should equal(productDataJson.as[Product])
  }

  Given("""^that I am passing valid (.*) and (.*) but invalid (.*)$""") { (productCode: String, productName: String, price: String) =>
    productDataJson = toProductDataJson(productCode, productName, price)
  }
  Then("""^I receive an appropriate error response$""") { () =>
    createResponse.status shouldBe BAD_REQUEST
    createResponse.body shouldBe "Invalid product data"
  }
  Then("""^the data has NOT been entered into the database\.$""") { () =>
    val savedProduct = await(productsRepository.findByCode((productDataJson \ "code").as[String]))

    savedProduct should be(None)
  }

  private def toProductDataJson[P: Writes](productCode: String, productName: String, price: P) =
    Json.obj("code" -> productCode, "name" -> productName, "price" -> price)

}
