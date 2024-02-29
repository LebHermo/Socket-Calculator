import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1050);
        Scanner scan = new Scanner(System.in);

        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            Scanner isc = new Scanner(socket.getInputStream());

            System.out.print("Enter the amount in PHP (Philippine Pesos): ");
            String amount = scan.nextLine();
            double newAmount = Double.parseDouble(amount);

            if (newAmount <= 0) {
                System.out.println("Invalid amount. Please enter a positive number");
                System.exit(0);
            } else {
                System.out.println("Choose currency to convert to (a - USD, b - UAE, c - GBP, d - JPY):");
                String choice = scan.nextLine();

                if (isValidChoice(choice)){
                    // Send to server
                    choice = choice.toLowerCase();
                    ps.println(newAmount + "," + choice);
                } else {
                    System.out.println("Invalid input. Please choose among the provided choices.");
                    System.exit(0);
                }

                // Receive from server
                String response = isc.nextLine();
                switch (choice){
                    case "a":
                        System.out.println("Converted amount: USD " + response);
                        break;
                    case "b":
                       System.out.println("Converted amount: UAE " + response);
                        break;
                    case "c":
                        System.out.println("Converted amount: GBP " + response);
                        break;
                    case "d":
                        System.out.println("Converted amount: JPY " + response);
                        break;
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input. Please enter a valid number");
        } finally {
            socket.close();
            scan.close();
        }
    }
    private static boolean isValidChoice(String choice) {
        return choice.length() == 1 && "abcd".contains(choice.toLowerCase());
    }
}
