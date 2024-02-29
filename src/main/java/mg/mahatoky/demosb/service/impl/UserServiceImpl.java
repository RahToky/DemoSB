package mg.mahatoky.demosb.service.impl;

import mg.mahatoky.demosb.model.entity.User;
import mg.mahatoky.demosb.reporistory.UserRepository;
import mg.mahatoky.demosb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mtk_ext
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
