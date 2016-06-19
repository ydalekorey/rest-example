package features

import cucumber.api.scala.{EN, ScalaDsl}
import models.Product
import play.api.test.Helpers._
import org.scalatest._
import play.api.libs.ws.WSResponse
import test.application.{ProductsRepositoryComponent, ProductsRestApi}

object RetrieveProductStepsDefinitions extends ProductsRepositoryComponent with ScalaDsl with EN with ShouldMatchers {

  private var productCodeToGet: String = _

  private var retrievedProductResponse: WSResponse = _

  Given("""^that I am passing valid (.*) to get product$""") { (productsCode: String) =>
    productCodeToGet = productsCode
  }
  When("""^I attempt to bring back all related data$""") { () =>
    retrievedProductResponse = ProductsRestApi.get(productCodeToGet)
  }
  Then("""^I receive a getting success message$""") { () =>
    retrievedProductResponse.status shouldBe OK
  }
  Then("""^the data returned is (.+), (.*) and (.*)$""") { (productCode: String, productName: String, productPrice: Double) =>
    Product(productCode, productName, productPrice) should equal(retrievedProductResponse.json.as[Product])
  }
}
