# Cloud Native Application Builder
A simple and innovative way to build cloud native application. To do so you configure the application technology stack. Use the stack to build the cloud native application. Generate the application artifacts as per the application architecture. For example, if you build Quarkus service that stores data in MySQL DB, the tool will generate the quarkus dependencies with dns name of MySQL name as per the architecture.  
The tool will also generate the deployment artifact for all components based on Kubernates/OpenShift. Think of this tool as a way to handle cloud native applications as a monolith application, from the design, code generation and deployment configurations.  
At the moment, the tool support some generators like Quarkus and Java where it will generate both project skeleton and deployment scripts. While in some generators it will only generate the deployment artifacts such as Tomcat, JBoss, MySQL and PostgreSQL.  
This is just the initial functionality which will be extended to generate all possible configurations.  
Later on, support to generate the whole stack in different technologies will be added gradually by adding more Generator types (classes that implements IGenerator interface).   
Also the GUI will be later on generated only by the available Generators.  

# It is a Netbeans based project that uses Maven to build it simply run:  
```
mvn clean install compile package
```

Deploy the war file from the target folder into either Tomcat, JBoss or Glassfish or any other web container such as WebLogic or Websphere  

# To deploy to OpenShift, either from the GUI or run the following commands:  
```
oc new-app --name=my-designer jboss-webserver30-tomcat8-openshift:1.3~https://github.com/osa-ora/app-designer  
```
To track the progress of the build (i.e. logs): 
```
oc logs -f bc/my-designer 
```
Expose a router:  
```
oc expose service my-desginer 
```
Get the router URL and add at the end: /AppBuilder-1.0-SNAPSHOT/  
```
oc get route my-designer 
```

# Application Designer

This is how it looks like for this application: https://github.com/osa-ora/AcmeInternetBankingApp  

![builder](https://user-images.githubusercontent.com/18471537/83556127-a6d08f80-a50f-11ea-93f8-bcfd08f8f977.png)


You can configure the stack as well (under development)  

![configure](https://user-images.githubusercontent.com/18471537/83556165-b5b74200-a50f-11ea-976d-9400ebba93df.png)

And you can also configure each component properties, for example MySQL DB configurations, etc.

![sample](https://user-images.githubusercontent.com/18471537/84061321-03272980-a9be-11ea-9380-80aaf0c66d70.png)
  
The tool uses the basic features without much dependencies, only JS library: domarrow is used: https://github.com/schaumb/domarrow.js  