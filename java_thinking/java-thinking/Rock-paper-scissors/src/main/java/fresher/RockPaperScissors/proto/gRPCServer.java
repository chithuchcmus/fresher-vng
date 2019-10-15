package fresher.RockPaperScissors.proto;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import fresher.RockPaperScissors.Helper.GameHelper;
import fresher.RockPaperScissors.Helper.PasswordService;
import fresher.RockPaperScissors.Helper.TokenService;
import fresher.RockPaperScissors.Model.Game;
import fresher.RockPaperScissors.Model.User;
import fresher.RockPaperScissors.Model.Turn;
import fresher.RockPaperScissors.dto.*;
import fresher.RockPaperScissors.Repository.GameRepository;
import fresher.RockPaperScissors.Repository.TurnRepository;
import fresher.RockPaperScissors.Repository.UserRepository;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Claims;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.thuc.rps.gRPC.source.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@GRpcService
public class gRPCServer extends gRPCServiceGrpc.gRPCServiceImplBase {

    private static final Logger logger =
            LoggerFactory.getLogger(gRPCServer.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TurnRepository turnRepository;

    @Autowired
    PasswordService passwordService;

    @Autowired
    TokenService tokenService;

    @Autowired
    GameHelper gameHelper;

    private Cache<Integer, Game> cacheGame = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(250)
            .build();

    @Override
    public void signUpRequest(UserRequest userRequest, StreamObserver<SignupRespone> responseObserver) {
        logger.info("Sign Up Request");
        SignupRespone signupRespone;
        String username = userRequest.getUsername();
        if (userRepository.existsUserByUsername(username)) {
            logger.info("Sign Up Request Fail");
            signupRespone = SignupRespone.newBuilder().setStatus("fail").setMessage("user exist").build();
        } else {
            logger.info("Sign Up Request Succes");
            String password = passwordService.hashPassword(userRequest.getPassword());
            User user = new User(username, password);
            userRepository.save(user);
            signupRespone = SignupRespone.newBuilder().setStatus("succes").setMessage("sign up succes").build();
        }
        responseObserver.onNext(signupRespone);
        responseObserver.onCompleted();
    }

    @Override
    public void signInRequest(UserRequest request, StreamObserver<SignInRespone> responseObserver) {
        logger.info("Sign In");
        SignInRespone signInRespone;
        String username = request.getUsername();
        String password = request.getPassword();
        User user = userRepository.findByUsername(username);
        if (user == null ||
                !passwordService.checkPassword(password, user.getHashpassword())) {
            logger.info("Sign In Fail");
            signInRespone = SignInRespone.newBuilder().setStatus("fail")
                    .setMessage("wrong username or password").build();
        } else {
            String token = tokenService.createJWT(user);
            logger.info("Sign In succes");
            signInRespone = SignInRespone.newBuilder().setMessage("login succes")
                    .setStatus("succes").setToken(token).build();
        }
        responseObserver.onNext(signInRespone);
        responseObserver.onCompleted();
    }

    @Override
    public void createGameRequest(RequestCreateGame requestCreateGame,StreamObserver<CreateGameRespone> responseObserver){
        logger.info("Create Game");
        CreateGameRespone createGameRespone;
        Claims infoUser = tokenService.decodeToken(requestCreateGame.getToken());
        if(infoUser == null ){
            logger.info("Create Game Fail");
            createGameRespone = CreateGameRespone.newBuilder().setMessage("token invalid")
                    .setStatus("fail")
                    .build();
        }
        else {
            Integer iduser = Integer.parseInt(infoUser.getId());
            if(!userRepository.existsById(iduser)){
                logger.info("Create Game Fail");
                createGameRespone = CreateGameRespone.newBuilder().setMessage("user or password invalid")
                        .setStatus("fail")
                        .build();
            }
            else
            {
                logger.info("Create Game Succes");
                Game newgame = gameRepository.save(new Game(iduser, gameHelper.isDraw));
                cacheGame.put(newgame.getIdGame(),newgame);
                createGameRespone = CreateGameRespone.newBuilder().setMessage("create game succes")
                        .setStatus("succes")
                        .setGameid(newgame.getIdGame())
                        .build();
            }
        }
        responseObserver.onNext(createGameRespone);
        responseObserver.onCompleted();
    }

    @Override
    public void  playGameRequest(PlayGameRequests playGameRequests, StreamObserver<PlayGameRespones> responseObserver){
        logger.info("Play Game Request");

        PlayGameRespones playGameRespones;
        Claims infoUser = tokenService.decodeToken(playGameRequests.getToken());
        if(infoUser == null ){
            logger.info("Play Game Request Fail");
            playGameRespones = PlayGameRespones.newBuilder().setMessage("token invalid")
                    .setStatus("fail")
                    .setUserplay(-1)
                    .setServerplay(-1)
                    .build();
        }
        else {
            Integer iduser = Integer.parseInt(infoUser.getId());
            if(!userRepository.existsById(iduser)){
                logger.info("Play Game Request Fail");
                playGameRespones = PlayGameRespones.newBuilder().setMessage("user or password invalid")
                        .setStatus("fail")
                        .setUserplay(-1)
                        .setServerplay(-1)
                        .build();
            }
            else
            {

                Game game = cacheGame.getIfPresent(playGameRequests.getGameid());
                if(game == null)
                {
                    game = gameRepository.findByIdGame(playGameRequests.getGameid());
                }
                if(game == null || !game.getIdUser().equals(iduser)
                        || !game.getResult().equals(gameHelper.isDraw) )
                {
                    logger.error("Play Game Request Fail");
                    playGameRespones = PlayGameRespones.newBuilder().setMessage("play wrong game or game haved resut")
                            .setStatus("fail")
                            .setUserplay(-1)
                            .setServerplay(-1)
                            .build();
                }else {
                    logger.info("Play Game Request Succes");
                    Integer serverplay = gameHelper.getServerPlay();
                    Turn turn = new Turn(playGameRequests.getUserplay(),
                            serverplay, game.getIdGame());
                    turnRepository.save(turn);
                    Integer result = gameHelper.getResult(playGameRequests.getUserplay(), serverplay);
                    if (result.equals(gameHelper.isWin)  || result.equals(gameHelper.isLose) ) {
                        if(result == gameHelper.isWin)
                            userRepository.updateNumberWinAndNumberPlay(iduser);
                        else {
                            userRepository.updateNumberPlay(iduser);
                        }
                        gameRepository.updateResult(result, game.getIdGame());
                        Game gameUpdate = new Game(iduser,result);
                        gameUpdate.setIdGame(game.getIdGame());
                        cacheGame.put(game.getIdGame(),gameUpdate);

                    } else {
                        userRepository.updateNumberPlay(iduser);
                    }
                    playGameRespones = PlayGameRespones.newBuilder().setMessage("play game succes")
                            .setStatus("succes")
                            .setServerplay(serverplay)
                            .setUserplay(playGameRequests.getUserplay())
                            .build();
                }
            }
        }
        responseObserver.onNext(playGameRespones);
        responseObserver.onCompleted();
    }

    @Override
    public void rankRequest(RankRequest rankRequest, StreamObserver<RankRespone> responseObserver){
        logger.info("Rank Request");
        RankRespone rankRespone = RankRespone.newBuilder()
                        .setMessage("get top succes")
                        .setStatus("succes")
                        .addAllInfoUser(getRankListUser(userRepository.getRanking()))
                        .build();
        responseObserver.onNext(rankRespone);
        responseObserver.onCompleted();
    }

    @Override
    public  void historyRequest(HistoryRequest historyRequest,  StreamObserver<HistoryRespone> responseObserver){
        logger.info("History Request");
        HistoryRespone historyRespone;
        Claims infoUser = tokenService.decodeToken(historyRequest.getToken());
        if(infoUser == null ){
            logger.info("History Request Fail");
            historyRespone = HistoryRespone.newBuilder().setMessage("token invalid")
                    .setStatus("fail")
                    .addAllGame(new ArrayList<com.thuc.rps.gRPC.source.Game>())
                    .build();
        }

        else {
            Integer iduser = Integer.parseInt(infoUser.getId());
            if(!userRepository.existsById(iduser)){
                logger.info("History Request Fail");
                historyRespone = HistoryRespone.newBuilder().setMessage("user or password invalid")
                        .setStatus("fail")
                        .addAllGame(new ArrayList<com.thuc.rps.gRPC.source.Game>())
                        .build();
            }
            else
            {
                logger.info("History Request Succes");
                List<HistoryDto> historyDtoList = userRepository.getHistory(iduser);
                historyRespone = HistoryRespone.newBuilder().setMessage("user or password invalid")
                        .setStatus("succes")
                        .addAllGame(getHistory(historyDtoList))
                        .build();
            }
        }
        responseObserver.onNext(historyRespone);
        responseObserver.onCompleted();
    }


    private List<com.thuc.rps.gRPC.source.InfoTopUser> getRankListUser(List<User> list) {
        if(list.size() > 0)
        {
            return list.stream().map(user -> com.thuc.rps.gRPC.source.InfoTopUser.newBuilder()
                    .setUsername(user.getUsername())
                    .build()).collect(Collectors.toList());
        }
        return new ArrayList<InfoTopUser>();
    }

    private List<com.thuc.rps.gRPC.source.Game> getHistory(List<HistoryDto> historyDtoList)
    {
        if(historyDtoList.size() > 0)
        {
            return historyDtoList.stream().map(list -> com.thuc.rps.gRPC.source.Game.newBuilder()
            .setUserplay(list.getUserPlay())
            .setServerplay(list.getServerPlay())
            .setResult(list.getResult()).build()).collect(Collectors.toList());
        }
        return new ArrayList<com.thuc.rps.gRPC.source.Game>();
    }
}