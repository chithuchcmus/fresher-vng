package fresher.RockPaperScissors.Repository;

import fresher.RockPaperScissors.Model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Integer> {
    @Query("SELECT t FROM Turn t WHERE t.idGame = :idgame")
    List<Turn> getListTurnOfGame(@Param("idgame") Integer idgame);
}
