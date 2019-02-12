package iut.nantes.serverMobile.entitiesTest;

import iut.nantes.serverMobile.infra.UserDao;
import iut.nantes.serverMobile.api.entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Djurdjevic Sacha
 */
public class UserTests {

    private static UserDao userDao = new UserDao();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        User user = new User("Elymne", "sdjur@gmail.com", "password");
        User user2 = new User("DoomGuy", "", "bfpassword");
        User user3 = new User("timotémdr", "mdr@gmail.com", "mdr");
        Long id = new Long(1);
        String speudo = "NOUVEAUSPEUDO";
        String email = "NOUVEAUEMAIL";
        String password = "NOUVEAUPASSWORD";

        List<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user3);

        userDao.init();

        System.out.println("--- Début des tests ---");
        System.out.println("Test 01 : Insertion d'un user");
        test01_Insertion(user);
        System.out.println("Test 02 : Selection de user précédement créé");
        test02_SelectionById(id);
        System.out.println("Test 03 : Update du nom et prénom du user");
        test03_UpdateNameById(id, speudo);
        System.out.println("Test 04 : Update de l'email du user");
        test04_UpdateEmailById(id, email);
        System.out.println("Test 05 : Update du mot de passe du user");
        test05_updatePasswordById(id, password);
        System.out.println("Test 06 : Ajoute une liste d'users");
        test06_addMany(userList);
        System.out.println("Test 07 : Selectionne tous les users");
        test07_selectAll();
        System.out.println("Test 08 : Suppression de l'utilisateur 1");
        test08_DeleteById(id);
        System.out.println("--- Fin des tests ---");

        clearAll();
        userDao.close();

    }

    private static void test01_Insertion(User user) {
        userDao.addOne(user);
    }

    private static void test02_SelectionById(Long id) {
        System.out.println(userDao.selectOneById(id).toString());
    }

    private static void test03_UpdateNameById(Long id, String speudo) {
        userDao.updateUserSpeudo(id, speudo);
        System.out.println(userDao.selectOneById(id).toString());
    }

    private static void test04_UpdateEmailById(Long id, String email) {
        userDao.updateUserEmail(id, email);
        System.out.println(userDao.selectOneById(id).toString());
    }

    private static void test05_updatePasswordById(Long id, String password) {
        userDao.updateUserPassword(id, password);
        System.out.println(userDao.selectOneById(id).toString());
    }

    private static void test06_addMany(List<User> userList) {
        userDao.addMany(userList);
    }

    private static void test07_selectAll() {
        for (User user : userDao.selectAll()) {
            System.out.println(user.toString());
        }
    }

    private static void test08_DeleteById(Long id) {
        userDao.deleteOneById(id);
        for (User user : userDao.selectAll()) {
            System.out.println(user.toString());
        }
    }

    private static void clearAll() {
        userDao.deleteOneById(new Long(2));
        userDao.deleteOneById(new Long(3));
    }

}
