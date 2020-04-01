# QLACK WebDesktop 

<p align="center">
    <a href="https://travis-ci.org/qlack/QLACK-WebDesktop" alt="TravisCI">
        <img src="https://travis-ci.org/qlack/QLACK-WebDesktop.svg?branch=master" />
    </a>
    <a href="https://qlack.com/" alt="Website">
        <img src="https://img.shields.io/website-up-down-green-red/https/qlack.com" />
    </a>
</p>

QLACK WebDesktop is an application which simulates a virtual desktop environment in one single browser window by allowing the seamless integration of multiple web applications under the same environment. 

All the integrated applications are displayed under a compact pop-up menu as tiles, defined by their title and icon and can be categorized based on their defined usability (system management, communication, etc.). Clicking on the tile of one application will lead to opening an iframe window which will display the front page of the web application and will provide you with the ability to use it exactly the same way you would do on a separate browser window.

Each application window supports every common desktop application feature, i.e. resize, minimize, maximize, drag, close and having multiple instances at the same time.   

Except from offering a complete and user-friendly solution in case your project has the need of supporting several application windows to be open at once, QLACK WebDesktop also provides the following features:
* Quick and easy integration with the applications during the deployment and/or the runtime using yaml configuration files
* Definition of different options for each application, i.e. setting up the default size of the window and disabling the features of each window (minimize, multiple instances, etc.)
* Exchange of real-time messages throughout the applications and publishing notification messages at the parent window
* Support of translations in multiple languages for the main system and the integrated applications
* Ability of serving as a reverse proxy for the integrated applications
* Support of a central authentication mechanism for all the applications using Single sign-on (SSO) access control
* Integration with any Lightweight Directory Access Protocol (LDAP) server
* Restriction of access to applications for all and/or specific users
* Provision of three system applications which provide useful tools for user, application and translation management
* Exchange messages between the integrated applications and the main QLACK WebDesktop system
* Easy and fast deployment using Docker

## Default System Applications

QLACK WebDesktop provides the following three ready-to-use applications:
1. Applications/Users Management
2. Translations Management
3. User Profile Management

### Applications/Users Management

This application is developed in order to provide management options for all the integrated QLACK WebDesktop applications, as well as the users who have access in the system using SSO.

Specifically, the following actions are provided:
* Browse the system users
* Create user groups and add or remove users in the groups
* Delete existing user groups
* View and update the properties of an application
* Disable the access to an application for specific users or user groups
* Disable completely the access to an application for all users
* Restrict the access to an application only for system administrators

Lastly, the option of integration a new application by uploading a yaml configuration file is available.

The access to this application is only possible for system administrators.

### Translations Management

This application can be used in order to modify the language and translation settings of the system, by offering the following options:
* Creating new languages
* Coping all the translations of existing languages to new ones
* Disabling available languages
* Creating new translations using the triple group-key-value
* Updating existing translated values

The access to this application is only possible for system administrators.

### User Profile Management

This application is accessible for every single user and provides the option of updating the attributes of their profile, which are the following:
* Profile picture (displayed at the system menu)
* Default background image (used as background for the system home page)
* Preferred system language (default is English)

## Installation Instructions

The instructions on how to install and use QLACK WebDesktop can be found [here](https://github.com/qlack/QLACK-WebDesktop/blob/master/MANUAL.md).

## Code Quality

<p align="center">
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=security_rating" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=reliability_rating" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktopp">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=sqale_rating" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=sqale_index" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=ncloc" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=coverage" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=duplicated_lines_density" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=code_smells" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=vulnerabilities" />
	</a>
	<a href="https://sonarcloud.io/dashboard?id=qlack_QLACK-WebDesktop">
  		<img src="https://sonarcloud.io/api/project_badges/measure?project=qlack_QLACK-WebDesktop&metric=bugs" />
	</a>
</p>


## Contributors

<p align="center">
	<a href="https://github.com/qlack/QLACK-WebDesktop/graphs/contributors">
  		<img src="https://contributors-img.firebaseapp.com/image?repo=qlack/qlack-webdesktop" />
	</a>
</p>
