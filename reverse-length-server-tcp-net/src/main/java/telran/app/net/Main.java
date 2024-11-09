package telran.app.net;

import telran.net.TcpServer;

public class Main {
    static final int PORT = 5011;

    public static void main(String[] args) {
        TcpServer server = new TcpServer(new ReverseLengthProtocol(), PORT);
        server.run();
    }
}