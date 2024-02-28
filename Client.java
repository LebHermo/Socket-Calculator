import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1050);
        Scanner scanner = new Scanner(System.in);

        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            Scanner isc = new Scanner(socket.getInputStream());

            System.out.print("Enter the amount in PHP (Philippine Pesos):");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 

            System.out.println("Choose currency to convert to (a - USD, b - UAE, c - GBP, d - JPY):");
            String choice = scanner.nextLine();

            // Send to server
            ps.println(amount);
            ps.println(choice);

            // Receive from server
            String response = isc.nextLine();
			// Output
            System.out.println("Converted amount: PHP " + response);

        } finally {
            socket.close();
            scanner.close();
        }
    }
}
