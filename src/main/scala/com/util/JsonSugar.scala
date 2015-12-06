package com.util

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport

trait JsonSugar extends Json4sSupport {
  implicit def json4sFormats: Formats = DefaultFormats ++ org.json4s.ext.JodaTimeSerializers.all
}
