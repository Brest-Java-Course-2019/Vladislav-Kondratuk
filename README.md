
# Rental Cars demo application
[![Build Status](https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk)
[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2019/Vladislav-Kondratuk/badge.svg?branch=master)](https://coveralls.io/github/Brest-Java-Course-2019/Vladislav-Kondratuk?branch=master)
# Project Title

Test Java project to study technologies such as git, maven, continuous integration, continuous delivery and others.
## Getting Started

### Prerequisites

        install jdk8

        install maven3+
        
        install tomcat

        install git

Apache Maven 3.5.2, Apache Tomcat 9.0.13, java 1.8, and local git required for build,
you can get it at:

        https://www.oracle.com

        http://maven.apache.org

        https://tomcat.apache.org

#### Check environment configuration

        $ java -version

        $ export JAVA_HOME = ...

        $ mvn -version

### Installing
Choose directory for project, download project from github:
 
       $ git clone https://github.com/Brest-Java-Course-2019/Vladislav-Kondratuk.git

#### Build project
Run terminal command in project directory:

        $ mvn clean install

#### Preparing reports

        $ mvn site

        $ mvn site:site

   check for reports: 
        
        ../<project>/target/site/index.html

#### Use tomcat server for WEB RESTful application test
   After project was build for web-app go to: 
        
        ../<project>/web-app/target/rental-cars.war
        
   and copy "rental-cars.war" to tomcat /webapps directory
        
        ../tomcat/webapps/rental-cars.war
        
   for rest-app go to: 
          
        ../<project>/rest-app/target/rest-rental-cars.war
          
   and copy "rest-rental-cars.war" to tomcat /webapps directory
          
        ../tomcat/webapps/rest-rental-cars.war
        
   the web application should be available at:
        
         http://localhost:8080/rental-cars/
         
#### Use embedded jetty server for WEB RESTful application test
For run web-app, rest-app at jetty server you need go to:
        
        ../<project>/web-app/target/src/main/resources/application.properties
        
   and change some values for jetty server at "application.properties": 
        
        port=8088
        point.orders=orders
        point.clients=clients
        point.cars=cars
   
   after this you need to rebuild project:
   
        $ mvn clean install
        
   if you want again use tomcat server change values back to:
   
        port=8080
        point.orders=rest-rental-cars/orders
        point.clients=rest-rental-cars/clients
        point.cars=rest-rental-cars/cars
   
   Run terminal commands in project directory:

        $ mvn -pl web-app/ jetty:run

        $ mvn -pl rest-app/ jetty:run

   Once started, the application should be available at:

        http://localhost:8090

#### Use embedded jetty server for REST application test
   Run terminal command in project directory:

        $ mvn -pl rest-app/ jetty:run

        Once started, the REST server should be available at:

        http://localhost:8088

   Try CURL:

        Get all orders:
        $ curl -X GET -v http://localhost:8088/orders/all

        Get all orders DTO:
        $ curl -X GET -v http://localhost:8088/orders/all-dto

        Get order by ID:
        $ curl -X GET -v http://localhost:8088/orders/order/1

        Get order DTO by ID:
        $ curl -X GET -v http://localhost:8088/orders/dto/1

        Get order DTO by date:
        $ curl -X GET -v http://localhost:8088/orders/dto/2019-01-18/2019-01-26

        Add new order:
        $ curl -H "Content-Type: application/json" -X POST -d'{"orderId":"5","clientId":"1","carId":"2","rentalTime":"1","regDate":"2019-01-01"}' -v http://localhost:8088/orders

        Update new order:
        $ curl -H "Content-Type: application/json" -X PUT -d '{"orderId":"5","clientId":"2","carId":"3","rentalTime":"3","regDate":"2019-01-01"}' -v http://localhost:8088/orders

        Delete order by ID:
        $ curl -X DELETE -v http://localhost:8088/orders/order/5

        ...

#### Travis CI integration

        Visit <https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk>

#### Coveralls integration

        Visit <https://coveralls.io/github/Brest-Java-Course-2019/Vladislav-Kondratuk>

--------
>Produced by Vladiskav Kondratuk <br> Brest Java Courses    2019
