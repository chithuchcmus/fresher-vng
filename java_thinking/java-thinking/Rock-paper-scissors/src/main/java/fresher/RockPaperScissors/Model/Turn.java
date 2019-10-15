package fresher.RockPaperScissors.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table ( name= "turn")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Turn implements Serializable {
    @Column(name = "user_play")
    private Integer userPlay;
    @Column(name = "server_play")
    private Integer serverPlay;
    @Column(name = "id_game")
    private Integer idGame;

    @Id
    @Column(name ="id_turn")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurn;

    public Turn()
    {

    }

    public Turn(Integer userPlay, Integer serverPlay, Integer idGame) {
        this.userPlay = userPlay;
        this.serverPlay = serverPlay;
        this.idGame = idGame;
    }

    public Integer getUserPlay() {
        return userPlay;
    }

    public void setUserPlay(Integer userPlay) {
        this.userPlay = userPlay;
    }

    public Integer getServerPlay() {
        return serverPlay;
    }

    public void setServerPlay(Integer serverPlay) {
        this.serverPlay = serverPlay;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getIdTurn() {
        return idTurn;
    }

    public void setIdTurn(Integer idTurn) {
        this.idTurn = idTurn;
    }
}
