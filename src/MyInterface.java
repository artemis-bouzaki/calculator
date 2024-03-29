import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Character;
import java.util.Stack;

public class MyInterface extends JFrame {

    private JTextField display;

    // function to create interface
    public MyInterface() {

        setTitle("Calculator GUI");
        setSize(300, 400);
        setLayout(new BorderLayout());

        // create display
        display = new JTextField();
        add(display, BorderLayout.NORTH);

        // add buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        String[] buttonLabels = { "(", ")", "DEL", "AC", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-",
                "0", " ", "=", "+" };
        // add a button listener for each button
        for (String buttonLabel : buttonLabels) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(new ButtonListener());
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Class to imlement shunting yard algorithm
    private class shuntingYard {

        // determine if char is letter or digit
        static boolean digitOrOperator(char c) {
            if (Character.isLetterOrDigit(c)) {
                return true;
            } else {
                return false;
            }
        }

        // find operator with higher precedence value
        static int getPrecedence(char c) {
            if (c == '+' || c == '-') {
                return 1;
            } else {
                return 2;
            }
        }

        // find left or right precedence
        static boolean hasLeftAssociativity(char c) {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                return true;
            } else {
                return false;
            }
        }

        // convert infix into postfix (reverse polish) notation
        static String infixToPostfix(String expression) {
            // initialise stack for operators and string for output
            Stack<Character> stack = new Stack<>();
            String output = new String("");

            // iterate over expression
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);

                // if the token is a digit, add to the output
                if (digitOrOperator(c)) {
                    output += c;
                    // if the token is '(' push to the stack
                } else if (c == '(') {
                    stack.push(c);
                    // if ')' is encountered pop and append to the output until '(' is encountered
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        output += stack.pop();
                    }
                } else {
                    while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())
                            && hasLeftAssociativity(c)) {
                        output += stack.pop();
                    }
                    stack.push(c);
                }

            }
            // pop all remaining operators from the stack to the output
            while (!stack.isEmpty()) {
                output += stack.pop();
            }
            return output;
        }

    }

    // class to implement button listeners
    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            String command = ((JButton) event.getSource()).getText();
            switch (command) {
                case "=":
                    // evaluate result
                    String expression = display.getText();
                    String expressionInPostfix = shuntingYard.infixToPostfix(expression);
                    display.setText(Double.toString(evaluateResult(expressionInPostfix)));
                    break;

                case "AC":
                    // delete all text
                    display.setText("");
                    break;

                case "DEL":
                    // delete last char
                    if (display.getText().length() > 0) {
                        String correctedExpresion = display.getText().substring(0, display.getText().length() - 1);
                        display.setText(correctedExpresion);
                    } else {
                        display.setText("");
                    }
                    break;

                default:
                    // if none of the above, simply display the char
                    display.setText(display.getText() + command);
                    break;

            }
        }
    }

    private double evaluateResult(String expression) {
        // Implement logic
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            // if the char is a digit, add it to the stack
            if (Character.isDigit(c)) {
                stack.push((double) Character.getNumericValue(c));

                // if the char is an operator pop the last two digits from the stack and perform
                // an operation
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {

                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result;
                switch (c) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        result = operand1 / operand2;
                        break;
                    default:
                        result = 0;

                }
                // push the result in the stack
                stack.push(result);
            }

        }
        // the last value of the stack should be the result
        return stack.pop();
    }

    public static void main(String[] args) {
        // run the app
        SwingUtilities.invokeLater(() -> new MyInterface());
    }

}
