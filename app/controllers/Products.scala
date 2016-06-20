package controllers

import javax.inject._

import dal.ProductsRepository
import io.swagger.annotations._
import models.Product
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

@Singleton
@Api(value = "/products", description = "Operations with Products")
class Products @Inject()(productsRepository: ProductsRepository) extends Controller {

  @ApiOperation(
    nickname = "createProduct",
    value = "Create Product",
    notes = "Create Product record in catalogue",
    httpMethod = "POST"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        dataType = "models.Product",
        required = true,
        paramType = "body",
        value = "Product"
      )
    )
  )
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid product data"),
    new ApiResponse(code = 201, message = "Product successfully saved")))
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

  @ApiOperation(
    nickname = "deleteProduct",
    value = "Delete Product",
    notes = "Delete Product record in catalogue",
    httpMethod = "DELETE"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Product successfully deleted")))
  def delete(@ApiParam(value = "Code of product to remove") code: String) = Action.async { implicit request =>
    productsRepository.delete(code).map { _ =>
      Ok("Product successfully deleted")
    }
  }

  @ApiOperation(
    nickname = "getProduct",
    value = "Get Product",
    notes = "Get Product record by code from catalogue",
    httpMethod = "GET"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Product found", response = classOf[Product])))
  def get(@ApiParam(value = "Code of product to retrieve") code: String) = Action.async {
    productsRepository.findByCode(code).map { product =>
      Ok(Json.toJson(product))
    }
  }

  @ApiOperation(
    nickname = "updateProduct",
    value = "Update Product",
    notes = "Update Product record by code in catalogue",
    httpMethod = "PUT"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        dataType = "models.Product",
        required = true,
        paramType = "body",
        value = "Product"
      )
    )
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Product successfully updated")))
  def update(@ApiParam(value = "Code of product to update")code: String) = Action.async(parse.json) { implicit request =>
    val product = request.body.as[Product]
    productsRepository.updateByCode(code, product).map { _ =>
      Ok("Product successfully updated")
    }
  }

}
