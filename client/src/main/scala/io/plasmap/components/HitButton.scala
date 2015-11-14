package io.plasmap.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.ReactEventI
import japgolly.scalajs.react.vdom.prefix_<^._

/**
 * Created by mark on 04.08.15.
 */
object HitButton {

  val component = ReactComponentB[String]("Component holding a String")
    .initialState(0)
    .render( scope =>
      <.form(
        ^.onSubmit ==> ((e:ReactEventI) => {
          e.preventDefault()
          scope.modState(_ + 1)
        })
      )(
        <.button(
          ^.disabled := scope.state > 2
        )(
          scope.state match {
            case 0 =>  "You looking at me?"
            case 1 =>  "Is that all you got?"
            case 2 => s"Come on ${scope.props}, hit me again!"
            case _ => s"That's enough ${scope.props}, you win."
          }
        )
      )
    ).build
}
