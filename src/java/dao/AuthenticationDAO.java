/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import util.EncryptPassword;

/**
 *
 * @author ThongLV
 */
public class AuthenticationDAO implements Serializable{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public User checkLogin(String email, String password) {
        EntityManager em = emf.createEntityManager();

        String jpql = "Select u From User u "
                + "Where u.email = :email And u.encryptedPassword = :encryptedPassword";

        Query query = em.createQuery(jpql);
        query.setParameter("email", email);
        
        query.setParameter("encryptedPassword", EncryptPassword.encrypt(password));
         
        try {
            User user = (User) query.getSingleResult();
            System.out.println("user role " + user.getRole());
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}
