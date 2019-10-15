package fresher.RockPaperScissors.Helper;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordService {
    private final int workload = 5;

    public  String hashPassword(String password) {
        return(BCrypt.hashpw(password, BCrypt.gensalt(workload)));
    }
    public  boolean checkPassword(String password, String hashPassword) {
        if( hashPassword == null)
            return false;
        return BCrypt.checkpw(password, hashPassword);
    }
}
