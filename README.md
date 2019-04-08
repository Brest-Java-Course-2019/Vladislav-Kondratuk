
# Rental Cars demo application
[![Build Status](https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk)
[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2019/Vladislav-Kondratuk/badge.svg?branch=master)](https://coveralls.io/github/Brest-Java-Course-2019/Vladislav-Kondratuk?branch=master)
# Project Title

Test Java project to study technologies such as git, maven, continuous integration, continuous delivery and others.
## Getting Started

### Prerequisites

        install jdk8

        install maven3+

        install git

Apache Maven 3.5.2, java 1.8 and local git required for build
You can get it at:

        https://www.oracle.com

        http://maven.apache.org

        https://git-scm.com/

#### Check environment configuration

        $ java -version

        $ export JAVA_HOME = ...

        $ mvn -version

### Installing
Download project from github

       git clone https://github.com/Brest-Java-Course-2019/Vladislav-Kondratuk.git

#### Build project

        $ mvn clean install

#### Preparing reports

        $ mvn site

        $ mvn site:site

        check: <project>/target/site/index.html

#### Use embedded jetty server for REST application test

        Run terminal in <project> directory:

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

#### Use embedded jetty server for WEB RESTful application test
        RESTful Web application are basically REST Architecture, it takes two servers to work.

        Run terminal in <project> directory:

        $ mvn -pl web-app/ jetty:run

        $ mvn -pl rest-app/ jetty:run

        Once started, the application should be available at:

        http://localhost:8090

#### Travis CI integration

        Visit <https://travis-ci.org/Brest-Java-Course-2019/Vladislav-Kondratuk>

#### Coveralls integration

        Visit <https://coveralls.io/github/Brest-Java-Course-2019/Vladislav-Kondratuk>

--------
>Produced by Vladiskav Kondratuk
Brest Java Courses
2019
