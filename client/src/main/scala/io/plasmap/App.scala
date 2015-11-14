package io.plasmap

import io.plasmap.components.HitButton
import japgolly.scalajs.react.{ReactDOM, React}

import scala.scalajs.js
import scalaz.effect.IO
import org.scalajs.dom

/**
 * The main App.
 */
object App extends js.JSApp {
  def main():Unit = {
    val containerNode = dom.document.getElementById("react-container")
    ReactDOM.render(HitButton.component("Eddy"), containerNode)
  }
}
