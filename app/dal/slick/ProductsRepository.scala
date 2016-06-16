package dal.slick

import javax.inject.{Inject, Singleton}

import models.Product
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits._
import slick.driver.JdbcProfile

import scala.concurrent.Future

@Singleton
class ProductsRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends dal.ProductsRepository {
  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  private val db = dbConfig.db

  import dbConfig.driver.api._

  private class ProductsTable(tag: Tag) extends Table[Product](tag, "products") {

    def code = column[String]("code", O.PrimaryKey)

    def name = column[String]("name")

    def price = column[Double]("price")

    def * = (code, name, price) <>((Product.apply _).tupled, Product.unapply)
  }

  private val Products = TableQuery[ProductsTable]

  def findByCode(code: String): Future[Option[Product]] = db.run(Products.filter(_.code === code).result.headOption)

  def create(product: Product): Future[Int] = db.run(Products  += product)

}