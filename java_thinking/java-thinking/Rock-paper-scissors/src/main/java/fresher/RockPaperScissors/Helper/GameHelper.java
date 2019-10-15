package fresher.RockPaperScissors.Helper;

import org.springframework.stereotype.Repository;

@Repository
public class GameHelper {
    public final int isWin = 1;
    public  final int isLose = 0;
    public final  int isDraw = 2;
    public  final int bao =1;
    public  final int bua =2;
    public  final int keo = 3;

    public int getResult(Integer userPlay, Integer serverPlay)
    {
        switch (userPlay)
        {
            case bao:
                if(serverPlay == bao)
                    return isDraw;
                if(serverPlay == bua)
                    return isWin;
                if(serverPlay == keo)
                    return isLose;
            case bua:
                if(serverPlay == bao)
                    return isLose;
                if(serverPlay == bua)
                    return isDraw;
                if(serverPlay == keo)
                    return isWin;
            case keo:
                if(serverPlay == bao)
                    return isWin;
                if(serverPlay == bua)
                    return isLose;
                if(serverPlay == keo)
                    return isDraw;
        }
        return isDraw;
    }
    public  Integer getServerPlay()
    {
        return (int)(Math.random() * 3 +1);
    }
}
