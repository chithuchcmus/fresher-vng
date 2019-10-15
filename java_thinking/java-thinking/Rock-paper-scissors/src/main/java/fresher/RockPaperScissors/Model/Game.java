package fresher.RockPaperScissors.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "game")

public class Game implements Serializable {
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "result")
    private Integer result;

    @Id
    @Column(name = "id_game")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGame;


    public Game()
    {

    }
    public Game(Integer idUser,Integer result)
    {
        this.idUser = idUser;
        this.result = result;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }


    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
