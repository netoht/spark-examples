package example

import example.model.Bank
import example.infra.SparkSessionWrapper

class Application {

  val spark = SparkSessionWrapper.spark

  // implicit conversion: https://docs.scala-lang.org/tour/implicit-conversions.html
  import spark.sqlContext.implicits._

  def run(args: String*) = {

    val df = loadDf

    df.printSchema()
    df.show()

    // spark sql: https://spark.apache.org/docs/2.4.0/sql-getting-started.html
    val result = df.sqlContext.sql(
      """
          select job, balance, day
          from bank
          where age < 30
          and job not in ('management', 'student')
          order by age
         """)

    result.show()
    result.printSchema()

    // coalesce: https://spark.apache.org/docs/2.4.0/api/java/org/apache/spark/rdd/RDD.html#coalesce-int-boolean-scala.Option-scala.math.Ordering-
    result.coalesce(1)
      .write
      .mode("overwrite")
      .option("header", true)
      .csv("datasource/output/")
  }

  private def loadDf = {
    val df = spark
      .read
      .option("header", true)
      .option("delimiter", ";")
      .option("inferSchema", false)
      .option("mode", "FAILFAST")
      .schema(Bank.schema())
      .csv("datasource/bank.csv")
      .withColumnRenamed("default", "standard")
      .as[Bank]

    df.createOrReplaceTempView("bank")

    df
  }
}

object Application extends App {
  new Application().run(args: _*)
}





