import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1050);
		Socket socket = server.accept();

            try {
                Scanner sc = new Scanner(socket.getInputStream());
                PrintStream ps = new PrintStream(socket.getOutputStream());

                // Receive amount
                String inputLine = sc.nextLine();
                String[] parts = inputLine.split(",");
                double amount = Double.parseDouble(parts[0]);
                String choice = parts[1].trim();

                double convertedAmount;
                switch (choice) {
                    case "a":
                        convertedAmount = toUSD(amount);
                        break;
                    case "b":
                        convertedAmount = toUAE(amount);
                        break;
                    case "c":
                        convertedAmount = toGBP(amount);
                        break;
                    case "d":
                        convertedAmount = toJPY(amount);
                        break;
                    default:
                        convertedAmount = 69;
                        break;
                }
                // Send to client
                ps.println(convertedAmount);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static double toUSD(double num) {
            return num * 0.018;
        }

        public static double toUAE(double num) {
            return num * 0.065;
        }

        public static double toGBP(double num) {
            return num * 0.014;
        }

        public static double toJPY(double num) {
            return num * 2.68;
        }
    
}
