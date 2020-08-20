package dao;

import entity.Promotion;
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
public class PromotionDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");

    public void persist(Promotion object) {
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

    public List<Promotion> findPromotions() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Promotion.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Promotion findPromotionByUserId(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Promotion p WHERE p.userId.id = :userId";
            Query query = em.createQuery(jpql);
            query.setParameter("userId", userId);
            return  query.getResultList().size() > 0 ? (Promotion)query.getSingleResult(): null;
        }finally {
            em.close();
        }
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
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
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
