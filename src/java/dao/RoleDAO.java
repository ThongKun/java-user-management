package dao;

import entity.Role;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ThongLV
 */
public class RoleDAO implements Serializable {

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

    public Role findRole(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Role.findById");
            query.setParameter("id", id);
            return (Role) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Role> findAll() {
         EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Role.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
