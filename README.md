gcd-service : Restful and Web Service using Java EE 6 Technologies Deployed as an EAR
==============================================================================================
Technologies: CDI, JPA, EJB, JAX-RS, JAX-WS, JMS, WAR, EAR  
Summary: The `gcd-service` demonstrates web-enabled database application, using CDI, EJB, JPA and JMS, packaged as an EAR.   
Target Product: JBoss EAP or compatiable JEE 6


System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Enterprise Application Platform 6.1 or later. 
All you need to build this project is Java 6.0 (Java SDK 1.6) or later, Maven 3.0 or later.

Configure Maven
---------------
If you have not yet done so, you must [Configure Maven](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/CONFIGURE_MAVEN.md#configure-maven-to-build-and-deploy-the-quickstarts) before testing the quickstarts.

Use of EAP_HOME
---------------

In the following instructions, replace `EAP_HOME` with the actual path to your JBoss EAP 6 installation. The installation path is described in detail here: [Use of EAP_HOME and JBOSS_HOME Variables](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_OF_EAP_HOME.md#use-of-eap_home-and-jboss_home-variables).

Start the JBoss EAP Server
-------------------------

1. Open a command prompt and navigate to the root of the JBoss EAP directory.
2. The following shows the command line to start the server:

        For Linux:   EAP_HOME/bin/standalone.sh
        For Windows: EAP_HOME\bin\standalone.bat

Set test user creidential for JMS
-------------------------



Configure JMS Queue via JBoss console command
-------------------------



 
Build and Deploy the GDC Service
-------------------------
1. Make sure you have started the JBoss EAP server as described above.
2. Open a command prompt and navigate to the root directory of this gcd-service.
3. Type this command to build and deploy the archive:

        mvn clean install jboss-as:deploy

4. This will deploy `target/gcd-service.ear` to the running instance of the server.


Access the application 
---------------------
RESTFul Services

1. http://localhost:8080/gcd-service-web/rest/gcd [GET] : return all input numbers what have been requested from database
2. http://localhost:8080/gcd-service-web/rest/gcd [POST] formparam i1=integer&i2=integer : register two numbers into JMS queue

Web Services
WSDL URL : http://localhost:8080/gcd-service-web/GcdWS?wsdl

1. getGcd  : return gcd number by two numbers from JMS queue
2. gcdList : return all gcd number what have been calculated from database
3. gcdSum  : return total sum of gcd from database

Undeploy the Archive
--------------------

        mvn jboss-as:undeploy

