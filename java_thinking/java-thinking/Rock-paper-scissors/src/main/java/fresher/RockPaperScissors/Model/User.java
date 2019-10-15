package fresher.RockPaperScissors.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
public class User implements Serializable  {
    @Column(name = "user_name")
    private String username;
    @Column(name = "hash_password")
    private String hashpassword;

    @Column(name = "number_win")
    private Integer numberWin;

    @Column(name = "number_turn")
    private Integer numberTurn;


    @Id
    @Column(name ="id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;


    public User()
    {
    }

    public User(String username, String hashpassword)
    {
        this.username = username;
        this.hashpassword = hashpassword;
        this.numberTurn=0;
        this.numberWin=0;
    }

    public Integer getNumberWin() {
        return numberWin;
    }

    public void setNumberWin(Integer numberWin) {
        this.numberWin = numberWin;
    }

    public Integer getNumberTurn() {
        return numberTurn;
    }

    public void setNumberTurn(Integer numberTurn) {
        this.numberTurn = numberTurn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHashpassword() {
        return hashpassword;
    }

    public void setHashpassword(String hashpassword) {
        this.hashpassword = hashpassword;
    }
}
