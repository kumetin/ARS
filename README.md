
# ARS

ARS is an Abnormal Requests Server.

## Build & Run ##

```sh
$ sbt
> jetty:start
```

## Tech Stack
- Scala 2.13
- Scalatra for Web API (Jetty under the hood)

## API Layer `com.ars.server`

External interface, via HTTP-API, it provides two features:
1. *[Model Store](ars.logic.ModelStore)* - loads a model of an endpoint into the model store.
2. *[Model Validate](ars.logic.ModelValidator)* - validates a model of a request against its corresponding endpoint model and returns any abnormalities found

Examples of json represting

## Service Layer `com.ars.logic`

Under the hood, the actual logic resides in these components:
1. *Model Store* - a model store with basic put and get methods
2. *Model Validator* - takes a per of endpoint model & request model and returns abnormalities

## Data Layer `com.ars.dal`

The service [ars.logic.ModelStore](src/main/scala/com/ars/logic/ModelStore.scala) delegates to a [ars.dal.ifc.ModelDao](src/main/scala/com/ars/dal/ifc/ModelDao.scala)

The latter is an interface, this project will be delivered with a simple in memory implementation [ars.dal.impl.HashMapModelDao](src/main/scala/com/ars/dal/impl/HashMapModelDao.scala) for model storage management.

Persistence of models in DB is possible by writing an implementation to [ars.dal.ifc.ModelDao](src/main/scala/com/ars/dal/ifc/ModelDao.scala) which saves and loads from a real database.


