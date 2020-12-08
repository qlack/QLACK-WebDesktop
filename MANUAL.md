# QLACK WebDesktop Installation Manual

This manual contains instructions on how to deploy and use the QLACK WebDesktop application.

## Manual Deployment

1) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8082) <br/>
**spring.datasource.url**: the url connection of the database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

2) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.host**: the url under which QLACK WebDesktop will be served (ex. http://localhost:8082) <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>
**webdesktop.administrator.username**: the user id of the user who will be the QLACK WebDesktop system administrator <br/>

3) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications-management\qlack
-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8090) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /applicationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

4) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

5) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications
-management\qlack-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.host**: the url under which QLACK WebDesktop will be served (ex. http://localhost:8082) <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

6) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations-management\qlack
-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8091) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /translationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

7) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

8) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations
-management\qlack-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.host**: the url under which QLACK WebDesktop will be served (ex. http://localhost:8082) <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

9) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile-management\qlack
-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8092) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /userProfileManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

10) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

11) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile
-management\qlack-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.host**: the url under which QLACK WebDesktop will be served (ex. http://localhost:8082) <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

12) Make a clean installation of the application by executing the following command on the root folder of the
 project (Java 8 must be installed and set as JAVA_HOME): <br/>
`mvnw.cmd clean install -Dmaven.test.skip=true` (for Windows OS) <br/>
`./mvnw clean install -Dmaven.test.skip=true` (for Linux OS) <br/>

13) Create the database schema by executing the command: <br/>
`CREATE DATABASE QlackWebDesktop CHARACTER SET utf8 COLLATE utf8_general_ci;`

14) Deploy the system QLACK WebDesktop applications by executing the commands: <br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &` <br/><br/>
If SSO is enabled, deploy the applications with the following command:
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &` <br/><br/>

15) Deploy the QLACK Web Desktop application by executing the command: <br/>
`nohup java -jar -Dsystem.default.language=en qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` <br/> <br/>
If SSO is enabled, deploy QLACK WebDesktop with the following command:
`nohup java -jar -Dsystem.default.language=en -Dspring.profiles.active=sso qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` <br/> <br/>
The argument _system.default.language_ is used in order to define the default language of the system and its possible values are 'en', 'el', 'fr' and 'de'.
Using the argument _apps.url_ you can specify the url of the .yaml configuration file of an application which will be registered in the system. The .yaml file should be accessible by performing a GET HTTP request on the provided url and multiple configuration files can be given during the deployment with the comma separator.

## Docker Deployment

QLACK WebDesktop can also be deployed using Docker Compose. There are three docker-compose files which can be used
 for the deployment:
 - docker-compose-no-auth.yml: Deployment without SSO integration
 - docker-compose-keycloak.yml: Deployment with integrated Keycloak server and NGINX configuration
 - docker-compose-keycloak-ssl.yml: Deployment with integrated Keycloak server, NGINX configuration and Let's Encrypt
  SSL certificates

### docker-compose-no-auth.yml
This Docker Compose file contains the following Docker containers:
1) qlack-webdesktop-db: This container contains a MySQL 8 installation with the schema 'QlackWebDesktop' in which all
 the other containers will be connected. <br/>
2) qlack-webdesktop-applications-management: This container runs a deployment of the Applications/Users Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
3) qlack-webdesktop-translations-management: This container runs a deployment of the Translations Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
4) qlack-webdesktop-user-profile-management: This container runs a deployment of the User Profile Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
5) qlack-webdesktop-app: This container runs a deployment of the QLACK Web Desktop application, in which the three
 custom applications are already integrated. <br/>
6) qlack-webdesktop-logs: This container provides a UI interface for accessing all the logs of the running Docker containers.

The following variables of the _.env_ file should be edited: <br/>
**DATABASE_ROOT_PASSWORD**: the password of the ROOT user of the MySQL application <br/>
**WEBDESKTOP_VERSION**: the version of the QLACK WebDesktop release <br/>
**SYSTEM_DEFAULT_LANGUAGE**: the default language for all the users (possible values are 'en', 'el', 'fr' and 'de') <br/>
**APPS_URL**: the urls of the .yaml configuration files of the applications to be integrated (separated by commas) <br/>
**DATABASE_PORT**: the port under which you can connect to the MySQL server on your system <br/>
**WEBDESKTOP_PORT**: the port under which QLACK WebDesktop will be accessible on your system <br/>
**LOGS_PORT**: the port under which the Docker logs will be accessible on your system <br/>

Start the containers with the command `docker-compose -f docker-compose-no-auth.yml up -d`

### docker-compose-keycloak.yml
This Docker Compose file contains the following Docker containers:
1) qlack-webdesktop-nginx: This container contains an NGINX running server which will be responsible for forwarding
 the requests to the Keycloak server, as well as the QLACK WebDesktop REST API calls. <br/>
2) qlack-webdesktop-keycloak: This container contains a Keycloak server instance, which will be used by the other containers for the SSO integration. <br/>
3) qlack-webdesktop-db: This container contains a MySQL 8 installation with the schema 'QlackWebDesktop' in which all
 the other containers will be connected. <br/>
4) qlack-webdesktop-applications-management: This container runs a deployment of the Applications/Users Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
5) qlack-webdesktop-translations-management: This container runs a deployment of the Translations Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
6) qlack-webdesktop-user-profile-management: This container runs a deployment of the User Profile Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
7) qlack-webdesktop-app: This container runs a deployment of the QLACK Web Desktop application, in which the three
 custom applications are already integrated. <br/>
8) qlack-webdesktop-logs: This container provides a UI interface for accessing all the logs of the running Docker containers.

The following variables of the _.env_ file should be edited: <br/>
**DATABASE_ROOT_PASSWORD**: the password of the ROOT user of the MySQL application <br/>
**OAUTH2_CLIENT**: the id of the SSO client <br/>
**OAUTH2_CLIENT_SECRET**: the key for accessing the restricted SSO client <br/>
**KEYCLOAK_ADMIN_USERNAME**: the username of the Keycloak admin user <br/>
**KEYCLOAK_ADMIN_PASSWORD**: the password of the Keycloak admin user <br/>
**WEBDESKTOP_VERSION**: the version of the QLACK WebDesktop release <br/>
**SYSTEM_DEFAULT_LANGUAGE**: the default language for all the users (possible values are 'en', 'el', 'fr' and 'de') <br/>
**WEBDESKTOP_URL**: the url under which QLACK WebDesktop will be served (ex. http://my-domain) <br/>
**WEBDESKTOP_ADMIN**: the user id of the user who will be the QLACK WebDesktop system administrator <br/>
**APPS_URL**: the urls of the .yaml configuration files of the applications to be integrated (separated by commas) <br/>
**NGNIX_PORT**: the port under which the application will be running <br/>
**KEYCLOAK_PORT**: the port under which the Keycloak admin panel will be accessible on your system <br/>
**DATABASE_PORT**: the port under which you can connect to the MySQL server on your system <br/>
**WEBDESKTOP_PORT**: the port under which QLACK WebDesktop will be accessible on your system <br/>
**LOGS_PORT**: the port under which the Docker logs will be accessible on your system <br/>

Overwrite the NGINX configuration _docker-data/nginx/app.conf_ with the following content:
```
server {
    listen NGNIX_PORT;
    server_name server-name;
    server_tokens off;

    location / {
        proxy_pass http://server-name:WEBDESKTOP_PORT;
    }

    location /auth/ {
        proxy_pass          http://server-name:KEYCLOAK_PORT/auth/;
        proxy_set_header    Host               $host;
        proxy_set_header    X-Real-IP          $remote_addr;
        proxy_set_header    X-Forwarded-For    $proxy_add_x_forwarded_for;
        proxy_set_header    X-Forwarded-Host   $host;
        proxy_set_header    X-Forwarded-Server $host;
        proxy_set_header    X-Forwarded-Port   $server_port;
        proxy_set_header    X-Forwarded-Proto  $scheme;
    }
}
```
`server-name` must be updated to match your server name, as well as `NGNIX_PORT`, `WEBDESKTOP_PORT` and `KEYCLOAK_PORT
` must be updated to match the ports you used in the `env` file.

Start the containers with the command `docker-compose -f docker-compose-keycloak.yml up -d`

After the containers have successfully run (this will require about 5 minutes), follow these steps in order to create
 a new Keycloak client for QLACK WebDesktop:
1) Navigate the Keycloak admin panel (http://server-name:NGNIX_PORT/auth/)]
2) Click on `Administration Console`
3) Login using the credentials of the `admin` user
4) From the left menu, click on `Clients`
5) On the table, click on `Create`
6) Enter the desired `Client ID` and click on `Save`
7) Wait a few seconds to be redirected to the configuration page of the new Client
8) Change the access type from `public` to `confidential`
9) Update the `Valid Redirect URIs` with the URI of your installation (ex. http://server-name:NGNIX_PORT/*)
10) Click on `Save`
11) On the top, navigate to the `Credentials` panel and copy paste the `Secret` value
12) Update the values of the `OAUTH2_CLIENT` and `OAUTH2_CLIENT_SECRET` variables of the _.env_ file 
13) Restart the containers with the command `docker-compose -f docker-compose-keycloak.yml up -d`

### docker-compose-keycloak-ssl.yml
This Docker Compose file contains the following Docker containers:
1) qlack-webdesktop-nginx: This container contains an NGINX running server which will be responsible for forwarding
 the requests to the Keycloak server, as well as the QLACK WebDesktop REST API calls. <br/>
2) qlack-webdesktop-certbot: This container will be responsible for auto-updated the SSL certificates every 90 days
 <br/>
3) qlack-webdesktop-keycloak: This container contains a Keycloak server instance, which will be used by the other
 containers for the SSO integration. <br/>
4) qlack-webdesktop-db: This container contains a MySQL 8 installation with the schema 'QlackWebDesktop' in which all
 the other containers will be connected. <br/>
5) qlack-webdesktop-applications-management: This container runs a deployment of the Applications/Users Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
6) qlack-webdesktop-translations-management: This container runs a deployment of the Translations Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
7) qlack-webdesktop-user-profile-management: This container runs a deployment of the User Profile Management
 application and prevents any external communication to the application by not publishing any ports. <br/>
8) qlack-webdesktop-app: This container runs a deployment of the QLACK Web Desktop application, in which the three
 custom applications are already integrated. <br/>
9) qlack-webdesktop-logs: This container provides a UI interface for accessing all the logs of the running Docker
 containers.

The following variables of the _.env_ file should be edited: <br/>
**DATABASE_ROOT_PASSWORD**: the password of the ROOT user of the MySQL application <br/>
**OAUTH2_CLIENT**: the id of the SSO client <br/>
**OAUTH2_CLIENT_SECRET**: the key for accessing the restricted SSO client <br/>
**KEYCLOAK_ADMIN_USERNAME**: the username of the Keycloak admin user <br/>
**KEYCLOAK_ADMIN_PASSWORD**: the password of the Keycloak admin user <br/>
**WEBDESKTOP_VERSION**: the version of the QLACK WebDesktop release <br/>
**SYSTEM_DEFAULT_LANGUAGE**: the default language for all the users (possible values are 'en', 'el', 'fr' and 'de') <br/>
**WEBDESKTOP_URL**: the url under which QLACK WebDesktop will be served (ex. http://my-domain) <br/>
**WEBDESKTOP_ADMIN**: the user id of the user who will be the QLACK WebDesktop system administrator <br/>
**APPS_URL**: the urls of the .yaml configuration files of the applications to be integrated (separated by commas) <br/>
**NGNIX_PORT**: the port under which the application will be running <br/>
**KEYCLOAK_PORT**: the port under which the Keycloak admin panel will be accessible on your system <br/>
**DATABASE_PORT**: the port under which you can connect to the MySQL server on your system <br/>
**WEBDESKTOP_PORT**: the port under which QLACK WebDesktop will be accessible on your system <br/>
**LOGS_PORT**: the port under which the Docker logs will be accessible on your system <br/>

Overwrite the NGINX configuration _docker-data/nginx/app.conf_ with the following content:
```
server {
    listen NGNIX_PORT;
    server_name server-name;
    server_tokens off;

    location / {
        return 301 https://$host$request_uri;
    }

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }
}

server {
    listen 443 ssl;
    server_name server-name;

    ssl_certificate /etc/letsencrypt/live/server-name/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/server-name/privkey.pem;

    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;

    location / {
        proxy_pass http://server-name:WEBDESKTOP_PORT;
    }

    location /auth/ {
        proxy_pass          http://server-name:KEYCLOAK_PORT/auth/;
        proxy_set_header    Host               $host;
        proxy_set_header    X-Real-IP          $remote_addr;
        proxy_set_header    X-Forwarded-For    $proxy_add_x_forwarded_for;
        proxy_set_header    X-Forwarded-Host   $host;
        proxy_set_header    X-Forwarded-Server $host;
        proxy_set_header    X-Forwarded-Port   $server_port;
        proxy_set_header    X-Forwarded-Proto  $scheme;
    }
}
```
`server-name` must be updated to match your server name, as well as `NGNIX_PORT`, `WEBDESKTOP_PORT` and `KEYCLOAK_PORT
` must be updated to match the ports you used in the `env` file.

Update the following lines of the SSL configuration file _init-letsenrcypt.sh_:
- line 10: Enter the public domain(s) of your server
- line 13: Add a valid email alias to receive email notifications for the Let's Encrypt SSL Certificates expirations

Modify the permissions of the SSL configuration file to be executable with the command `chmod +x init-letsenrcypt.sh
` and run the script with the command `./init-letsenrcypt.sh`

Start the containers with the command `docker-compose -f docker-compose-keycloak-ssl.yml up -d`

After the containers have successfully run (this will require about 5 minutes), follow these steps in order to create
 a new Keycloak client for QLACK WebDesktop:
1) Navigate the Keycloak admin panel (http://server-name:NGNIX_PORT/auth/)]
2) Click on `Administration Console`
3) Login using the credentials of the `admin` user
4) From the left menu, click on `Clients`
5) On the table, click on `Create`
6) Enter the desired `Client ID` and click on `Save`
7) Wait a few seconds to be redirected to the configuration page of the new Client
8) Change the access type from `public` to `confidential`
9) Update the `Valid Redirect URIs` with the URI of your installation (ex. http://server-name:NGNIX_PORT/*)
10) Click on `Save`
11) On the top, navigate to the `Credentials` panel and copy paste the `Secret` value
12) Update the values of the `OAUTH2_CLIENT` and `OAUTH2_CLIENT_SECRET` variables of the _.env_ file 
13) Restart the containers with the command `docker-compose -f docker-compose-keycloak.yml up -d`

## Applications Integration

A QLACK WebDesktop application is identified based on the following information: <br/>
**applicationName**: the identifier name of the application <br/>
**groupName**: the group in which the application is registered <br/>
**appUrl**: the url where the application is deployed <br/>
**appPath**: the context path of the application home page  <br/>
**proxyAppPath**: the reverse proxy path under which QLACK WebDesktop will serve the application <br/>
**icon**: the url of the application icon
**iconSmall**: the url of the small application icon
**width**: the default width of the application window <br/>
**minWidth**: the maximum width of the application window <br/>
**height**: the default height of the application window <br/>
**minHeight**: the maximum height of the application window <br/>
**active**: defines if the application is active or not <br/>
**multipleInstances**: defines if the application window can be opened multiple times or not <br/>
**resizable**: defines if the application window can be resised or not <br/>
**draggable**: defines if the application window can be dragged or not <br/>
**closable**: defines if the application window can be closed or not <br/>
**minimizable**: defines if the application window can be minimized or not <br/>
**maximizable**: defines if the application window can be maximized or not <br/>
**showTitle**: defines if the application title will be shown or not <br/>
**system**: defines if the application is accessible only by the system administrator or not <br/>
**restrictAccess**: defines if the application will be accessible based on user/user group rules or not <br/>
**version**: the current version of the application <br/>

QLACK WebDekstop provides three ways of integrating new applications:

### Form
1) Open the Applications/Users Management application and select 'Create'
2) Fill in the fields
3) Click on Save

### Yaml file
1) Open the Applications/Users Management application and select 'Upload'
2) Select a .yaml configuration file
3) Click on Submit

### Deployment
While deploying QLACK WebDesktop, you can provide the url of the .yaml configuration file on the _apps_url_ argument.

An example of a configuration file can be found here: https://github.com/qlack/QLACK-WebDesktop/blob/master/qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/src/main/resources/configuration.yaml

## Translations

QLACK WebDesktop is deployed with multiple ready-to-read translations for multiple languages, which are defined at the _QLACK-WebDesktop/qlack-webdesktop-app/src/main/resources/qlack-lexicon-config.yaml_ file. 
The values of these translations can be updated before each QLACK WebDesktop re-deploy by changing the value and adding the pair key:value `forceUpdate: true` below each updated key. For example:
```
- group: webdesktop-ui
  locale: en
  keys:
    - groupOthers: Others
      forceUpdate: true
```

Alternatively, the Translations Management application provides the ability of viewing and updating all the system translations, as well as creating new languages and copying the already existing translations of another language.

When creating new applications or/and new application groups, their translations must be set up via the Translations Management application.

## Applications Messages

QLACK WebDesktop also provides the ability of exchanging messages between the integrated applications and/or the main application, by using the [QLACK QPubSub Javascript library](https://www.npmjs.com/package/@qlack/qpubsub) or the [QLACK QPubSub Angular Wrapper](https://www.npmjs.com/package/@qlack/qng-pubsub).

Each integrated application, which will be making use of the QLACK QPubSub library, must be registered as a _client_ before being able to _publish_ messages or _subscribe_ to message topics.

QLACK WebDesktop provides the following ready-to-use topics:
1) QNotifications: display notifications on the main application window
```
this.qPubSubService.publish('QNotifications', 'Notifications Message');
```
2) QUserInformation: retrieve the information of the logged user
```
this.qPubSubService.subscribe('QUserInformationRequest', () => {
    this.qPubSubService.subscribe('QUserInformationRequest', (message: QPubSub.Message) => {
		console.log(message);
	});
});
```
3) QDefaultLanguage: retrieve the system language locale
```
this.qPubSubService.subscribe('QDefaultLanguageRequest', () => {
    this.qPubSubService.subscribe('QDefaultLanguageResponse', (message: QPubSub.Message) => {
		console.log(message);
	});
});
```
