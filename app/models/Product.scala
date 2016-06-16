package models

import play.api.libs.json._

case class Product(productCode: String, productName: String, price: Double)

object Product {
  implicit val productFormat: Format[Product] = Json.format[Product]
}
