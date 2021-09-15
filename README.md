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

# demo
![Screen Shot 2021-09-15 at 18 54 16](https://user-images.githubusercontent.com/75293120/133412924-bfa93d3c-ae7e-47aa-8369-dcf804b84c51.png)
![Screen Shot 2021-09-15 at 18 54 50](https://user-images.githubusercontent.com/75293120/133412972-196b13b4-b788-4eb6-aeb8-c63466f37f81.png)
![Screen Shot 2021-09-15 at 18 56 14](https://user-images.githubusercontent.com/75293120/133412983-7ac5db3a-3cb5-4c8f-9736-9690b18cf048.png)
![Screen Shot 2021-09-15 at 18 56 21](https://user-images.githubusercontent.com/75293120/133412998-34000141-536b-43bb-8ea4-d51a389dfa62.png)


# dependencies

- scalikejdbc(stable version, but some queryDSL does not work how I thought...)
- play-json(still RC version)

# What I lean

- almost frameworks for scala have not supported Scala3 yet.
- almost libraries have not supported Scala3 yet.
- libraries which have supported for Scala3 have been just Release Candidate version yet.
