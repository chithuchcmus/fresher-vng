package fresher.RockPaperScissors.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDto {
    private String userName;
    private Integer result;
    private Integer userPlay;
    private Integer serverPlay;

    public HistoryDto(String userName, Integer result, Integer userPlay, Integer serverPlay) {
        this.userName = userName;
        this.result = result;
        this.userPlay = userPlay;
        this.serverPlay = serverPlay;
    }

    public Integer getServerPlay() {
        return serverPlay;
    }

    public Integer getResult() {
        return result;
    }

    public Integer getUserPlay() {
        return userPlay;
    }
}
