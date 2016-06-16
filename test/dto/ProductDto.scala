package dto

import play.api.libs.json.{Format, Json}

case class ProductDto(code: String, name: String, price: String)

object ProductDto {
  implicit val productDtoFormat : Format[ProductDto] = Json.format[ProductDto]
}