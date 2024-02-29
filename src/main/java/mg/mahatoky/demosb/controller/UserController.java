package mg.mahatoky.demosb.controller;

import mg.mahatoky.demosb.model.entity.User;
import mg.mahatoky.demosb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mtk_ext
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

}
