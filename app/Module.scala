import com.google.inject.AbstractModule

class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[dal.ProductsRepository]).to(classOf[dal.slick.ProductsRepository])
  }
}