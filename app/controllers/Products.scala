package controllers

import javax.inject._

import dal.ProductsRepository
import models.Product
import play.api.mvc.{Action, Controller}

@Singleton
class Products @Inject()(productsRepository: ProductsRepository) extends Controller {
  def create = Action(parse.json) { implicit request =>
    val productToCreate = request.body.as[Product]
    productsRepository.create(productToCreate)
    Ok("Product successfully saved")
  }

}
