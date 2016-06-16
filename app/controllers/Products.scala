package controllers

import javax.inject._
import play.api.mvc.{Action, Controller}

@Singleton
class Products @Inject() extends Controller {
  def create = Action {
    Ok("Product successfully saved")
  }

}
