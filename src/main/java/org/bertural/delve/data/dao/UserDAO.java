package org.bertural.delve.data.dao;

import org.bertural.delve.data.entities.EntityUser;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class UserDAO {

    public static EntityUser retrieveByLogin(Session session, String login) {
        String hqlStr = "SELECT user FROM " + EntityUser.class.getName() + " user WHERE user.login = :login";
        try {
            return (EntityUser)session.createQuery(hqlStr).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
