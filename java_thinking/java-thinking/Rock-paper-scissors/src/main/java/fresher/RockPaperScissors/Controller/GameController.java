package fresher.RockPaperScissors.Controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import fresher.RockPaperScissors.Helper.*;
import fresher.RockPaperScissors.Model.Game;
import fresher.RockPaperScissors.Model.Turn;
import fresher.RockPaperScissors.Repository.*;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
@Service
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;


    @Autowired
    Message message;

    @Autowired
    TurnRepository turnRepository;

    @Autowired
    GameHelper gameHelper;

    private Logger logger = LoggerFactory.getLogger(GameController.class);
    private Cache<Integer, Game> cacheGame = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(300)
            .build();


    @PostMapping("/games")
    public ResponseEntity<?> CreateGame(@RequestHeader(value = "token") String token) {
        logger.info("Create game");
        if (token.length() == 0) {
            logger.error(message.wrongData());
            return createBadRespone(message.wrongData(),message.fail());
        }
        Integer userId = checkCorrectToken(token);
        if (userId <= 0) {
            logger.error(message.userExist());
            return createBadRespone(message.userNotExist(),message.fail());
        }
        Game game = new Game(userId, gameHelper.isDraw);
        Game newgame = gameRepository.save(game);
        cacheGame.put(newgame.getIdGame(),game);
        return createSuccesRespone(message.createGameSucces(),message.succes(),newgame);
    }

    @PostMapping("/games/{idGame}")
    public ResponseEntity<?> PlayGame(@PathVariable Integer idGame, @RequestParam Integer userplay, @RequestHeader(value = "token") String token) {
        logger.info("Play game");
        if (idGame == null || token == null ||
                idGame <= 0 || userplay > 3 || userplay <= 0) {
            logger.error(message.wrongFormat());
            return createBadRespone(message.wrongFormat(),message.fail());
        }

        Integer userId = checkCorrectToken(token);
        if (userId <= 0) {
            logger.error(message.userExist());
            return createBadRespone(message.wrongToken(),message.fail());
        }

        Integer resultOfGame = -1;
        Integer idUserOfGame = -1;
        Game gameOfUser = cacheGame.getIfPresent(idGame);
        if(gameOfUser != null)
        {
            resultOfGame = gameOfUser.getResult();
            idUserOfGame = gameOfUser.getIdUser();
        }
        else {
            try
            {
                Game gameOfDB = gameRepository.findByIdGame(idGame);
                if(gameOfDB != null)
                {
                    System.out.println("co vao ko");
                    resultOfGame = gameOfDB.getResult();
                    idUserOfGame = gameOfDB.getIdUser();
                }
            }
            catch (Exception e)
            {
                return createBadRespone(message.userPlayWrongGame(),message.fail());
            }

        }
        if ( !idUserOfGame.equals(userId)) {
            logger.error(message.userPlayWrongGame());
            logger.error(userId.toString());
            logger.error(idUserOfGame.toString());
            return createBadRespone(message.userPlayWrongGame(),message.fail());
        }
        if (!resultOfGame.equals(gameHelper.isDraw)) {
            logger.error(message.gameHavedResult());
            return createSuccesRespone(message.gameHavedResult(),message.fail(),null);
        }
        Integer serverPlay = gameHelper.getServerPlay();
        Turn newTurn = new Turn(userplay, serverPlay, idGame);
        turnRepository.save(newTurn);
        Integer result = gameHelper.getResult(userplay, serverPlay);
        if (!result.equals(gameHelper.isDraw)) {
            if(result.equals(gameHelper.isWin))
            {
                userRepository.updateNumberWinAndNumberPlay(userId);
            }else
            {
                userRepository.updateNumberPlay(userId);
            }
            gameRepository.updateResult(result,idGame);
            cacheGame.put(idGame,new Game(userId,result));
        } else {
            userRepository.updateNumberPlay(userId);
        }
        return  createSuccesRespone(message.playGameSucces(serverPlay,userplay),message.succes(),newTurn);
    }

    @GetMapping("/users/games/turns")
    public ResponseEntity<?> getHistoryGameAndTurn(@RequestHeader(value = "token") String token) {
        logger.info("History request");

        if (token.length() == 0) {
            logger.error(message.wrongToken());
            return createBadRespone(message.wrongToken(),message.fail());
        } else {
            Integer userId = checkCorrectToken(token);
            if (userId <= 0) {
                logger.error(message.wrongToken());
                return createBadRespone(message.wrongToken(),message.fail());
            }
            return createSuccesRespone(message.getHistorySucces(userId)
                    ,message.succes(),userRepository.getHistory(userId));

        }
    }

    private Integer checkCorrectToken(String token)
    {
        Claims infoUser = tokenService.decodeToken(token);
        if (infoUser == null) {
            return -1;
        }
        Integer userId = Integer.parseInt(infoUser.getId());
        String userName = infoUser.get("username").toString();
        if (userRepository.findByUsername(userName) == null) {
            return -1;
        }
        return userId;
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


