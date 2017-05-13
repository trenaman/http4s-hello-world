package com.fellurian.helloworld

import org.http4s.server.blaze.BlazeBuilder

object BlazeExample extends App {
  BlazeBuilder.bindHttp(8080, "0.0.0.0")
    .mountService(HelloWorld.service, "/")
    .mountService(HealthCheck.service, "/")
    .run
    .awaitShutdown()
}
