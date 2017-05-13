package com.fellurian.helloworld

import org.http4s._
import _root_.argonaut.Argonaut._
import org.http4s.argonaut._
import org.http4s.dsl._
import org.log4s._

object HealthCheck {
  private val log = getLogger

  val service = HttpService {
    case GET -> Root / "_internal_" / "ping" => {
      log.info(s"Received 'ping'")
      Ok(jSingleObject("message", jString("pong")))
    }
  }
}
