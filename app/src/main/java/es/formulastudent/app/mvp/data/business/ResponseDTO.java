package es.formulastudent.app.mvp.data.business;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {

    private Object data;
    private List<String> errors = new ArrayList<>();
    private List<String> info = new ArrayList<>();

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
