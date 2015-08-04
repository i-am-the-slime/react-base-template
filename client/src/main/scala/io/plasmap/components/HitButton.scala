package io.plasmap.components

import japgolly.scalajs.react.ScalazReact.ReactS
import japgolly.scalajs.react.{ReactEvent, ReactEventI, ReactComponentB, React}
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.ScalazReact._

import scalaz.effect.IO

/**
 * Created by mark on 04.08.15.
 */
object HitButton {
  val ST = ReactS.Fix[Int]

  def increaseHitCount(e:ReactEventI): ReactST[IO, Int, Unit] = {
    ST.retM(e.preventDefaultIO) >> ST.mod(_ + 1).liftIO
  }

  val component = ReactComponentB[String]("Component holding a String")
    .initialState(0)
    .renderS( (scope, props, state) ⇒  //In the official examples 'scope' is '$' and 'state' is 'S'
                                      //In our case they are scope, name and numberOfTimesHit
      <.form(
        ^.onSubmit ~~> scope._runState(increaseHitCount)
      )(
        <.button(
          ^.disabled := state > 2
        )(
          state match {
            case 0 ⇒ "You looking at me?"
            case 1 ⇒ "Is that all you got?"
            case 2 ⇒ s"Come on $props, hit me again!"
            case _ ⇒ s"That's enough $props, you win."
          }
        )
      )
    ).build
}
