package com.saltsecurity.ars

// Everything under `com.saltsecurity.ars.testing` is data & tooling resources for test scenario authoring
package object testing {
  object EndpointModels {
    val GetUsersInfo =
      """{
        |"path": "/users/info",
        |"method": "GET",
        |"query_params": [
        |	{
        |		"name": "with_extra_data",
        |		"types": ["Boolean"],
        |		"required": false
        |	},
        |	{
        |		"name": "user_id",
        |		"types": ["String", "UUID"],
        |		"required": false
        |	}
        |],
        |"headers": [
        |	{
        |		"name": "Authorization",
        |		"types": ["String", "Auth-Token"],
        |		"required": true
        |	}
        |],
        |"body": []
        |}""".stripMargin
  }

  object RequestData {
    val GetUsers: String =
      """{
          |"path": "/users/info",
          |"method": "GET",
          |"query_params": [
          |  {
          |     "name": "with_extra_data",
          |     "value": false
          |  }
          |],
          |"headers": [
          |   {
          |      "name": "Authorization",
          |      "value": "Bearer 56ee9b7a-da8e-45a1-aade-a57761b912c4"
          |   }
          |],
          |"body": []
        |}""".stripMargin
  }
}
