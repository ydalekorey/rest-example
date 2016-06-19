package features

import cucumber.api.scala.{EN, ScalaDsl}
import models.Product
import play.api.test.Helpers._
import test.application.ProductsRepositoryComponent

object ProductsDbSteps extends ProductsRepositoryComponent with ScalaDsl with EN {
  Given("""^that a valid product with (.*) , (.*) and (.+) exists in catalogue$"""){ (productCode: String, productName: String, price: Double) =>
    await(productsRepository.create(new Product(productCode, productName, price)))
  }
}
