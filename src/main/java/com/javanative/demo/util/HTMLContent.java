package com.javanative.demo.util;

public class HTMLContent {

    public static String getHTMLContent(){
        // Set the message text and the header information for the email
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; }\n" +
                "        .container { background: #ffffff; margin: 20px auto; padding: 20px; max-width: 600px; border-radius: 8px; }\n" +
                "        .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }\n" +
                "        .content { padding: 20px; }\n" +
                "        .button { background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 5px; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class='container'>\n" +
                "        <div class='header'>\n" +
                "            <h1>Welcome to Our Service!</h1>\n" +
                "        </div>\n" +
                "        <div class='content'>\n" +
                "            <p>Hello,</p>\n" +
                "            <p>Thank you for signing up for our service. We're excited to have you on board!</p>\n" +
                "            <p>Here's a little something to get you started:</p>\n" +
                "            <a href='#' class='button'>Get Started</a>\n" +
                "            <p>If you have any questions, feel free to reach out to our support team.</p>\n" +
                "            <p>Best regards,<br>Your Service Team</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        return htmlContent;
    }
}
