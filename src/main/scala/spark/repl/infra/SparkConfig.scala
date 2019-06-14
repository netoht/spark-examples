package spark.repl.infra

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.{AbstractEnvironment, EnumerablePropertySource, Environment}

import scala.collection.JavaConverters._
import scala.collection.mutable.HashMap

@Configuration
class SparkConfig {

  @Autowired
  var env: Environment = null

  @Bean
  @Autowired
  def sparkSession(config: SparkConf): SparkSession = {
    return SparkSession
      .builder()
      .config(config)
      .getOrCreate()
  }

  @Bean
  def sparkConf(): SparkConf = {
    val conf = new SparkConf
    sparkProps().foreach(it =>
      conf.set(it._1, it._2)
    )
    return conf
  }

  private def sparkProps(): HashMap[String, String] = {
    var props = HashMap[String, String]()

    env.asInstanceOf[AbstractEnvironment].getPropertySources.asScala.toStream
      .filter(_.isInstanceOf[EnumerablePropertySource[Any]])
      .flatMap(_.asInstanceOf[EnumerablePropertySource[Any]].getPropertyNames)
      .distinct
      .filter(_.startsWith("spark"))
      .filter(env.getProperty(_).trim.nonEmpty)
      .foreach(it => props.+=(it -> env.getProperty(it)))

    return props
  }
}