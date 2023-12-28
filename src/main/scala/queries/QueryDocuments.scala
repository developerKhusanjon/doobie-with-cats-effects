package dv.khusanjon
package queries

import doobie.implicits._
import dv.khusanjon.models.Document

object QueryDocuments {

  def createTable: doobie.Update0 = {
    sql"""
         |CREATE TABLE IF NOT EXISTS documents (
         |  id VARCHAR(100) PRIMARY KEY,
         |  name VARCHAR(100),
         |  timestamp Long
         |)
       """.stripMargin
      .update
  }

  def insert(document: Document): doobie.Update0 = {
    sql"""
         |INSERT INTO documents (
         |  id,
         |  name,
         |  timestamp
         |)
         |VALUES (
         |  ${document.id},
         |  ${document.name},
         |  ${document.timestamp}
         |)
       """.stripMargin
      .update
  }

  def search(name: String): doobie.Query0[Document] = {
    sql"""
         |SELECT * FROM documents
         |WHERE name = $name
       """
      .stripMargin
      .query[Document]
  }

  def searchById(id: String): doobie.Query0[Document] = {
    sql"""
         |SELECT * FROM documents
         |WHERE id = $id
         |LIMIT 1
       """.stripMargin
      .query[Document]
  }

  def searchWithRange(offset: Int, limit: Int): doobie.Query0[Document] = {
    sql"""
         |SELECT * FROM documents
         |LIMIT $limit
         |OFFSET $offset
       """.stripMargin
      .query[Document]
  }

  def searchByNameWithOrder(name: String, asc: Boolean): doobie.Query0[Document] = {
    val f1 = fr"SELECT id, name, timestamp FROM documents"
    val f2 = fr"WHERE name = $name"
    val f3 = fr"ORDER BY timestamp" ++ (if (asc) fr"ASC" else fr"DESC")
    val q = (f1 ++ f2 ++ f3).query[Document]
    q
  }

  def update(id: String, name: String): doobie.Update0 = {
    sql"""
         |UPDATE documents
         |SET name = $name
         |WHERE id = $id
       """.stripMargin
      .update
  }

  def delete(id: String): doobie.Update0 = {
    sql"""
         |DELETE FROM documents
         |WHERE id = $id
       """.stripMargin
      .update
  }
}
