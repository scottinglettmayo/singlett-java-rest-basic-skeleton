# Get Started with DSS's Spring Boot Java Basic REST Template

## Purpose
This project kick-starts development of a Basic CRUD REST API (supporting simple example HTTP GET, POST, PUT, and DELETE endpoints).  The endpoints manage a simple static list of employee data.  Requests may be made to fetch a JSON description of all employees or an ID specified employee.  Requests may also be made to delete a specified employee, add a new employee, or update employee information.

## Prerequisites
* JDK 1.8 or later
* Maven client (https://maven.apache.org/)
* Git client (https://git-scm.com/downloads)
* IDE: Eclipse or Intellij or others

## Setup
The first thing to do is to clone this project via the appropriate git command.

For Eclipse Developers:
* Install SpringBoot Tools plugin: "Help / Eclipse Marketplace ..." and search for "Spring Tool"
* Import project into Eclipse workspace: "File / Import ...", "Existing Maven Project".
* You may also need to clean project and/or run Maven update if the project shows any errors.

For Intellij Developers:
* Import project into Intellij workspace
* Choose "Edit configurations..." from the dropdown next to the play button.
* Press the green "+" sign in the upper left-hand corner.
* Choose to create a maven configuration.
* Name it.
* Add "spring-boot:run" to the "Command line" entry.
* Click on the green "+" sign again
* Click "spring boot"
* Select main class: edu.mayo.odn.rest.Application

## Running the project
In Eclipse, right-mouse menu on the project - click on "Run As / SpringBoot Application".  You may also run the project on a Tomcat server managed by Eclipse - "Run As / Run on Server". 

In Intellij, run "Application" class. 

## Swagger UI
The project has built-in Swagger UI to view and try out the REST services. Use the following to open the Swagger UI: [Swagger UI](http://localhost:8080/swagger-ui.html)

## Deploy the project
For deployment the project must be packaged as a `.war` file and deployed to a servlet container just like what you would do to deploy a web service project.  You can also deploy the project as a daemon process (standalone java app) without a servlet container by running the application main method in edu.mayo.javarestbasicskeleton.JavaRestBasicSkeletonApplication class, in this case you would package the project as a typical java application for deployment.

## Additional Info
You may notice the two maven files in the project: "mvnw" and "mvnw.cmd".  These are for anyone who wants to use Maven CLI without having it available globally.

## Support Contact

DL RST Tusken Raiders <DLRSTTuskenRaiders@exchange.mayo.edu>

## Last Updated

4/12/2019

