# DeliveryCost demo project

# Author Vladislav-Kondratuk

# Project Title

Test Java project to study technologies such as git, maven, continuous integration, continuous delivery and others.

## Getting Started

### Prerequisites

install jdk8<br />
install git<br />
install maven3+

### Installing

download project from github 

```
git clone https://github.com/Brest-Java-Course-2019/Vladislav-Kondratuk.git
mvn clean install
```

## Running the tests

```
mvn test
```

## Deployment

```
mvn site
  
mvn site:stage
  
check: <project>/target/stage/index.html 
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the MIT License
