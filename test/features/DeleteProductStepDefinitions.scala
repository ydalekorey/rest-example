package features

import test.application.{ProductsRepositoryComponent, ProductsRestApi}
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.ShouldMatchers
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._

object DeleteProductStepDefinitions extends ProductsRepositoryComponent with ScalaDsl with EN with ShouldMatchers {

  private var productCodeToRemove: String = _

  private var createResponse: WSResponse = _

  Given("""^that I am passing valid (.*)\ to remove$""") { (productCode: String) =>
    productCodeToRemove = productCode
  }

  When("""^I attempt to delete the related data in the product catalogue$"""){ () =>
    createResponse = ProductsRestApi.delete(productCodeToRemove)
  }

  Then("""^I receive a remove success message$"""){ () =>
    createResponse.status shouldBe OK
    createResponse.body shouldBe "Product successfully deleted"
  }

  Then("""^the data has been deleted in the database$"""){ () =>
    val savedProduct = await(productsRepository.findByCode(productCodeToRemove))

    savedProduct should be(None)
  }

}
