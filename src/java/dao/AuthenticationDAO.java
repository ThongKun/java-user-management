package dao;

import entity.User;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import util.EncryptPassword;

/**
 *
 * @author ThongLV
 */
public class AuthenticationDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");
    static final Logger LOGGER = Logger.getLogger(AuthenticationDAO.class);

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception: " +e);
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
            return user;
        } catch (NoResultException e) {
            LOGGER.error("Exception: " +e);
            return null;
        }
    }
}
