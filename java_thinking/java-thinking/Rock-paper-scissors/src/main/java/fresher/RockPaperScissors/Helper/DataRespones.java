package fresher.RockPaperScissors.Helper;

import java.util.stream.Stream;

public class DataRespones {
    private String message;
    private String status;
    private Object data;
    public DataRespones(String message,String status, Object data) {
        this.message = message;
        this.status=status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataRespones() {
        this.data = null;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
