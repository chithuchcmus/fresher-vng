package fresher.RockPaperScissors.Controller;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import fresher.RockPaperScissors.Helper.*;

import fresher.RockPaperScissors.Model.Game;
import fresher.RockPaperScissors.Model.User;
import fresher.RockPaperScissors.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
@Service
public class UserController<T> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    Message message;

    @Autowired
    PasswordService passwordService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private Cache<String, User> cacheUser = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(200)
            .build();


    @PostMapping("/register")
    public ResponseEntity<?> signUpController(@RequestBody userForm userForm) {
        logger.info("register Request");
        String username = userForm.getUsername();
        String password = userForm.getPassword();
        if (username.length() == 0 || password.length() == 0 ||
                username.contains(";") || password.contains(";")) {
            logger.error(message.wrongData());
            return createBadRespone(message.wrongFormat(), message.fail());
        }
        User userExist = userRepository.findByUsername(username);
        if (userExist != null) {
            logger.error(message.userExist());
            return createBadRespone(message.userExist(), message.fail());
        }
        User newUser = userRepository.save(new User(username, passwordService.hashPassword(password)));
        cacheUser.put(newUser.getUsername(),newUser);
        return createSuccesRespone(message.signUpSucces(), message.succes(), null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signInController(@RequestBody userForm userForm) {
        logger.info("login  Request");
        if (userForm == null) {
            logger.error(message.wrongFormat());
            return createBadRespone(message.wrongFormat(), message.fail());
        }
        String username = userForm.getUsername();
        String password = userForm.getPassword();
        if (username.length() == 0 || password.length() == 0 ){
            return createBadRespone(message.wrongData(), message.fail());
        }
        User userExist = cacheUser.getIfPresent(username);
        if(userExist == null)
        {
            userExist = userRepository.findByUsername(username);
        }
        if (userExist != null) {
            if (passwordService.checkPassword(password, userExist.getHashpassword())) {
                String token = tokenService.createJWT(userExist);
                return createSuccesRespone(message.signInSucces(), message.succes(), token);
            }
        }
        logger.error(message.signInFail());
        return createBadRespone(message.wrongData(), message.fail());
    }



    @GetMapping("/users/top")
    @ResponseBody
    public ResponseEntity<?> getTopUserWinRate(@RequestParam Integer top) {
        logger.info("Get Top ranking request");
        if (top == null || top <= 0 || top >= 1000) {
            logger.error(message.wrongData());
            return createBadRespone(message.wrongData(),message.fail());
        } else {
            List<User> userlist = userRepository.getTopUserWinRate(PageRequest.of(0, top)).getContent();
            return createSuccesRespone(message.getTopSucces(),message.succes(),userlist);
        }
    }

    private ResponseEntity<?> createBadRespone(String message,String status)
    {
        return new ResponseEntity<Object>(new DataRespones(message,status,null),HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<?> createSuccesRespone(String message,String status, Object data)
    {
        return new ResponseEntity<Object>(new DataRespones(message,status,data),HttpStatus.OK);
    }

}
