package dal

import javax.inject.Singleton

import com.google.inject.ImplementedBy
import models.Product

@ImplementedBy(classOf[FakeProductsRepository])
trait ProductsRepository {
  def findByCode(code: String): Product
  def create(product: Product)
}

@Singleton
class FakeProductsRepository extends ProductsRepository {
  override def findByCode(code: String): Product = new Product("FG001", "Red Umbrella", 12.59)

  override def create(product: Product): Unit = {}
}


