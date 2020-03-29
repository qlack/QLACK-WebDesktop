# QLACK WebDesktop Installation Manual

This manual contains instructions on how to deploy and use the QLACK WebDesktop application.

## Deployment

1) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8082) <br/>
**spring.datasource.url**: the url connection of the database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

2) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications-management\qlack-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8090) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /applicationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

3) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

5) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations-management\qlack-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8091) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /translationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

6) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

7) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile-management\qlack-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8092) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /userProfileManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

8) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

9) Make a clean installation of the application by executing the following command on the root folder of the project (Java 8 must be installed and set as JAVA_HOME): <br/>
`mvnw.cmd clean install -Dmaven.test.skip=true` (for Windows OS) <br/>
`./mvnw clean install -Dmaven.test.skip=true` (for Linux OS) <br/>

10) Create the database schema by executing the command: <br/>
`CREATE DATABASE QlackWebDesktop CHARACTER SET utf8 COLLATE utf8_general_ci;`

11) Deploy the system QLACK WebDesktop applications by executing the commands: <br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &` <br/><br/>

12) Deploy the QLACK Web Desktop application by executing the command: <br/>
`nohup java -jar -Dsystem.default.language=en qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` <br/> <br/>
The argument _system.default.language_ is used in order to define the default language of the system and its possible values are 'en', 'el', 'fr' and 'de'.
Using the argument _apps.url_ you can specify the url of the .yaml configuration file of an application which will be registered in the system. The .yaml file should be accessible by performing a GET HTTP request on the provided url and multiple configuration files can be given during the deployment with the comma separator.

## Deployment using Docker

QLACK WebDesktop can also be deployed using Docker by executing the command `docker-compose up`.
This command will the following five containers:
1) qlack-webdesktop-db
This container contains a MySQL 8 installation with the schema 'QlackWebDesktop' in which all the other containers will be connected. <br/>
2) qlack-webdesktop-applications-management
This container runs a deployment of the Applications/Users Management application and prevents any external communication to the application by not publishing any ports. <br/>
3) qlack-webdesktop-translations-management
This container runs a deployment of the Translations Management application and prevents any external communication to the application by not publishing any ports. <br/>
4) qlack-webdesktop-user-profile-management
This container runs a deployment of the User Profile Management application and prevents any external communication to the application by not publishing any ports. <br/>
5) qlack-webdesktop-app
This container runs a deployment of the QLACK Web Desktop application, in which the three custom applications are already integrated. The application is accessible on port 80, which is published by the container.

Before running the containers, the following variables of the _.env_ file should be edited: <br/>
**PUBLISHED_PORT**: the port under which QLACK WebDesktop will be accessible on your system <br/>
**DATABASE_ROOT_PASSWORD**: the password of the ROOT user of the MySQL application <br/>
**SPRING_PROFILES_ACTIVE**: the security profile of the system ('sso' if SSO will be enabled, or leave empty) <br/>
**SYSTEM_DEFAULT_LANGUAGE**: the default language for all the users (possible values are 'en', 'el', 'fr' and 'de') <br/>
**APPS_URL**: the urls of the .yaml configuration files of the applications to be integrated (sepated by comma)

