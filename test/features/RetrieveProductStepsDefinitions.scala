package features

import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest._
import test.application.ProductsRepositoryComponent

object RetrieveProductStepsDefinitions extends ProductsRepositoryComponent with ScalaDsl with EN with ShouldMatchers {

  private var productCodeToGet: String = _

  Given("""^that I am passing valid (.*) to get product$""") { (productsCode: String) =>
    productCodeToGet = productsCode
  }
  When("""^I attempt to bring back all related data$""") { () =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Then("""^I receive a getting success message$""") { () =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
  Then("""^the data returned is (.*) and (.*)$""") { (productName: String, productPrice: Double) =>
    //// Write code here that turns the phrase above into concrete actions
    throw new PendingException()
  }
}
