# springboot-films-example

Spring boot project with 3 modules:

- data (Spring Data)
- service (MapStruct)
- web (Spring REST)

All CRUD operations were implemented using microservices and Sakila DB
(sample DB shipped with MySQL).

Tribute to Florent LEPORA.

To exploit the project in the IDE (STS):

- Run clean install against parent project
- Run web module as Spring Boot App

To deploy the project in Docker:
- Open command line
- Change directory to where Dockerfile is located
- execute the following:

	- docker network create sakila_mysql

	- docker run -d -p 6033:3306 -it --network sakila_mysql -e MYSQL_USER=sakila -e MYSQL_ROOT_PASSWORD=p_ssW0rd -e MYSQL_DATABASE=sakila --name sakila_films sakiladb/mysql

	- docker build -t springboot-films-example-web:0.0.1-SNAPSHOT .

	- docker run -d -it -p 8080:8080 --network sakila_mysql --name springboot-films-example springboot-films-example-web:0.0.1-SNAPSHOT 


