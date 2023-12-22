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
    public static final String ENTITIES_PACKAGE = "org.bertural.deepforge.data.entities";
    public static Logger logger = LoggerFactory.getLogger(Database.class);

    private static Database instance = null;
    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private Configuration config = null;
    private SessionFactory factory = null;

    private Database() {
        // Only log severe messages
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    public void configure() {
        logger.info("Configuring hibernate database..");
        config = new Configuration();
        Set<Class<?>> classes = new HashSet<>();
        Reflections reflections = new Reflections(ENTITIES_PACKAGE, new Scanner[0]);
        classes.addAll(reflections.getTypesAnnotatedWith(Entity.class));
        for (Class<?> c : classes) {
            config.addAnnotatedClass(c);
        }
        config.configure(new File("hibernate.sqlite.cfg.xml"));
        logger.info("Build hibernate session factory..");
        factory = config.buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
