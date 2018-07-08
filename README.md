# Team 108 - Plagiarism Detector

An application to help instructors detect situations where two or more students submit similar solutions to an 
assignment, in which one version derives from another version through one or more behavior-preserving transformations.

## Authors

* **Apoorva Nagaraj**
* **Disha Sule**
* **Eswara Sai Surya Teja Gummalla**
* **Siddhesh Salgaonkar**

## Link for our live System
* [Web Application](http://ec2-54-209-77-120.compute-1.amazonaws.com:8080)



## Link for the setup video
* [Setup Video](https://youtu.be/Sxr98r4AO74)


## Link for the system demo
* [System Demo](https://youtu.be/ILvZQL5YafM)


## The link for the final presentation
* [Final Presentation](https://youtu.be/qk5sE3_3KHo)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and 
testing purposes. 

### Prerequisites

Java, Maven and Spring Boot 

##### Java 


> Check Java Version as follows:

```
$ java -version
java version "1.8.0_112"  
Java(TM) SE Runtime Environment (build 1.8.0_112-b15)  
Java HotSpot(TM) 64-Bit Server VM (build 25.112-b15, mixed mode)
```

```
$ where javac
C:\Program Files\Java\jdk1.8.0_112\bin\javac.exe
```

(Make sure above is JDK path and not JRE)

> If not installed:

* [Install Java] (https://java.com/en/download/)
* Make sure environment variable `JAVA_HOME is set to C:\Program Files\Java\jdk1.8.0_112\`
* Edit path environment variable to include `%JAVA_HOME%bin`

##### Maven

Refer below instructions:

* [Install Maven](https://maven.apache.org/download.cgi)
* [Sample](https://spring.io/guides/gs/maven/)

##### Spring Boot

Refer below instructions:

* [Install Spring Boot/CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html)
* [Home Page](https://spring.io/)

## Installing

Assuming you have cloned the repository to your local machine and have all the required installations/environment 
variables set. From 'team-108' (we'll call it 'root' from now onwards) folder, run following commands.

##### Command Line:

`mvn compile` (compiles the project)  
`mvn package` (packages the project into war/jar)  
`java -jar target/team-108-0.0.1-SNAPSHOT.war` (runs the application)

> OR 

`mvn spring-boot:run` 
(all packaged into one command -[Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html))


##### IntelliJ:

* Use IntelliJ Ultimate. Community Edition doesn't support Spring Boot
* Import the project using pom.xml
* IntelliJ will build the project automatically.
* Run the project using 'Run Configuration'.
* Make sure 'scope' element is commented as follows:
    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <!--<scope>provided</scope>-->
    </dependency>
    ```
* Follow same procedure for debugging:

> Hit localhost:8080 to access the application and you are all set!
 
    
## Project Structure (Evolving)

* team-108
    * lib - custom libraries for the project
    * public - (Front End for the web application)
        * app - AngularJS code
            * {entity} - entity in the system like ast, user etc.
                * controller - controllers for the entity
                * templates - view for the entity
            *  services - client services
        * assets - true static content like CSS, Images, Fonts etc.
        * index.html - single page for the application
    * src - all the source code for the project.
        * main - application code
            * java - source code 
                * edu.neu.ccs.plagiarismdetector
                    * {entity}
                        * {entity}.java
                        * {entity}Controller.java
                        * {entity}Service.java
                        * {entity}Repository.java
                    * PlagiarismDetectorApplication.java - Main Spring App
            * resources - properties, config etc
                * antlr4 - contains ANTLR grammer file for C language
                * application.properties - project properties file
        * test - test code
            * java - test sources
            * resources - mock C code files 
    * target - compiled source code (only after compilation)
    * .gitignore - git ignore config for the project.
    * Jenkinsfile - Jenkins Pipeline config
    * mvnw - maven runner (not required)
    * mvnw.cmd - maven runner (not required)
    * pom.xml - Specifies package dependency, metadata for the project.
    * README.md - Contains description about the project.
    
## Jenkins/Web Application Endpoints

* [Web Application](http://ec2-54-209-77-120.compute-1.amazonaws.com:8080)
* [Jenkins Setup](http://34.205.248.243:8080/)

## Additional Notes

### Built With

* [Java](https://java.com/en/) - Language of implementation
* [Maven](https://maven.apache.org/) - Build tool
* [Spring Boot](https://projects.spring.io/spring-boot/) - MVC web framework
* [Jenkins](https://jenkins.io/) - Build server (CI)
* [AWS](https://aws.amazon.com/) - Server stack
* [ANTLR](http://www.antlr.org/about.html) - Parser Generator
* [SonarQube](https://www.sonarqube.org/) - Continuous code quality
* [AngularJS](https://angularjs.org/) - Front/Client end of the app


### Logging

Coming Soon!


## Acknowledgments/References

* [Spring Boot with IntelliJ](https://spring.io/guides/gs/intellij-idea/)
* [Spring Boot with AngularJS](https://spring.io/guides/gs/consuming-rest-angularjs/)
* [Spring Boot Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)
* [Spring Boot Web Testing](https://spring.io/guides/gs/testing-web/)
* [Spring Boot with AngularJS - 2](https://examples.javacodegeeks.com/enterprise-java/spring/boot/spring-boot-and-angularjs-integration-tutorial/)
* [Spring Boot REST API](https://medium.com/@salisuwy/building-a-spring-boot-rest-api-a-php-developers-view-part-i-6add2e794646)
* [ANTLR Parsing](https://tomassetti.me/parsing-any-language-in-java-in-5-minutes-using-antlr-for-example-python/)
* [Angular File Upload](http://www.folio3.com/blog/angularjs-file-upload-example-tutorial/)
* [Spring Boot File Upload](https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/)
* [Jenkins Declarative Pipeline](https://jenkins.io/blog/2017/02/15/declarative-notifications/)
* [Angular Style Guide](https://github.com/johnpapa/angular-styleguide/blob/master/a1/README.md)
* [Angular Project Structure](https://scotch.io/tutorials/angularjs-best-practices-directory-structure)
* [Spring Boot Project Structure](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html)
* [Maven Project Structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
* [SonarQube exclusions](https://docs.sonarqube.org/display/SONAR/Analysis+Parameters)
* [GitHub Slack Integration](https://get.slack.help/hc/en-us/articles/232289568-GitHub-for-Slack#connect-github-to-slack)
* [GitHub Slack Integration - 2](https://cs5500.slack.com/services/B9ED606P5)
* [Smart Commits](https://confluence.atlassian.com/fisheye/using-smart-commits-298976812.html)
* [Protected Branches](https://help.github.com/enterprise/2.12/admin/guides/developer-workflow/configuring-protected-branches-and-required-status-checks/)
