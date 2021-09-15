# Scala3 sampleApp
This app can do only

- create table
- insert records
- search record by column
- response to json
- drop table

# Getting Started

## install

sbt
```shell
$ brew install coursier/formulas/coursier && cs setup
```
(see: https://docs.scala-lang.org/scala3/getting-started.html)

MySQL
```shell
$ brew install mysql
```
(see: https://formulae.brew.sh/formula/mysql )

## create db
```shell
$ mysql -uroot # enter mysql server
mysql> create database demo # create db of this project
```

## clone project
```shell
$ git clone
```
 
## run

you can do

- create member database
- drop member database
- register member
- search member
- update member data


```shell
$ cd {to under this project}
$ pwd
> xxxx/sampleapp
$ sbt run
```

# dependencies

- scalikejdbc(stable version, but some queryDSL does not work how I thought...)
- play-json(still RC version)

# What I lean

- almost frameworks for scala have not supported Scala3 yet.
- almost libraries have not supported Scala3 yet.
- libraries which have supported for Scala3 have been just Release Candidate version yet.
