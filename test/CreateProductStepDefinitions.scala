import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import models.Product
import org.scalatest.ShouldMatchers
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class CreateProductStepDefinitions extends ScalaDsl with EN with PlaySteps with ShouldMatchers {

  private var product: Product = _

  private var createResponse: WSResponse = _

  Given("""^that I am passing valid (.*) , (.*) and (.+)$""") { (productCode: String, productName: String, price: Double) =>
    product = new Product(productCode, productName, price)
  }

  When("""^I attempt to add this data to the product catalogue$"""){ () =>
    createResponse = Await.result(wsClient.url("http://localhost:"+ port+ "/create").post(Json.toJson(product)), Duration.Inf)
  }

  Then("""^I receive a success message$"""){ () =>
    createResponse.status shouldBe OK
    createResponse.body shouldBe "Product successfully saved"
  }

  Then("""^the data has been entered into the database\.$"""){ () =>
    throw new PendingException()
  }

}
