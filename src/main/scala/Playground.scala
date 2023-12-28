package dv.khusanjon

import cats.effect.IO
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.hikari.HikariTransactor
import doobie.implicits._
import dv.khusanjon.models.Document
import dv.khusanjon.queries.{QueryDocuments, QueryMain}

object Playground extends App {

  val config = new HikariConfig()
  config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/crmdocs")
  config.setUsername("crmadmin")
  config.setPassword("crmadminpass")
  config.setMaximumPoolSize(5)

  val transactor: IO[HikariTransactor[IO]] =
    IO.pure(HikariTransactor.apply[IO](new HikariDataSource(config)))

  val db = for {
    xa <- transactor
    result <- QueryMain.createDb.run.transact(xa)
  } yield result
  println(db.unsafeRunSync())
  val dbf = db.unsafeToFuture()

  val createDocuments = for {
    xa <- transactor
    result <- QueryDocuments.createTable.run.transact(xa)
  } yield result
  println(createDocuments.unsafeRunSync())
  val f = createDocuments.unsafeToFuture()

  val insertionStaff = for {
    xa <- transactor
    result <- QueryDocuments.insert(Document("0002", "staff", System.currentTimeMillis() / 100)).run.transact(xa)
  } yield result
  println(insertionStaff.unsafeRunSync())

  val insertionAdmins = for {
    xa <- transactor
    result <- QueryDocuments.insert(Document("0001", "admins", System.currentTimeMillis() / 100)).run.transact(xa)
  } yield result
  println(insertionAdmins.unsafeRunSync())

  val insertionItems = for {
    xa <- transactor
    result <- QueryDocuments.insert(Document("0003", "items", System.currentTimeMillis() / 100)).run.transact(xa)
  } yield result
  println(insertionItems.unsafeRunSync())

  val search4Staff = for {
    xa <- transactor
    result <- QueryDocuments.search("staff").to[List].transact(xa)
  } yield result
  search4Staff.unsafeRunSync().foreach(println)

  val searchByIdWith001 = for {
    xa <- transactor
    result <- QueryDocuments.searchById("0001").option.transact(xa)
  } yield result
  searchByIdWith001.unsafeRunSync().foreach(println)

  val searchByNameWithOrder = for {
    xa <- transactor
    result <- QueryDocuments.searchByNameWithOrder("admins", true).to[List].transact(xa)
  } yield result
  searchByNameWithOrder.unsafeRunSync().foreach(println)

  val updateFor0002 = for {
    xa <- transactor
    result <- QueryDocuments.update("0002", "employees").run.transact(xa)
  } yield result
  println(updateFor0002.unsafeRunSync())

  val deleteFor0003 = for {
    xa <- transactor
    result <- QueryDocuments.delete("0003").run.transact(xa)
  } yield result
  println(deleteFor0003.unsafeRunSync())
}
