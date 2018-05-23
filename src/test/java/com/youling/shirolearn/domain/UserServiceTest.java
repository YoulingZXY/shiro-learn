package com.youling.shirolearn.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByUsername() {
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("lalala");
        user.setPassword("000000");
        userService.saveUser(user);
    }
}