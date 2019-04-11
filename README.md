
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
   After project was build you can go to:
   
        http://localhost:8080/manager/html/
        
   and choose .war files for deploy web-app, rest-app in deploy section. 
   
   Another way to do this for web-app go to: 
        
        ../<project>/web-app/target/rental-cars.war
        
   and copy "rental-cars.war" to tomcat /webapps directory
        
        ../tomcat/webapps/rental-cars.war
        
   for rest-app go to:
          
        ../<project>/rest-app/target/rest-rental-cars.war
          
   and copy "rest-rental-cars.war" to tomcat /webapps directory
          
        ../tomcat/webapps/rest-rental-cars.war
        
   the web application should be available at:
        
         http://localhost:8080/rental-cars/
         
   if you wanna shutdown or stop tomcat server go to:
   
        http://localhost:8080/manager/html/
   
   choose app click to "undeploy" for shutdown and removing, or click to "stop" for stopping app.
   Also you can remove rest-app.war, web-app.war files from:
        
        ../tomcat/webapps/
        
   for shutdown and removing this apps from tomcat server.
#### Use embedded jetty server for WEB RESTful application test
You need to run these commands in different tabs or terminal windows:
        
        $ mvn -pl web-app/ jetty:run -P jetty

        $ mvn -pl rest-app/ jetty:run

   Once started, the application should be available at:

        http://localhost:8090

   if you wanna shutdown jetty server, go to terminal tab or window
   you wanna stop and press "CTRL+C".
    
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
