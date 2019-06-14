package spark.repl.model

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

// class class: https://docs.scala-lang.org/tour/case-classes.html
case class Bank(age: Integer,
                job: String,
                marital: String,
                education: String,
                standard: String,
                balance: Integer,
                housing: String,
                loan: String,
                contact: String,
                day: Integer,
                month: String,
                duration: Integer,
                campaign: Integer,
                pdays: Integer,
                previous: Integer,
                poutcome: String,
                y: String)

// singleton object: https://docs.scala-lang.org/tour/singleton-objects.html
object Bank {
  def schema() = {
    StructType(Array(
      StructField("age", IntegerType),
      StructField("job", StringType),
      StructField("marital", StringType),
      StructField("education", StringType),
      StructField("standard", StringType),
      StructField("balance", IntegerType),
      StructField("housing", StringType),
      StructField("loan", StringType),
      StructField("contact", StringType),
      StructField("day", IntegerType),
      StructField("month", StringType),
      StructField("duration", IntegerType),
      StructField("campaign", IntegerType),
      StructField("pdays", IntegerType),
      StructField("previous", IntegerType),
      StructField("poutcome", StringType),
      StructField("y", StringType)
    ))
  }
}