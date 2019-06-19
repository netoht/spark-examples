package example.infra

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.HashMap

object SparkSessionWrapper {

  val spark = SparkSession
      .builder()
      .config(sparkConf())
      .getOrCreate()

  def sparkConf(): SparkConf = {
    val conf = new SparkConf
    sparkProps().foreach(it =>
      conf.set(it._1, it._2)
    )
    return conf
  }

  private def sparkProps(): HashMap[String, String] = {
    val props = HashMap[String, String]()

    Properties
      .allKeys()
      .filter(_.startsWith("spark"))
      .filter(Properties.get(_).trim.nonEmpty)
      .foreach(key => props += (key -> Properties.get(key)))

    return props
  }
}