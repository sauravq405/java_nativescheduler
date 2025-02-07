package com.javanative.demo;

import com.javanative.demo.model.Email;
import com.javanative.demo.util.HTMLContent;
import com.javanative.demo.util.JavaNativeEmailSender;
import com.javanative.demo.util.JavaNativeScheduler;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JavaNativeMain {
    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static void main(String[] args) {
        printAppStartUpBanner();
        try {
            LogManager.getLogManager().readConfiguration(MethodHandles.lookup().lookupClass().getResourceAsStream("/logging.properties"));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not load default logging.properties:", e);
        }
        String to = "xxxxxx@gmail.com";
        String from = "xxxxxx@gmail.com";
        String subject = "Test Subject - Welcome to Our Service! 🎉🚀";
        String body = "This is a test email body.";
        String host = "smtp.gmail.com";
        String port = "587";
        String htmlContent = HTMLContent.getHTMLContent();
        Email email = Email.builder().to(to).from(from).subject(subject).body(body)
                .host(host).port(port).htmlContent(htmlContent).build();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                JavaNativeEmailSender javaNativeEmailSender = new JavaNativeEmailSender();
                javaNativeEmailSender.sendEmail(email);
            }
        };
        JavaNativeScheduler javaNativeScheduler = new JavaNativeScheduler();
        javaNativeScheduler.recurringActions(task);
    }

    public static void printAppStartUpBanner() {
        log.log(Level.INFO,
                "\n" +
                        "  ______  _   _ _____  _____  ______ _______ \n" +
                        " |  ____|| \\ | |  __ \\|  __ \\|  ____|__   __|\n" +
                        " | |__   |  \\| | |  | | |  | | |__     | |   \n" +
                        " |  __|  | . ` | |  | | |  | |  __|    | |   \n" +
                        " | |____ | |\\  | |__| | |__| | |____   | |   \n" +
                        " |______|_| \\_|_____/|_____/|______|  |_|   \n" +
                        "                                             \n" +
                        "Java Native Scheduler is now running!        \n");

    }
}