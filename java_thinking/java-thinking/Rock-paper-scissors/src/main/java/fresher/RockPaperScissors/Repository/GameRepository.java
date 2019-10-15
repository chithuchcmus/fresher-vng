package fresher.RockPaperScissors.Repository;

import fresher.RockPaperScissors.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {

    Game findByIdGame(Integer idGame);

    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.result = :result WHERE g.idGame = :idgame")
    void updateResult(@Param("result") Integer result,@Param("idgame") Integer idgame);

    @Query("SELECT g.result FROM Game g WHERE g.idGame = :idgame")
    Integer getResultOfGame(@Param("idgame") Integer idgame);

    @Query("SELECT g FROM Game g WHERE g.idUser = :iduser")
    List<Game> getListGameOfUser(@Param("iduser") Integer iduser);

}
