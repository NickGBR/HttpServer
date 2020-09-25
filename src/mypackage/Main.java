package mypackage;


import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Main {

    public static void main(String[] args) {

        //Создаем сервер и передаем в него имя и порт.
        HttpServer server=null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/socket", new MyHttpHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server problems");
        }
    }
}
