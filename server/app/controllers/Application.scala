package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import scala.concurrent.Future

/**
 * Created by mark on 04.08.15.
 */
object Application extends Controller {
  def index() = Action.async {
    request ⇒ Future(Ok(views.html.index()))
  }
}
