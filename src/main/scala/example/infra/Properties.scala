package example.infra

import java.util

import org.apache.commons.configuration.{ConfigurationException, PropertiesConfiguration}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

object Properties {

  private val env = System.getenv().asScala
  private val param = System.getProperties.asScala
  private val profilesActiveKey = "profiles.active"
  private val profilesActive =
    env.getOrElse(profilesActiveKey,
      env.getOrElse(keyEnv(profilesActiveKey),
        param.getOrElse(profilesActiveKey,
          param.getOrElse(keyEnv(profilesActiveKey), ""))))
      .trim
      .split(",")
      .filterNot(_.trim.isEmpty)
      .map(env => new Profile(profileFilename(s"-${env}"))) :+ new Profile(profileFilename())

  private def keyEnv(key: String) = key.toUpperCase.replaceAll("\\.", "_")
  private def profileFilename(env: String = "") = s"application${env}.properties"

  def allKeys() = {
    val keys = new util.TreeSet[String]()
    profilesActive.foreach(_.props.getKeys.asScala.foreach(it => keys.add(it.asInstanceOf[String])))
    env.foreach(it => keys.add(it._1))
    param.foreach(it => keys.add(it._1))
    keys.asScala
  }

  def get(key: String): String = {
    if (env.contains(key) || env.contains(keyEnv(key))) {
      return env.get(key).getOrElse(env.get(keyEnv(key)).getOrElse(""))
    }
    if (param.contains(key) || param.contains(keyEnv(key))) {
      return param.get(key).getOrElse(param.get(keyEnv(key)).getOrElse(""))
    }
    for (profile <- profilesActive) {
      if (profile.props.containsKey(key)) {
        return profile.props.getString(key)
      }
    }
    ""
  }

  case class Profile(val filename: String = "") {
    val logger = LoggerFactory.getLogger(this.getClass)
    val props = new PropertiesConfiguration()

    try {
      props.load(filename)
    } catch {
      case e: ConfigurationException => logger.warn(e.getMessage)
    }
  }
}