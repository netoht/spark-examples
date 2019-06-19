#!/bin/bash

# Spark versão 2.4.0 compatível com o EMR versão 5.23.0
# EMR:   https://docs.aws.amazon.com/pt_br/emr/latest/ReleaseGuide/emr-spark.html
# Spark: https://spark.apache.org/downloads.html
#curl --output /tmp/spark.tgz \
#rm -rf /tmp/spark.tgz
#    https://archive.apache.org/dist/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz

mkdir -p /opt/spark
cd /opt/spark && tar -xzvf /tmp/spark.tgz

echo "# Add to your Shell

echo \"export SPARK_HOME=$(ls -d /opt/spark/spark-*)\" >> ~/.bashrc
echo \"export PATH="\$PATH:\$SPARK_HOME/bin"\" >> ~/.bashrc

or

echo \"export SPARK_HOME=$(ls -d /opt/spark/spark-*)\" >> ~/.zshrc
echo \"export PATH="\$PATH:\$SPARK_HOME/bin"\" >> ~/.zshrc

# Restart your shell so that PATH changes take effect. (Opening a new terminal tab will usually do it.)
"
