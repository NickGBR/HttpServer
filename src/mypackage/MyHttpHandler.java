package mypackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;



public class MyHttpHandler implements HttpHandler {

    private String response;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //Если тип запроса "GET" то передаем список файлов
        if(exchange.getRequestMethod().equals("GET")) {

            //Получаем спимок файлов в директории
            //Имя текущей директории
            String folderpath = "mypackage";
            File dir = new File(getClass().getClassLoader().getResource(folderpath).getFile());
            File[] files = dir.listFiles();
            if(files!=null) {
                response = fileToString(files);
                handleResponse(exchange, fileToString(files));
            }
            System.out.println(exchange.getRequestMethod());
        }
        else{
            String error404 = "<h1>404 Not Found</h1> No context found for request";
            handleResponse(exchange, error404);
        }
        System.out.println(exchange.getRequestMethod());
    }

    //Преобразование имен файлов в строковую константу
    private String fileToString(File[] files){
        response = "";
        for(File file : files){
            response = response + file.getName();
            response = response +"\n";
        }
        return response;
    }

    private void handleResponse(HttpExchange exchange, String response) throws IOException {

        OutputStream outputStream = exchange.getResponseBody();

        exchange.sendResponseHeaders(200,response.length());

        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
