package dv.khusanjon
package queries

import cats.effect.IO
import doobie._
import doobie.implicits._
import doobie.scalatest._
import org.scalatest._

class QuerySpec extends WordSpec with Matchers with IOChecker {

  val transactor = {
    val tx = Transactor
      .fromDriverManager[IO](
        "org.h2.Driver",
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
      )

    Query.createTable
      .run
      .transact(tx)
      .unsafeRunSync()

    tx
  }

  "check search" in {
    check(Query.search("002"))
  }

  /*
   * output
  Query0[Document] defined at Query.scala:44
  SELECT * FROM documents
  WHERE name = ?
  ✓ SQL Compiles and TypeChecks
  ✓ P01 String  →  VARCHAR (VARCHAR)
  ✓ C01 ID        VARCHAR (VARCHAR) NOT NULL  →  String
  ✕ C02 NAME      VARCHAR (VARCHAR) NULL      →  String
    Reading a NULL value into String will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[String]
  ✕ C03 TIMESTAMP BIGINT  (BIGINT)  NULL      →  Long
    Reading a NULL value into Long will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[Long]
  */

  "check searchWithId" in {
    check(Query.searchWithId("002"))
  }

  /*
   * output
  Query0[Document] defined at Query.scala:53
  SELECT * FROM documents
  WHERE id = ?
  LIMIT 1
  ✓ SQL Compiles and TypeChecks
  ✓ P01 String  →  VARCHAR (VARCHAR)
  ✓ C01 ID        VARCHAR (VARCHAR) NOT NULL  →  String
  ✕ C02 NAME      VARCHAR (VARCHAR) NULL      →  String
    Reading a NULL value into String will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[String]
  ✕ C03 TIMESTAMP BIGINT  (BIGINT)  NULL      →  Long
    Reading a NULL value into Long will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[Long]
  */

  "check searchWithFragment" in {
    check(Query.searchWithFragment("002", asc = true))
  }

  /*
   * output
  Query0[Document] defined at Query.scala:53
  SELECT id, name, timestamp FROM documents WHERE name = ? ORDER BY
  timestamp ASC
  ✓ SQL Compiles and TypeChecks
  ✓ P01 String  →  VARCHAR (VARCHAR)
  ✓ C01 ID        VARCHAR (VARCHAR) NOT NULL  →  String
  ✕ C02 NAME      VARCHAR (VARCHAR) NULL      →  String
    Reading a NULL value into String will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[String]
  ✕ C03 TIMESTAMP BIGINT  (BIGINT)  NULL      →  Long
    Reading a NULL value into Long will result in a runtime failure.
    Fix this by making the schema type NOT NULL or by changing the
    Scala type to Option[Long]
  */

  "check insert" in {
    check(Query.insert(Document("002", "lamabda", System.currentTimeMillis() / 1000)))
  }

  "check update" in {
    check(Query.update("002", "ops"))
  }

}