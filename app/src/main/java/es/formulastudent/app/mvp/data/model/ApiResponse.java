package es.formulastudent.app.mvp.data.model;

import java.util.List;


public class ApiResponse {

    private List<User> results = null;
    private ApiInfo info;

    public ApiResponse() {
    }

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public ApiInfo getInfo() {
        return info;
    }

    public void setInfo(ApiInfo info) {
        this.info = info;
    }

}
