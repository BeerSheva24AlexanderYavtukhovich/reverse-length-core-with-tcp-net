package telran.app.net;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import telran.net.TcpClient;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class Main {
    static TcpClient tcpClient;
    private static final HashSet<String> types = new HashSet<>(Arrays.asList("normal", "reverse", "length"));

    public static void main(String[] args) {
        Item[] items = {
                Item.of("start session", Main::startSession),
                Item.of("exit", Main::Exit, true)
        };
        Menu menu = new Menu("Reverse-Length TCP Client Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        tcpClient = new TcpClient(host, port);

        Menu menu = new Menu("Run Session",
                Item.of("Send request", Main::requestProcessing),
                Item.ofExit());
        menu.perform(io);
    }

    static void Exit(InputOutput io) {
        if (tcpClient != null) {
            try {
                tcpClient.close();
            } catch (IOException e) {
                io.writeLine("Error while closing client: " + e);
            }
        }
    }

    static void requestProcessing(InputOutput io) {
        String typeOptions = String.join(", ", types);
        String type = io.readStringOptions("Enter request type (" + typeOptions + ")",
                "Invalid type, please choose again", types);
        String request = io.readString("Enter string for processing");
        String response = tcpClient.sendAndReceive(type, request);
        io.writeLine("Response: " + response);
    }

}