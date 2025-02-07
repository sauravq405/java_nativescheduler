# Java Native Email Scheduler Application

This application leverages native Java capabilities for scheduling tasks and sending emails at specified times. Here's how it works:

## Features

- **Scheduled Email Sending**: Utilizes Java's `ScheduledExecutorService` for defining when emails should be sent.
- **Time Logging**: Logs the time at which each email is scheduled to be sent in Indian Standard Time (IST).

## Setup

### Dependencies

- **Jakarta Mail** for email sending functionality.
```xml
<dependency>
   <groupId>org.eclipse.angus</groupId>
   <artifactId>jakarta.mail</artifactId>
   <version>2.0.3</version>
</dependency>
```
- **Lombok** optionally for avoiding boiler-plate code.
```xml
<dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
   <version>1.18.24</version> <!-- Use the latest version available -->
   <scope>provided</scope>
</dependency>
```
- **No external scheduling library** - uses Java's built-in `java.util.concurrent` package.

### Configuration

To configure and run the application:

1. **Email Configuration**: Directly set up your SMTP settings in your Java code:

    ```java
    Properties properties = System.getProperties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.starttls.required", "true");

    // Use the Session with authentication
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("your-email@gmail.com", "your-gmail-app-password");
        }
    });

2. **Building the Application:**

- **maven-shade-plugin:** This plugin is added to the <plugins> section of your <build> configuration. It will create an "uber-jar" (or fat JAR) which includes all your project's dependencies.
- **Execution Phase:** The plugin is configured to run during the package phase, which means it will execute when you run mvn package or any command that includes packaging.
- **Transformers:** The ManifestResourceTransformer is used to ensure the Main-Class attribute in the manifest file points to the correct main class for your application to run.
- **Main Class:** I've kept com.javanative.demo.JavaNativeMain as the main class in both the maven-jar-plugin and maven-shade-plugin. Make sure this matches your actual main class name.

After ensuring that your pom.xml is updated correctly, you can build your project with:
```bash
mvn clean package
```
This will create a new JAR in the target directory that includes all your dependencies, allowing you to run it with java -jar without needing to specify a classpath or having external dependencies.

3. **Running the Application:**
Run the application using your preferred IDE or via CLI with java -jar your-jar-file.jar after creating a JAR.
The application will automatically schedule and send emails based on the configuration you've set.

4. **JVM Options:** 
If your application requires specific JVM options (like memory allocation settings), you can add them to your command:
```bash
java -Xmx1024m -jar your-jar-file.jar
```

- **Xmx1024m**
- **Xmx:** This is a non-standard option (denoted by the -X prefix) that sets the maximum Java heap size.
- **1024m:** This specifies that the maximum heap size should be set to 1024 megabytes (which is 1 gigabyte). The 'm' stands for megabytes.

For debugging purposes, especially if you encounter issues with email functionality, run with:
```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar your-jar-file.jar
```


## Conclusion
This application showcases the use of Java's native capabilities for both scheduling tasks and email dispatch, demonstrating how to set up automated email sending with time logging in IST. For security reasons, remember not to hardcode your email credentials in the source code. Instead, use environment variables or a secure configuration mechanism to manage sensitive information.

Also, note that **Java Util Logging** has been used for logging, and the `logging.properties` file has been declared under `src/main/resources`.
