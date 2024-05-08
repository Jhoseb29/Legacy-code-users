
# Introduction

This is a basic JAVA companion app (*CLI*) for Software Development 3, allowing the  **CRUD** of users.

# Requirements

This app works with **MySQL** or **MongoDB**. For simplicity, we recommend using **Docker**:

## MySQL

Pull Image:
```
docker pull mysql:latest
```

Run Container:
```
$ docker run -d --name sd3db -e MYSQL_ROOT_PASSWORD=sd5 -p 3307:3306 mysql
```

# DB Schema

We use the following DB Schema (MySQL, we use schema **sd3** for this example):

```
CREATE TABLE `sd3`.`users` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
```

This schema needs to be created once.

# DB Configuration File

For MySQL You will need a configuration file to connect (example):

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.cj.jdbc.Driver" />
				<property name="url" value="jdbc:mysql://localhost:3307/sd3" />
				<property name="username" value="SOME_USER" />
				<property name="password" value="SOME_PASSWORD" />
			</dataSource>
		</environment>
	</environments>
</configuration>
```

## MongoDB

Pull Image:
```
docker pull mongo:latest
```
Run Container:
```
$ docker run --name mongo_cis -d -p 27017:27017 mongo
```

# DB Schema

You need to create a DB inside mongo (for example `cis`), and a collection called `users` inside it

# DB Configuration File

For Mongo you will need a configuration file to connect (example):
```
{
    "cnx": "mongodb://<user>:<pwd>@localhost",
    "db": "cis"
}
```

# Usage

Just compile and run this program:

```
Users CLI

Usage: users -config=<configuration> -type=<type> [COMMAND]
CRUD on a Users DB
      -config=<configuration>
         Configuration File (xml)
      -type=<type>   mongo | mysql         
Commands:
  -read    Read Users
  -delete  Delete a User by ID
  -create  Create a new user
  -update  Update an existing user
```

## MySql

These are common parameters:

_In this example, sd3.xml is a file that follows the **MySQL Db Configuration File**_

* Read users:

```
-config=sd3.xml -type=mysql -read 
```

* Create user 
```
-config=sd3.xml -type=mysql -create -n javier -l jroca -p pass123
```

* Delete existing user (id = aab5d5fd-70c1-11e5-a4fb-b026b977eb28 )
```
-config=sd3.xml -type=mysql -delete aab5d5fd-70c1-11e5-a4fb-b026b977eb28 
```

* Update existing user (id = 3bf71036-e7ef-4890-b79b-91496c14160f)
```
-config=sd3.xml -type=mysql -update -i 3bf71036-e7ef-4890-b79b-91496c14160f -n javier2 -l jroca2 -p pwd321
```

## MongoDb

These are common parameters:

_In this example, sd3.json is a file that follows the **Mongo Db Configuration File**_

* Read users:

```
-config=sd3.json -type=mongo -read 
```

* Create user
```
-config=sd3.json -type=mongo -create -n javier -l jroca -p pass123
```

* Delete existing user (id = aab5d5fd-70c1-11e5-a4fb-b026b977eb28 )
```
-config=sd3.json -type=mongo -delete aab5d5fd-70c1-11e5-a4fb-b026b977eb28 
```

* Update existing user (id = 3bf71036-e7ef-4890-b79b-91496c14160f)
```
-config=sd3.json -type=mongo -update -i 3bf71036-e7ef-4890-b79b-91496c14160f -n javier2 -l jroca2 -p pwd321
```


# Log of Changes 

- **V1.0. February 2024**:  Initial Version J.ROCA (MasterClass Professor)
- **V2.0. April 2024**:  MongoDb Version J.ROCA (MasterClass Professor)

_This project is property of Jala University. Do not distribute externally._