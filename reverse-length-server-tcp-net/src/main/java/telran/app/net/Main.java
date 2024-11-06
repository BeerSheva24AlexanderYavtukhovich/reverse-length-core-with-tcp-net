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
        String requestData = request.requestData();
        ResponseCode code;
        String data;

        switch (request.requestType()) {
            case "reverse" -> {
                code = ResponseCode.OK;
                data = new StringBuilder(requestData).reverse().toString();
            }
            case "length" -> {
                code = ResponseCode.OK;
                data = String.valueOf(requestData.length());
            }
            case "normal" -> {
                code = ResponseCode.OK;
                data = requestData;
            }
            default -> {
                code = ResponseCode.WRONG_TYPE;
                data = "Unsupported request type";
            }
        }

        return new Response(code, data);
    };

    public static void main(String[] args) {
        tcpServer = new TcpServer(protocol, SERVER_PORT);
        tcpServer.run();
    }
}