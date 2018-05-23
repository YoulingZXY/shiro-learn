package com.youling.shirolearn.domain;

import com.youling.shirolearn.utils.GetSaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }


    void saveUser(User user){
        String oldPassword = user.getPassword();
        //生成4位随机码
        String salt = GetSaltUtil.generateSalt();
        user.setSalt(salt);
        Md5Hash md5Hash=new Md5Hash(oldPassword,salt,1024/*散列次数*/);
        String password = md5Hash.toHex();
        user.setPassword(password);
        userRepository.save(user);
    }
}
