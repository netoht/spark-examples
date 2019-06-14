package spark.repl

import org.apache.spark.sql.{Dataset, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import spark.repl.model.Bank

@SpringBootApplication
@EnableAutoConfiguration(exclude = Array(classOf[GsonAutoConfiguration]))
class Application extends CommandLineRunner {

  @Autowired
  val spark: SparkSession = null

  // implicit conversion: https://docs.scala-lang.org/tour/implicit-conversions.html
  import spark.sqlContext.implicits._

  override def run(args: String*) = {

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
  SpringApplication.run(classOf[Application])
}
