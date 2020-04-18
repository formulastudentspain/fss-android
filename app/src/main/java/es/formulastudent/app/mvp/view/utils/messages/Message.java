package es.formulastudent.app.mvp.view.utils.messages;

public class Message {

    private Integer stringID;
    private Object[] args;

    public Message(Integer stringID, Object...args) {
        this.stringID = stringID;
        this.args = args;
    }

    public Integer getStringID() {
        return stringID;
    }

    public Object[] getArgs() {
        return args;
    }
}
