package com.example.my.test.application.hibernate;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This code was taken almost in entirety from the Hibernate 3.1 reference
 * manual. The package name and formatting has been changed only.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static TransactionScope currentTransactionScope;

    public static final String ADDITIONAL_CONFIG_PROPERTIES_FILENAME = "additional.hibernate.cfg";

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration().configure();
            Properties additionalProperties = new Properties();
            InputStream additionalPropertiesStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(ADDITIONAL_CONFIG_PROPERTIES_FILENAME + ".properties");
            if (additionalPropertiesStream != null) {
                additionalProperties.load(additionalPropertiesStream);
                for (Map.Entry entry : additionalProperties.entrySet()) {
                    annotationConfiguration.setProperty((String) entry.getKey(), (String) entry.getValue());
                }
            }
            sessionFactory = annotationConfiguration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Transaction beginTransaction() {
        Session session = sessionFactory.getCurrentSession();
        TransactionScope transactionScope = new TransactionScope(session);
        if (currentTransactionScope == null || !currentTransactionScope.isActiveScope()) {
            currentTransactionScope = transactionScope;
        }
        return transactionScope;
    }

    public static void commitTransaction() {
        currentTransactionScope.commit();
    }

    public static void rollbackTransaction() {
        currentTransactionScope.rollback();
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
