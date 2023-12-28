package dv.khusanjon
package queries

import doobie.implicits._

object QueryMain {

  def createDb: doobie.Update0 = {
    sql"""
         |CREATE DATABASE IF NOT EXISTS crmdocs
       """
      .update
  }

  def dropDb: doobie.Update0 = {
    sql"""
         |DROP DATABASE IF EXISTS crmdocs
       """
      .update
  }

}
