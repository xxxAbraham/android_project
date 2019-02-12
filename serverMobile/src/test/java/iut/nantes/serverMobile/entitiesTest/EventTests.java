package iut.nantes.serverMobile.entitiesTest;

import iut.nantes.serverMobile.infra.EventDao;
import iut.nantes.serverMobile.infra.UserDao;
import iut.nantes.serverMobile.api.entities.Event;
import iut.nantes.serverMobile.api.entities.User;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Djurdjevic Sacha
 */
public class EventTests {

    private static EventDao eventDao = new EventDao();
    private static UserDao userDao = new UserDao();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
            Utilisateur créateur de l'événement
         */
        User user = new User("Elymne", "sdjur@gmail.com", "password");

        /*
            Utilisateurs jouant le rôle d'invité
         */
        User userInvite = new User("Svenne", "shfftfff", "grosPasswordkkkk");
        User userInvite2 = new User("Cyneila", "ssdddhh@fff", "grosPasswordmmmm");
        User userInvite3 = new User("Vultrad", "sshfffffffh@fff", "grosPasswordppppp");

        /*
            Evénement créé par l'utilisateur principal
         */
        LocalDate localDate = LocalDate.of(2018, Month.DECEMBER, 31);
        Event event = new Event("Grosse Race", localDate, user);

        /*
            Jeu de données
         */
        Long id = new Long(1);

        /*
            Tests
         */
        userDao.init();
        eventDao.init();

        user = addUserAndCallIt(user,  new Long(1));
        userInvite = addUserAndCallIt(userInvite, new Long(2));
        userInvite2 = addUserAndCallIt(userInvite2,  new Long(3));
        userInvite3 = addUserAndCallIt(userInvite3,  new Long(4));

        System.out.println("--- Début des tests ---");
        System.out.println("Test 01 : Insertion d'un user");
        test01_Insertion(event);
        System.out.println("Test 02 : Selection du user");
        test02_Selection(id);
        System.out.println("Test 03 : Ajout d'un invité");
        test03_addInvite(id, userInvite);
        test03_addInvite(id, userInvite2);
        test03_addInvite(id, userInvite3);
        System.out.println("Test 04 : Supprimer un invité");
        test04_removeInvite(id, userInvite);
        System.out.println("Test 05 : Affichage de tous les invités");
        test05_getAllInvite(id);
        System.out.println("--- Fin des tests ---");

        eventDao.close();
        userDao.close();
    }

    public static void test01_Insertion(Event event) {
        eventDao.addOne(event);
    }

    public static void test02_Selection(Long id) {
        System.out.println(eventDao.selectOneById(id).toString());
    }

    public static void test03_addInvite(Long id, User user) {
        eventDao.addUser(id, user);
    }
    
    public static void test04_removeInvite(Long id, User user) {
        eventDao.removeUser(id, user);
    }

    public static void test05_getAllInvite(Long id) {
        for (User user : eventDao.selectOneById(id).getUserList()) {
            System.out.println(user.toString());
        }
    }

    public static User addUserAndCallIt(User user, Long id) {
        userDao.addOne(user);
        return userDao.selectOneById(id);
    }
}
