export SPARK_LOCAL_IP=127.0.0.1

spark-submit:
	$(SPARK_HOME)/bin/spark-submit --master 'local[4]' --packages 'org.apache.hadoop:hadoop-aws:2.7.3' --class example.Application build/libs/spark-examples-*.jar

package:
	./gradlew clean shadowJar

run: package spark-submit
