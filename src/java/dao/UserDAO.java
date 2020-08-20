/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Role;
import entity.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import exception.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author ThongLV
 */
public class UserDAO implements Serializable {

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

    public User findUserById(int userId) {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("User.findById");
            query.setParameter("id", userId);

            return (User) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public User findUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("User.findByEmail");
            query.setParameter("email", email);

            if (query.getResultList().size() > 0) {
                return (User) (query.getResultList() != null ? query.getResultList().get(0) : null);
            }
            return null;
        } finally {
            em.close();
        }
    }

    public void create(User user) throws PreexistingEntityException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (findUserByEmail(user.getEmail()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.");
            }
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUsers(String searchWord, int roleId, Role userRole) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u from User u "
                    + "WHERE u.name LIKE :searchWord ";

            if (roleId == -1) {
                if (userRole.getId() > 1) {
                    jpql += "AND u.role.id <> 1";
                }
            } else {
                jpql += "AND u.role.id = :roleId";
            }

            Query query = em.createQuery(jpql, User.class);
            query.setParameter("searchWord", "%" + searchWord + "%");

            if (roleId != -1) {
                query.setParameter("roleId", roleId);
            }
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void changeUserStatus(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.id = :userId";
            Query query = em.createQuery(jpql);
            query.setParameter("userId", userId);

            User user = (User) query.getSingleResult();

            em.getTransaction().begin();
            user.setStatus(!user.getStatus());
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(User newUserInfo) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            Query query = em.createQuery(jpql);
            query.setParameter("email", newUserInfo.getEmail());

            User user = (User) query.getSingleResult();

            em.getTransaction().begin();
            user.setName(newUserInfo.getName());
            user.setEncryptedPassword(newUserInfo.getEncryptedPassword());
            user.setPhone(newUserInfo.getPhone());
            user.setImg(newUserInfo.getImg());
            user.setRole(newUserInfo.getRole());
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
