import java.awt.*;
import java.awt.event.*;
public class Calculator extends Frame implements ActionListener {
    private TextField tfInput;
    private double num1, num2, result;
    private char operator;
    public Calculator() {
        setTitle("AWT Calculator");
        setLayout(new BorderLayout());
        
        // Text field for input
        tfInput = new TextField();
        tfInput.setEditable(false);
        add(tfInput, BorderLayout.NORTH);
        
        // Panel for buttons
        Panel panelButtons = new Panel();
        panelButtons.setLayout(new GridLayout(4, 4));
        
        // Buttons for numbers and operations
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.addActionListener(this);
            panelButtons.add(button);
        }
        
        add(panelButtons, BorderLayout.CENTER);
        
        // Event handling for closing the window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        
        setSize(400, 400);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "+":
            case "-":
            case "*":
            case "/":
                operator = command.charAt(0);
                num1 = Double.parseDouble(tfInput.getText());
                tfInput.setText("");
                break;
                
            case "=":
                num2 = Double.parseDouble(tfInput.getText());
                calculate();
                tfInput.setText(String.valueOf(result));
                break;
                
            default:
                tfInput.setText(tfInput.getText() + command);
                break;
        }
    }
    
    private void calculate() {
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                try {
                    result = num1 / num2;
                } catch (ArithmeticException e) {
                    tfInput.setText("Error");
                }
                break;
        }
    }
    
    public static void main(String[] args) {
        new Calculator();
    }
}
