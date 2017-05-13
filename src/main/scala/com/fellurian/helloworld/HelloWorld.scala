package com.fellurian

import _root_.argonaut._
import org.http4s._
import _root_.argonaut.Argonaut._
import org.http4s.argonaut._
import org.http4s.dsl._
import org.log4s._


object HelloWorld {
  private val log = getLogger
  val service = HttpService {
    case GET -> Root / "hello" / name => {
      log.info(s"Received 'hello' with name '${name}'")
      Ok(jSingleObject("message", jString(s"Hello, ${name}. You are a good friend.")))
    }
  }
}
