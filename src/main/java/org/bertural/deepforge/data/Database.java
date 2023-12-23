package org.bertural.deepforge.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Database {
    public static Logger logger = LoggerFactory.getLogger(Database.class);
    public static final String ENTITIES_PACKAGE = "org.bertural.deepforge.data.entities";
    public static final String SQL_IMPL = "sqlite";

    private static Database instance = null;
    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private String databaseFile = null;
    private Configuration config = null;
    private SessionFactory factory = null;

    private Database() {
        // Only log severe messages
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    public void configure(String databaseFile) {
        logger.info("Configuring hibernate for database " + databaseFile + " (" + SQL_IMPL + ")..");
        this.databaseFile = databaseFile;
        config = new Configuration();
        logger.info("Collecting entities with reflections in " + ENTITIES_PACKAGE + "..");
        Set<Class<?>> classes = new HashSet<>();
        Reflections reflections = new Reflections(ENTITIES_PACKAGE, new Scanner[0]);
        classes.addAll(reflections.getTypesAnnotatedWith(Entity.class));
        for (Class<?> c : classes) {
            config.addAnnotatedClass(c);
        }
        config.setProperty("hibernate.connection.url", "jdbc:" + SQL_IMPL + ":" + databaseFile);
        config.configure(new File("hibernate." + SQL_IMPL + ".cfg.xml"));
        logger.info("Build hibernate session factory..");
        factory = config.buildSessionFactory();
    }

    public static SessionFactory getFactory() {
        return getInstance().factory;
    }
}
