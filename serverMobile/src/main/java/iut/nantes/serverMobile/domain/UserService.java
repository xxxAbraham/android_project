package iut.nantes.serverMobile.domain;

import iut.nantes.serverMobile.api.entities.User;
import iut.nantes.serverMobile.infra.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Djurdjevic Sacha
 */
public class UserService {
    
    
    @Autowired
    UserDao userDao;
    
    public List<User> getAll(){
        return userDao.selectAll();
    }
    
    public User get(Long id){
        return userDao.selectOneById(id);
    }
    
    public void addOne(User user){
        userDao.addOne(user);
    }
    
    public void updateSpeudo(Long id, String newSpeudo){
        userDao.updateUserSpeudo(id, newSpeudo);
    }
    
    public void updatePassword(Long id, String password){
        userDao.updateUserPassword(id, password);
    }
    
    public void updateEmail(Long id, String email){
        userDao.updateUserEmail(id, email);
    }
    
    public void delete(Long id){
        userDao.deleteOneById(id);
    }
    
    public boolean validUser(User user){
        boolean result = true;
        
        //if(user.)
        
        return result;
    }
            
}
