package Utils;

public class HibernateResult {
    private Object response;
    private String msg;

    public HibernateResult() {
        this.msg = "";
        this.response = new Object();
    }

    public HibernateResult(Object response) {
        this.msg = "";
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
