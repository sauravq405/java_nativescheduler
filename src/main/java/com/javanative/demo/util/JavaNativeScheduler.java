package com.javanative.demo.util;

import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaNativeScheduler {
    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    public void recurringActions(Runnable task) {
        //setJavaUtilLogging_Programmatically();

        // Schedule a task to run every 10 seconds
        scheduler.scheduleAtFixedRate(() -> {
            if (task != null) {
                try {
                    task.run(); // Executes the provided task only if it's not null
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error executing scheduled task", e);
                }
            }
            String time = currentTimeInIST(System.currentTimeMillis());
            log.log(Level.INFO, "Scheduled task run at: {0}", time);
            logMemoryUsageInMegaBytes();
        }, 0, 10, TimeUnit.SECONDS);
        // Keep the JVM running
        try {
            Thread.sleep(60000); // Sleep for 1 minute to keep the scheduler running
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Task thread interrupted", e);
        } finally {
            scheduler.shutdown(); // Shutdown the scheduler when done
        }
    }

    /**
     * WARNING: This method runs indefinitely, please use with caution.
     *
     */
    public void recurringActionsIndefinitely(Runnable task) {
        // Schedule your task
        scheduler.scheduleAtFixedRate(() -> {
            if (task != null) {
                try {
                    task.run(); // Executes the provided task only if it's not null
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error executing scheduled task", e);
                }
            }
            String time = currentTimeInIST(System.currentTimeMillis());
            log.log(Level.INFO, "Scheduled task run at: {0}", time);
            logMemoryUsageInMegaBytes();
        }, 0, 10, TimeUnit.SECONDS);

        // Add a shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down scheduler...");
            scheduler.shutdown();
            try {
                // Wait a while for existing tasks to terminate
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow(); // Cancel currently executing tasks
                    // Wait a while for tasks to respond to being cancelled
                    if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                        log.severe("Scheduler did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("Scheduler shutdown successfully");
        }));

        // Keep the main thread alive indefinitely
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit the loop if interrupted
            }
        }
    }

    public String currentTimeInIST(long timeInMilliseconds) {
        // Example milliseconds
        long milliseconds = timeInMilliseconds; // Replace with your milliseconds
        // Convert milliseconds to an Instant
        Instant instant = Instant.ofEpochMilli(milliseconds);
        // Define the IST zone
        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        // Convert to IST
        ZonedDateTime istTime = instant.atZone(istZone);
        // Format the time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        // Print the time
        String formattedTime = istTime.format(formatter);
        return formattedTime;
    }

    private void setJavaUtilLogging_Programmatically(){
        // Set global logging level to FINE (equivalent to DEBUG in other frameworks)
        Logger.getLogger("").setLevel(Level.FINE);

        // Optionally, set the level for a specific class logger
        Logger.getLogger(MethodHandles.lookup().lookupClass().getName()).setLevel(Level.FINE);
    }

    public static void logMemoryUsageInMegaBytes() {
        // Convert bytes to megabytes
        long heapUsed = memoryMXBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
        long heapMax = memoryMXBean.getHeapMemoryUsage().getMax() / (1024 * 1024);
        long nonHeapUsed = memoryMXBean.getNonHeapMemoryUsage().getUsed() / (1024 * 1024);
        log.log(Level.INFO, "Heap Memory Usage: {0} MB used of {1} MB max", new Object[]{heapUsed, heapMax});
        log.log(Level.INFO, "Non-Heap Memory Usage: {0} MB", nonHeapUsed);
    }
}
