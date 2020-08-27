package dao;

import entity.Role;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ThongLV
 */
public class RoleDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");
    static final Logger LOGGER = Logger.getLogger(RoleDAO.class);

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
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
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }

    public List<Role> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Role.findAll");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }
}
