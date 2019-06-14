# Spark Examples

> Projeto para executar alguns exemplos em spark com Scala.

## Versões utilizadas

- Spark versão 2.4.0 (Scala 2.11.x) compatível com o EMR versão 5.23.0

Mais infos:

* [EMR](https://docs.aws.amazon.com/pt_br/emr/latest/ReleaseGuide/emr-spark.html)
* [Spark](https://spark.apache.org/)

## Pré-requisitos

**Instale o spark, é possível fazer isso pelo script**

```sh
./scripts/setup-spark.sh
```

**Instale o scala, é possível fazer isso pelo script**

```sh
./scripts/setup-scala.sh
```

## Iniciando

Para executar o job spark em Scala é preciso gerar o jar, e depois submeter ao cluster spark,
no caso corrente iremos utilizar local. Execute comando abaixo, será executado o job:

```sh
make run
```



## Documentações de Referência

- [RDD Programming Guide](http://spark.apache.org/docs/latest/rdd-programming-guide.html)
- [Spark SQL, DataFrames and Datasets Guide](http://spark.apache.org/docs/latest/sql-programming-guide.html)
- [Databricks - Data Source CSV Files](https://docs.databricks.com/spark/latest/data-sources/read-csv.html)
- [Spark SQL, Built-in Functions](https://spark.apache.org/docs/2.4.0/api/sql/)
