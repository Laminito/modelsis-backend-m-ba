package sn.modelsisbackendmba.response;


import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    public static CustomResponse createCustomResponse(String status, int statusCode, String message, Object data) {
        return new CustomResponse(status, statusCode, message, data);
    }
}