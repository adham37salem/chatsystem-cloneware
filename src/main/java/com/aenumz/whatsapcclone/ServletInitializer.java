package com.aenumz.whatsapcclone;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for deploying the WhatsapcClone application as a WAR file
 * to an external servlet container (e.g., Tomcat, Jetty, etc.).
 * 
 * <p>This class extends {@link SpringBootServletInitializer} and is required when
 * packaging the Spring Boot application as a WAR file instead of a standalone JAR.
 * It allows the application to be deployed to traditional servlet containers that
 * follow the Servlet 3.0+ specification.</p>
 * 
 * <p>When the application is packaged as a WAR and deployed to a servlet container,
 * the container will call the {@link #configure(SpringApplicationBuilder)} method
 * during application startup to initialize the Spring Boot application context.</p>
 * 
 * <p>For standalone JAR deployments (using embedded Tomcat), this class is not
 * required and the application will use the main method in
 * {@link WhatsapcCloneApplication} directly.</p>
 * 
 * @author WhatsapcClone Development Team
 * @version 1.0
 * @since 1.0
 * @see SpringBootServletInitializer
 * @see WhatsapcCloneApplication
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configures the Spring Boot application builder with the main application class.
     * 
     * <p>This method is called by the servlet container during application startup
     * when deploying as a WAR file. It specifies which Spring Boot application class
     * should be used as the primary source for configuration.</p>
     * 
     * @param application the Spring application builder instance provided by the
     *                    servlet container
     * @return the configured SpringApplicationBuilder with the main application
     *         class set as the source
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WhatsapcCloneApplication.class);
    }

}
