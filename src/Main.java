import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private double balance = 0;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea messageArea;

    public Main() {
        // Set up the main frame
        setTitle("Banking Program");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Changed to handle closing manually
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Add window listener for the close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitMessage();
            }
        });

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create top panel for balance display
        JPanel topPanel = new JPanel(new FlowLayout());
        balanceLabel = new JLabel("Current Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(balanceLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create center panel for input and buttons
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Amount input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Amount: $"));
        amountField = new JTextField(10);
        inputPanel.add(amountField);
        centerPanel.add(inputPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);
        centerPanel.add(buttonPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Create bottom panel for messages
        messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add action listeners
        depositButton.addActionListener(e -> handleDeposit());
        withdrawButton.addActionListener(e -> handleWithdraw());
        exitButton.addActionListener(e -> showExitMessage());

        // Add the main panel to the frame
        add(mainPanel);

        updateBalanceDisplay();
    }

    private void showExitMessage() {
        JOptionPane.showMessageDialog(this,
                "Thank you! Have a nice day!",
                "Goodbye",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText(String.format("Current Balance: $%.2f", balance));
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount < 0) {
                messageArea.append("Amount can't be negative\n");
            } else {
                balance += amount;
                messageArea.append(String.format("Deposited: $%.2f\n", amount));
                updateBalanceDisplay();
            }
        } catch (NumberFormatException e) {
            messageArea.append("Please enter a valid amount\n");
        }
        amountField.setText("");
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount < 0) {
                messageArea.append("Amount can't be negative\n");
            } else if (amount > balance) {
                messageArea.append("Insufficient funds\n");
            } else {
                balance -= amount;
                messageArea.append(String.format("Withdrawn: $%.2f\n", amount));
                updateBalanceDisplay();
            }
        } catch (NumberFormatException e) {
            messageArea.append("Please enter a valid amount\n");
        }
        amountField.setText("");
    }

    public static void main(String[] args) {
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Main bankingApp = new Main();
            bankingApp.setVisible(true);
        });
    }
}