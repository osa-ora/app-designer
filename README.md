# Cloud Native Application Builder
A simple and innovative way to build cloud native application. To do so you configure the application technology stack. Use the stack to build the cloud native application. Generate the application artifacts as per the application architecture. For example, if you build Quarkus service that stores data in MySQL DB, the tool will generate the quarkus dependencies with dns name of MySQL name as per the architecture.  
At the moment, the tool allow you to design the architecture and save it to JSON and load it back from JSON.  
Next step to generate the whole stack for you in different technologies.  

# It is a Netbeans based project that uses Maven to build it simply run:  
```
mvn clean install compile package
```

Deploy the war file from the target folder into either Tomcat, JBoss or Glassfish or any other web container such as WebLogic or Websphere  

# Application Designer

This is how it looks like for this application: https://github.com/osa-ora/AcmeInternetBankingApp  

![builder](https://user-images.githubusercontent.com/18471537/83556127-a6d08f80-a50f-11ea-93f8-bcfd08f8f977.png)


You can configure the stack as well (under development)  

![configure](https://user-images.githubusercontent.com/18471537/83556165-b5b74200-a50f-11ea-976d-9400ebba93df.png)




The tool uses the basic features without much dependencies, only JS library: domarrow is used: https://github.com/schaumb/domarrow.js  