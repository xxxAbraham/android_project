package iut.nantes.serverMobile.infra;

import iut.nantes.serverMobile.api.entities.Event;
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
public class EventDao {

    private EntityManagerFactory factory = null;

    public void init() {
        factory = Persistence.createEntityManagerFactory("serverMobile");
    }

    public void close() {
        if (factory != null) {
            factory.close();
        }
    }

    public void addOne(Event event) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            em.persist(event);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void addMany(List<Event> eventList) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            for (Event event : eventList) {
                em.persist(event);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void addUser(Long id, User user) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            Event event = selectOneById(id);
            if (event != null) {
                event.getUserList().add(user);
                em.merge(event);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void addManyUser(Long id, List<User> userList) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            Event event = selectOneById(id);
            if (event != null) {
                for(User user : userList){
                    event.getUserList().add(user);
                }
                em.merge(event);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void removeUser(Long idEvent, User user){
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            Event event = selectOneById(idEvent);
            if (event != null) {
                event.getUserList().remove(user);
                em.merge(event);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Event selectOneById(Long id) {
        EntityManager em = null;
        Event result = null;

        try {
            em = factory.createEntityManager();
            if (em.find(Event.class, id) != null) {
                result = em.find(Event.class, id);
            }
            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    
    public List<Event> selectAll() {
        EntityManager em = null;
        List<Event> result = null;
        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select e from Event e");
            if (!query.getResultList().isEmpty()) {
                result = query.getResultList();
            }
            return result;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteOneById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("serverMobile");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transac = em.getTransaction();
        transac.begin();

        if (em.find(Event.class, id) != null) {
            em.remove(em.find(Event.class, id));
        }
        transac.commit();
        em.close();
        emf.close();
    }

}
