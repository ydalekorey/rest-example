package test.application

object DbHelpers {
  def inMemoryDb() = Map(
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> ("jdbc:h2:mem:play-test-" + scala.util.Random.nextInt + ";DATABASE_TO_UPPER=FALSE")
  )
}
