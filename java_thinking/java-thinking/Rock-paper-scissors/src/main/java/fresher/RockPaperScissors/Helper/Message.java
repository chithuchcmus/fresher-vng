package fresher.RockPaperScissors.Helper;

import fresher.RockPaperScissors.Model.Game;
import fresher.RockPaperScissors.Model.User;
import org.springframework.stereotype.Repository;

@Repository
public class Message {
    public String wrongFormat() {
        return "Sai định dạng gửi lên";
    }

    public String wrongData() {
        return "Dữ liệu gửi lên sai";
    }

    public String userExist() {
        return "Đã tồn tại user";
    }

    public String userNotExist() {
        return "Không tồn tại user";
    }


    public String signUpSucces() {
        return "Đăng kí thành công ";
    }

    public String signInSucces() {
        return "Đăng nhập thành công";
    }

    public String signInFail() {
        return "Không tồn tại tài khoản hoặc mật khẩu ";
    }

    public String createGameSucces() {
        return "Tạo game mới thành công";
    }

    public String gameNotExist()
    {
        return "Game không tồn tại";
    }
    public String gameHavedResult()
    {
        return "Game đã có kết quả";
    }


    public String userPlayWrongGame() {
        return "User chơi nhằm game";
    }
    public String playGameSucces(Integer serverplay, Integer result) {
        return "Server choi: " + serverplay.toString() + "Ket qua: " + result.toString();
    }

    public String wrongToken() {
        return "sai token";
    }

    public String getHistorySucces(Integer idser) {
        return "Lấy lịch sử game của người dùng thành công,iduser: " + idser.toString();
    }

    public String getTopSucces() {
        return "Lấy danh sách top tỉ lệ thắng thành công";
    }

    public String succes() {
        return "succes";
    }

    public String fail() {
        return "fail";
    }

    public String request() {
        return "Thông tin request";
    }

    public String infoToken() {
        return "Info token";
    }

}
