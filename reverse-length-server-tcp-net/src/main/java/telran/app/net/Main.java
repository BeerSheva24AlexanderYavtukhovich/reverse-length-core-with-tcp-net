package telran.app.net;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;
import telran.net.TcpServer;

public class Main {
    private static int SERVER_PORT = 5011;
    private static TcpServer tcpServer;
    private static final Protocol protocol = (Request request) -> {
        record ResponseInfo(ResponseCode code, String data) {
        }

        String requestData = request.requestData();

        ResponseInfo responseInfo = switch (request.requestType()) {
            case "reverse" -> new ResponseInfo(ResponseCode.OK, new StringBuilder(requestData).reverse().toString());
            case "length" -> new ResponseInfo(ResponseCode.OK, String.valueOf(requestData.length()));
            case "normal" -> new ResponseInfo(ResponseCode.OK, requestData);
            default -> new ResponseInfo(ResponseCode.WRONG_TYPE, "Unsupported request type");
        };

        return new Response(responseInfo.code(), responseInfo.data());
    };

    public static void main(String[] args) {
        tcpServer = new TcpServer(protocol, SERVER_PORT);
        tcpServer.run();
    }
}