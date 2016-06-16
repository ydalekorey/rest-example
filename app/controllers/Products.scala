package controllers

import javax.inject._

import dal.ProductsRepository
import models.Product
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Action, Controller}

@Singleton
class Products @Inject()(productsRepository: ProductsRepository) extends Controller {
  def create = Action.async(parse.json) { implicit request =>
    val productToCreate = request.body.as[Product]
    productsRepository.create(productToCreate).map {code =>
      Ok("Product successfully saved")
    }
  }

}
