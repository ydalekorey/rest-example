package features

import cucumber.api.scala.{EN, ScalaDsl}
import models.Product
import org.scalatest._
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import test.application.{ProductsRepositoryComponent, ProductsRestApi}

object UpdateProductStepsDefinitions extends ProductsRepositoryComponent with ScalaDsl with EN with ShouldMatchers {

  private var updatedProductResponse: WSResponse = _

  private var priceToUpdate: Double = _

  private var productToUpdate: Product = _

  Given("""^that a valid product with (.*) , (.*) and (.+) exists in catalogue$"""){ (productCode: String, productName: String, price: Double) =>
    productToUpdate = new Product(productCode, productName, price)
    await(productsRepository.create(productToUpdate))
  }

  When("""^I attempt to update this existing data in the product catalogue with a new (.+)$""") { (price: Double) =>
    priceToUpdate = price
    updatedProductResponse = ProductsRestApi.update(productToUpdate.code, Json.obj("price"-> price))
  }
  Then("""^I receive a success message$""") { () =>
    updatedProductResponse.status shouldBe OK
    updatedProductResponse.body shouldBe "Product successfully updated"
  }
  Then("""^the data has been updated in the database""") { () =>
    val savedProduct = await(productsRepository.findByCode(productToUpdate.code)).get
    savedProduct should equal(productToUpdate.copy(price = priceToUpdate))
  }
}
