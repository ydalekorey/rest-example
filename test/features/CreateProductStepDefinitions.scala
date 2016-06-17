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

class CreateProductStepDefinitions extends ScalaDsl with EN with PlaySteps with ShouldMatchers {

  private var productDataJson: JsValue = _

  private var createResponse: WSResponse = _

  Given("""^that I am passing valid (.*) , (.*) and (.+)$""") { (productCode: String, productName: String, price: Double) =>
    productDataJson = toProductDataJson(productCode, productName, price)
  }

  When("""^I attempt to add this data to the product catalogue$"""){ () =>
    createResponse = post("/products", productDataJson)
  }

  Then("""^I receive a success message$"""){ () =>
    createResponse.status shouldBe CREATED
    createResponse.body shouldBe "Product successfully saved"
  }

  Then("""^the data has been entered into the database\.$"""){ () =>
    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode((productDataJson\"code").as[String]), Duration.Inf).get

    savedProduct should equal(productDataJson.as[Product])
  }

  Given("""^that I am passing valid (.*) and (.*) but invalid (.*)$"""){ (productCode: String, productName: String, price: String) =>
    productDataJson = toProductDataJson(productCode, productName, price)
  }
  Then("""^I receive an appropriate error response$"""){ () =>
    createResponse.status shouldBe BAD_REQUEST
    createResponse.body shouldBe "Invalid product data"
  }
  Then("""^the data has NOT been entered into the database\.$"""){ () =>

    val productsRepository = injector.instanceOf(classOf[ProductsRepository])

    val savedProduct = Await.result(productsRepository.findByCode((productDataJson\"code").as[String]), Duration.Inf)

    savedProduct should be(None)
  }

  private def toProductDataJson[P:Writes](productCode:String, productName:String, price: P) =
    Json.obj("code" -> productCode, "name"->productName, "price"-> price)

}
