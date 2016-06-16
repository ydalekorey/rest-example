package dal

import models.Product

import scala.concurrent.Future

trait ProductsRepository {
  def findByCode(code: String): Future[Option[Product]]
  def create(product: Product):  Future[Int]
}


