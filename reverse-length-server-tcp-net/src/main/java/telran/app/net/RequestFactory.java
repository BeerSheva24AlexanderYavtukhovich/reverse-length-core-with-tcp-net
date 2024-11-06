package telran.app.net;

import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class RequestFactory {

    public Response reverse(String data) {
        String reversedData = new StringBuilder(data).reverse().toString();
        return getOkResponse(reversedData);
    }

    public Response length(String data) {
        String lengthData = String.valueOf(data.length());
        return getOkResponse(lengthData);
    }

    public Response normal(String data) {
        return getOkResponse(data);
    }

    public Response getResponse(Request request) {
        String data = request.requestData();
        return switch (request.requestType()) {
            case "reverse" -> reverse(data);
            case "length" -> length(data);
            case "normal" -> normal(data);
            default -> new Response(ResponseCode.WRONG_TYPE, "Unsupported request type");
        };
    }

    private Response getOkResponse(String data) {
        return new Response(ResponseCode.OK, data);
    }
}