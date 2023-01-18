package com.saltsecurity.ars

import com.saltsecurity.ars.dal.impl.HashMapModelDao
import com.saltsecurity.ars.logic.ModelStore

/**
 * Provided singleton implementations for service layer components
 * Service layer components are used either by API layer components or by other service layers components.
 * We use this approach of one stop shop singleton for all the other singletons actual implementations in favor of
 * dependency injection as means of inversion of control.
 * Scalatra framework is looking for empty constructors for the ScalatraServlet sub-type instances wired to it - this
 * makes it hard to utilize dependency injection, so instead we go with this simple global (but immutable!) state to
 * hold the references for the service layer component.
 */
object Services {
  val modelStore = new ModelStore(data.models)

  private object data {
    val models = new HashMapModelDao
  }
}
