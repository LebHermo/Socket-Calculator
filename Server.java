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
                double amount;
                try {
                    amount = sc.nextDouble();
                    sc.nextLine();
                    if (amount <= 0) {
                        ps.println("Invalid amount. Please enter a positive number.");
                        return;
                    }
                } catch (Exception e) {
                    ps.println("Invalid amount format. Please enter a valid number.");
                    return;
                }

                // Receive choice
                String choice = sc.nextLine().trim();
                if (!isValidChoice(choice)) {
                    ps.println("Invalid choice. Please enter a valid option (a, b, c, d).");
                }

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
                        convertedAmount = 0; // Invalid choice
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

        private static boolean isValidChoice(String choice) {
            return choice.length() == 1 && "abcd".contains(choice.toLowerCase());
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
