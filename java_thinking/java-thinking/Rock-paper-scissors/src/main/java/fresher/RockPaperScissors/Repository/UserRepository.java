package fresher.RockPaperScissors.Repository;

import fresher.RockPaperScissors.Model.User;
import fresher.RockPaperScissors.dto.HistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);
    Boolean existsUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.numberTurn = u.numberTurn +1, u.numberWin = u.numberWin +1" +
            "WHERE u.userId = :iduser")
    void updateNumberWinAndNumberPlay(@Param("iduser") Integer iduser);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.numberTurn = u.numberTurn +1"
            + "WHERE u.userId = :iduser")
    void updateNumberPlay(@Param("iduser") Integer iduser);

    @Modifying
    @Query(value = "SELECT  new fresher.RockPaperScissors.dto.HistoryDto(u.username,g.result,t.userPlay,t.serverPlay) from " +
            "User u JOIN  Game g on u.userId = g.idUser join Turn t on t.idGame = g.idGame " +
            "where u.userId =:iduser")
    List<HistoryDto> getHistory(@Param("iduser") Integer iduser);

    @Query("select u.username from User  u " +
            "order by u.numberWin/u.numberTurn desc")
    Page<User> getTopUserWinRate(Pageable pageable);


    @Query(value = "SELECT * FROM user u order by u.percent_win desc LIMIT 100", nativeQuery = true)
    List<User> getRanking();
}
