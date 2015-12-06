package com.controller

import spray.routing.HttpService

trait Controller extends HttpService {

  val rootRoute =
    get {
      path("") {
        complete {
          "OK"
        }
      }
    }
}
