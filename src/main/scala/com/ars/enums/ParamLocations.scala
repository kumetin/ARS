package com.ars.enums

object ParamLocations extends Enumeration {
  type ParamLocation = Value
  val Query, Header, Body = Value
}
