package org.bertural.deepforge.data;

import org.apache.commons.lang3.RandomStringUtils;
import org.bertural.deepforge.data.dao.UserDAO;
import org.bertural.deepforge.data.entities.EntityUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class Authentication {
    private static Logger logger = LoggerFactory.getLogger(Authentication.class);

    public static EntityUser login(String login, String password) {
        EntityUser user = null;
        Session session = null;
        Transaction t = null;
        try {
            session = Database.getFactory().openSession();
            t = session.beginTransaction();
            user = UserDAO.retrieveByLogin(session, login);
            if (user != null && user.getPasswordHash().equals(encrypt(password, user.getPasswordSalt()))) {
                user.setLastLoginAt(DateTime.now());
                t.commit();
            } else {
                user = null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (t != null && t.getStatus().canRollback())
                t.rollback();
            user = null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public static EntityUser register(String login, String password) {
        Session session = null;
        Transaction t = null;
        EntityUser user = null;
        try {
            session = Database.getFactory().openSession();
            t = session.beginTransaction();
            user = UserDAO.retrieveByLogin(session, login);
            if (user == null) {
                user = new EntityUser(login);
                user.setPasswordSalt(RandomStringUtils.random(32, true, true));
                user.setPasswordHash(encrypt(password, user.getPasswordSalt()));
                session.save(user);
                t.commit();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (t != null && t.getStatus().canRollback())
                t.rollback();
            user = null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public static String encrypt(String password, String salt) {
        try {
            byte[] val = MessageDigest.getInstance("SHA-256").digest((password + salt).getBytes("UTF-8"));
            String response = "";
            for (byte v : val) {
                String part = Integer.toHexString(0xFF & v);
                response += (part.length() == 1 ? "0" : "") + part;
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return password;
    }
}
