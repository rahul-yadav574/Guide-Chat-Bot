package in.nfclocations;

import java.io.Serializable;

/**
 * Created by Brekkishhh on 23-08-2016.
 */
public class ChatMessage implements Serializable {

    private String message;
    private String time;
    private String isSended;

    public ChatMessage(String message, String time,String isSended) {
        this.message = message;
        this.time = time;
        this.isSended = isSended;
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String isSended() {
        return this.isSended;
    }

    public void setSended(String sended) {
        this.isSended = sended;
    }
}
