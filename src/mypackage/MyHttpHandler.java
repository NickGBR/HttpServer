package mypackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MyHttpHandler implements HttpHandler {

    String response = "";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //Имя текущей директории
        String FOLDERPATH = "mypackage";

        String response;

        //Получаем спимок файлов в директории
        File dir = new File(getClass().getClassLoader().getResource(FOLDERPATH).getFile());
        File[] files = dir.listFiles();

        //Если тип запроса "GET" то передаем список файлов
        if(exchange.getRequestMethod().equals("GET")) {
            response = fileToString(files);
            handleResponse(exchange,response);
        }
        System.out.println(exchange.getRequestMethod());
    }

    /**Преобразование имен файлов в строковую константу
     *
     * @param files
     * @return
     */
    private String fileToString(File[] files){
        response = "";
        for(File file : files){
            response = response+file.getName();
            response = response +"\n";
        }
        return response;
    }

    /** Метод принимает строку, и передает ее в браузер.
     *
     * @param exchange
     * @param response
     * @throws IOException
     */
    private void handleResponse(HttpExchange exchange, String response) throws IOException {

        OutputStream outputStream = exchange.getResponseBody();

        exchange.sendResponseHeaders(200,response.length());

        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
