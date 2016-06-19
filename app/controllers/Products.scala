package controllers

import javax.inject._

import dal.ProductsRepository
import models.Product
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
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

  def delete(code: String) = Action.async { implicit request =>
    productsRepository.delete(code).map { _ =>
      Ok("Product successfully deleted")
    }
  }

  def get(code: String) = Action.async {
    productsRepository.findByCode(code).map { product =>
      Ok(Json.toJson(product))
    }
  }

  def update(code: String) = Action.async(parse.json) { implicit request =>
    val product = request.body.as[Product]
    productsRepository.update(code, product).map { _ =>
      Ok("Product successfully updated")
    }
  }

}
