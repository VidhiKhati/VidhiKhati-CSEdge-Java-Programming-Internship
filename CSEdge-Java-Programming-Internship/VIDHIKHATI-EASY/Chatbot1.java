import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
public class Chatbot1{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Chatbot1 chatbot = new Chatbot1();

        System.out.println("Hello! I am your console-based chatbot. How can I assist you today?");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye!");
                break;
            }

            chatbot.handleCommand(userInput);
        }

        scanner.close();
    }
public void handleCommand(String command) {
    if (command.toLowerCase().startsWith("open application")) {
        openApplication(command.substring(16).trim());
    } else if (command.toLowerCase().startsWith("search web")) {
        searchWeb(command.substring(10).trim());
    } else {
        System.out.println("Sorry, I don't understand that command.");
    }
}
private void openApplication(String applicationName) {
    String os = System.getProperty("os.name").toLowerCase();
    Runtime runtime = Runtime.getRuntime();
    
    try {
        if (os.contains("win")) {

            if (applicationName.equalsIgnoreCase("notepad")) {
                runtime.exec("notepad");
            } else {
                System.out.println("Application not recognized or not supported.");
            }
        } else if (os.contains("mac")) {

            if (applicationName.equalsIgnoreCase("textedit")) {
                runtime.exec("open -a TextEdit");
            } else {
                System.out.println("Application not recognized or not supported.");
            }
        } else if (os.contains("nix") || os.contains("nux")) {
            if (applicationName.equalsIgnoreCase("gedit")) {
                runtime.exec("gedit");
            } else {
                System.out.println("Application not recognized or not supported.");
            }
        }
    } catch (IOException e) {
        System.out.println("Failed to open application: " + e.getMessage());
    }
}
private void searchWeb(String query) {
    String url = "https://www.google.com/search?q=" + query.replace(" ", "+");
    if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open web browser: " + e.getMessage());
        }
    } else {
        System.out.println("Desktop operations not supported on this system.");
    }
}
}
