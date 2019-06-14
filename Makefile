export SPARK_LOCAL_IP=127.0.0.1

spark-submit:
	$(SPARK_HOME)/bin/spark-submit --master 'local[4]' --packages 'org.apache.hadoop:hadoop-aws:2.7.3' build/libs/spark-examples-*.jar

package:
	./gradlew clean bootJar

run: package spark-submit
