package dao;

import entity.Promotion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.eclipse.persistence.jpa.JpaEntityManager;

/**
 *
 * @author ThongLV
 */
public class PromotionDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");
    static final Logger LOGGER = Logger.getLogger(PromotionDAO.class);

    public void persist(Promotion object) {
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

    public List<Promotion> findPromotions(String sort) {
        EntityManager em = emf.createEntityManager();
        ((JpaEntityManager) em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
        try {
            String jpql = "SELECT p FROM Promotion p ORDER BY p.updateAt " + sort;
            Query query = em.createQuery(jpql);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }

    public List<Promotion> findPromotions() {
        EntityManager em = emf.createEntityManager();
        ((JpaEntityManager) em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
        try {
            Query query = em.createQuery("SELECT p FROM Promotion p");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }

    public Promotion findPromotionByUserId(int userId) {
        EntityManager em = emf.createEntityManager();
        ((JpaEntityManager) em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
        try {
            String jpql = "SELECT p FROM Promotion p WHERE p.userId.id = :userId";
            Query query = em.createQuery(jpql);
            query.setParameter("userId", userId);
            return query.getResultList().size() > 0 ? (Promotion) query.getSingleResult() : null;
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }

    public void removePromotion(int promotionId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "DELETE FROM Promotion p WHERE p.id = :promotionId";
            Query query = em.createQuery(jpql);
            query.setParameter("promotionId", promotionId);

            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void updatePromotion(int id, int score) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Promotion.findById");
            query.setParameter("id", id);
            Promotion currentPromotion = (Promotion) query.getSingleResult();
            em.getTransaction().begin();
            currentPromotion.setScore(score);
            em.merge(currentPromotion);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
