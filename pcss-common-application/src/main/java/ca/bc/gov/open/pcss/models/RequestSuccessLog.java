package ca.bc.gov.open.pcss.models;

import lombok.Data;

@Data
public class RequestSuccessLog {
    private String type;
    private String endpoint;
    private Object request;

    public RequestSuccessLog(String type, String endpoint) {
        this.type = type;
        this.endpoint = endpoint;
    }

    public RequestSuccessLog(String type, String endpoint, Object request) {
        this.type = type;
        this.endpoint = endpoint;
        this.request = request;
    }
}
