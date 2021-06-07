package server;

import database.CommandForServer;
import database.MessageForClient;
import database.SpaceMarineCollection;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int SERVICE_PORT = 5001;

    public static void main(String[] args) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }



        String jdbcURL = "jdbc:postgresql://localhost:5000/postgres";
        Connection connection;
        String password;
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a password to DataBase");
            password = scanner.nextLine();
            connection = DriverManager.getConnection(jdbcURL,"postgres", password);
            System.out.println("Connection to database is done");
        }catch (SQLException e) {
            System.out.println("Something was wrong with connection to database. Exiting...");
            System.exit(-1);
            connection = null;
            password = null;
        }


        Postgre postgre = new Postgre(connection, "postgres", password);
        SpaceMarineCollection collection = new SpaceMarineCollection(postgre);
        postgre.fillCollection(collection);

        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);


        DatagramSocket socket = new DatagramSocket(SERVICE_PORT);


        Exchanger<CommandForServer> exchangerCommand = new Exchanger<>();
        Exchanger<MessageForClient> exchangerMessage = new Exchanger<>();
        fixedPool.submit(new ServerReading(socket, collection, exchangerCommand));
        cachedPool.submit(new ServerProcessing(socket, collection, exchangerCommand, exchangerMessage, postgre));
        forkJoinPool.invoke(new ServerWriting(socket, exchangerMessage));
    }
}
