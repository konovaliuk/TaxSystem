# Tax reporting system

## Description

Tax reporting system. After registration as a taxpayer, client can add new tax reports and feedback requests for changing the inspector. Client can edit returned reports and requests. Inspector can add tax reports to his todo list, return or approve tax reports. Supervisor can add feedback requests to his todo list, return or complete feedback requests and change the inspector, responsible for reviewing of tax report. Admin can activate/deactivate users and change user's role. 

## Instalation and running 
### Prerequisites

- JDK, JRE 8 or later
- Apache Maven
- Apache Tomcat
- MySQL

### Set up
- Clone the project to local reposiroty and build `.war` using Maven command: `mvn clean package -DskipTests`.
- Create database using `initDB`.sql file. Specify user login and password in `/META-INF/context.xml file`. It must be a user with rights to modify database. 
- Deploy `.war` file to Apache Tomcat.
