import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final int socket;
    private ServerSocket serverSocket = null;
    private List<PrintWriter> allUserWriters = new ArrayList<>();

    public Server() {
        this(6969);
    }

    public Server(int socket) {
        this.socket = socket;
        try {
            serverSocket = new ServerSocket(socket);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void run() {
        while(true) {
            try {
                Socket newSocket = serverSocket.accept();
                ClientThread client = new ClientThread(newSocket, allUserWriters);
                Thread t = new Thread(client);
                t.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                break;
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void broadcast(String message) {
        final String line = "SERVER: " + message;
        for (PrintWriter w : allUserWriters) {
            w.println(line);
        }
    }
}
