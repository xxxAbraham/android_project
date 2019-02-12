package iut.nantes.serverMobile.infra;

import iut.nantes.serverMobile.api.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author Djurdjevic Sacha
 */
public class UserDao {

    private EntityManagerFactory factory = null;

    public void init() {
        factory = Persistence.createEntityManagerFactory("serverMobile");
    }

    public void close() {
        if (factory != null) {
            factory.close();
        }
    }

    public void addOne(User user) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if (em != null)
                em.close();
        }
    }
    
    public void addMany(List<User> userList){
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            for(User user : userList){
                em.persist(user);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null)
                em.close();
        }
    }

    public User selectOneById(Long id) {
        EntityManager em = null;
        User result = null;

        try {
            em = factory.createEntityManager();
            if (em.find(User.class, id) != null)
                result = em.find(User.class, id);
            return result;
        } finally {
            if (em != null)
                em.close();
        }
    }

    public List<User> selectAll() {
        EntityManager em = null;
        List<User> result = null;
        try{
            em = factory.createEntityManager();
            Query query = em.createQuery("select u from User u");
            if (!query.getResultList().isEmpty())
                result = query.getResultList();
            return result;
        }finally{
            if (em != null)
                em.close();
        }    
    }

    public void updateUserSpeudo(Long id, String speudo) {
        EntityManager em = null;
        try{
            em = factory.createEntityManager();
            em.getTransaction().begin();
            User user = selectOneById(id);
            if (user != null) {
                user.setSpeudo(speudo);
            }
            em.getTransaction().commit();
        }finally{
            if (em != null)
                em.close();
        }
    }

    public void updateUserEmail(Long id, String email) {
        EntityManager em = null;
        try{
            em = factory.createEntityManager();
            em.getTransaction().begin();
            User user = selectOneById(id);
            if (user != null) {
                user.setEmail(email);
            }
            em.getTransaction().commit();
        }finally{
            if (em != null)
                em.close();
        }
    }

    public void updateUserPassword(Long id, String password) {
        EntityManager em = null;
        try{
            em = factory.createEntityManager();
            em.getTransaction().begin();
            User user = selectOneById(id);
            if (user != null) {
                user.setPassword(password);
            }
            em.getTransaction().commit();
        }finally{
            if (em != null)
                em.close();
        }
    }

    public void deleteOneById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("serverMobile");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transac = em.getTransaction();
        transac.begin();

        if (em.find(User.class, id) != null) {
            em.remove(em.find(User.class, id));
        }
        transac.commit();
        em.close();
        emf.close();
    }

}
