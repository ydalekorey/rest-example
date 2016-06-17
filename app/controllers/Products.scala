package controllers

import javax.inject._

import dal.ProductsRepository
import models.Product
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

@Singleton
class Products @Inject()(productsRepository: ProductsRepository) extends Controller {
  def create = Action.async(parse.json) { implicit request =>

    request.body.validate[Product].fold(
      errors => {
        Future.successful(BadRequest("Invalid product data"))
      },
      product => {
        productsRepository.create(product).map { _ =>
          Created("Product successfully saved")
        }
      }
    )
  }

}
