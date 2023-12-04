import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class GameServer implements Runnable 
{
    private ServerSocket serverSock;
    private List<GameClient> connectedClients;
    private GameGUI gameGUI = new GameGUI();

    public GameServer(int serverPort) 
    {
        try 
        {
            serverSock = new ServerSocket(serverPort);
            connectedClients = new ArrayList<>();
        } 
        catch (IOException e) 
        {
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            while (true) 
            {
                Socket playerSocket = serverSock.accept();
                GameClient client = new GameClient("localhost", 1234, gameGUI);
    
                // Add the client to the list of connected clients
                connectedClients.add(client);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
    }
    

    public static void main(String[] args) 
    {
        int serverPort = 1234; 
        GameServer server = new GameServer(serverPort);

        // Start the server in a separate thread
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}