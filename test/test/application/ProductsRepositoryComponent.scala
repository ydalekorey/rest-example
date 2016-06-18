package test.application

import dal.ProductsRepository

trait ProductsRepositoryComponent {
  def productsRepository = PlayApplication.instanceOf[ProductsRepository]
}
