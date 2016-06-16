package models

import play.api.libs.json._

case class Product(code: String, name: String, price: Double)

object Product {
  implicit val productFormat: Format[Product] = Json.format[Product]
}
