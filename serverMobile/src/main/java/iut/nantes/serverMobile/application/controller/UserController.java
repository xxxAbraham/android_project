package iut.nantes.serverMobile.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import iut.nantes.serverMobile.api.entities.User;
import iut.nantes.serverMobile.domain.UserService;
import iut.nantes.serverMobile.infra.UserDao;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Djurdjevic Sacha
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    private UserDao userDao = new UserDao();

    @Autowired
    UserService userService;

    @GetMapping("/")
    @ResponseBody
    public List<User> getAllEvents() {
        try{
            userDao.init();
            return userDao.selectAll();
        }finally{
            userDao.close();
        }
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public User getEvent(@PathVariable("id") String id){
        try{
            userDao.init();
            return userDao.selectOneById(Long.valueOf(id));
        }finally{
            userDao.close();
        }
    }
    
    @PostMapping("/add")
    @ResponseBody
    public void addUser(@RequestBody String payload) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(payload, User.class);
        userDao.addOne(user);
    }

}
