import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyInterface extends JFrame {

    private JTextField display;

    public MyInterface() {
        setTitle("Calculator GUI");
        setSize(200, 300);
        setLayout(new BorderLayout());

        // create display
        display = new JTextField();
        add(display, BorderLayout.NORTH);

        // add buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        String[] buttonLabels = { "(", ")", "DEL", "AC", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-",
                "0", ".", "=", "+" };
        for (String buttonLabel : buttonLabels) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(new ButtonListener());
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            String command = ((JButton) event.getSource()).getText();
            switch (command) {
                case "=":
                    String expression = display.getText();
                    display.setText(Double.toString(evaluateResult(expression)));
                    break;
                case "AC":
                    display.setText("");
                    break;
                case "DEL":
                    if (display.getText().length() > 0) {
                        String correctedExpresion = display.getText().substring(0, display.getText().length() - 1);
                        display.setText(correctedExpresion);
                    } else {
                        display.setText("");
                    }
                    break;
                default:
                    display.setText(display.getText() + command);
                    break;

            }
        }
    }

    private double evaluateResult(String expression) {
        // Implement logic
        return 0;
    }

    public static void main(String[] args) {
        // run the app
        SwingUtilities.invokeLater(() -> new MyInterface());
    }

}
