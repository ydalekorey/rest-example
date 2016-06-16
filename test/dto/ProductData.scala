package dto

import models.Product
import play.api.libs.json.{Format, Json}

case class ProductData(code: String, name: String, price: String) {
  def toProdut(): Product = new Product(code,name, price.toDouble)
}

object ProductData {
  implicit val productDtoFormat : Format[ProductData] = Json.format[ProductData]
}