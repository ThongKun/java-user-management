package dao;

import entity.Role;
import entity.User;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import exception.PreexistingEntityException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ThongLV
 */
public class UserDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab01PU");
    static final Logger LOGGER = Logger.getLogger(UserDAO.class);

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

    public User findUserById(int userId) {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("User.findById");
            query.setParameter("id", userId);

            return (User) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
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
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
    }

    public void create(User user) throws PreexistingEntityException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (findUserByEmail(user.getEmail()) != null) {
                LOGGER.error(new PreexistingEntityException("User " + user + " already exists."));
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

    public int getUsersNumbers(String searchWord, int roleId, Role userRole) {
        List<User> allUsers = findUsers(searchWord, roleId, userRole);
        return allUsers.size();
    }

    public List<User> findUsers(String searchWord, int roleId, Role userRole, int maxResult, int page) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u from User u "
                    + "WHERE u.name LIKE :searchWord ";

            if (roleId == -1) { //select all
                if (userRole.getId() > 1) { //user is not admin
                    jpql += "AND u.role.id = :userRoleId";
                }
            } else {
                if (userRole.getId() > 1) { //user is not admin
                    jpql += "AND u.role.id = :userRoleId";
                } else {
                    jpql += "AND u.role.id = :roleId";
                }
            }

            Query query = em.createQuery(jpql, User.class);
            query.setParameter("searchWord", "%" + searchWord + "%");

            if (roleId == -1) {
                if (userRole.getId() > 1) {
                    query.setParameter("userRoleId", userRole.getId());
                }
            } else {
                if (userRole.getId() > 1) { //user is not admin
                    query.setParameter("userRoleId", userRole.getId());
                } else {
                    query.setParameter("roleId", roleId);
                }
            }

            query.setMaxResults(maxResult);
            query.setFirstResult(maxResult * (page - 1));

            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
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
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
        return null;
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
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
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
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        } finally {
            em.close();
        }
    }
}
