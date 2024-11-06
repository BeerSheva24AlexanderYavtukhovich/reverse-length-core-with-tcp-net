package telran.app.net;

import telran.net.Protocol;
import telran.net.TcpServer;

public class Main {
    private static final int SERVER_PORT = 5011;
    private static TcpServer tcpServer;
    private static final RequestFactory requestFactory = new RequestFactory();
    private static final Protocol protocol = request -> requestFactory.getResponse(request);

    public static void main(String[] args) {
        tcpServer = new TcpServer(protocol, SERVER_PORT);
        tcpServer.run();
    }
}