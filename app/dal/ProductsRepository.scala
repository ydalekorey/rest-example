package dal

import com.google.inject.ImplementedBy
import models.Product

@ImplementedBy(classOf[FakeProductsRepository])
trait ProductsRepository {
  def findByCode(code: String): Product
}

class FakeProductsRepository extends ProductsRepository {
  override def findByCode(code: String): Product = new Product("FG001", "Red Umbrella", 12.59)
}


