package toolDS;

import javax.swing.*;
import java.awt.*;

public class RSADSView extends JFrame {

    // GUI Components
    private JTextArea inputTextArea, outputTextArea;
    private JButton genKeyPairButton, copyPrivateKeyButton, copyPublicKeyButton, loadPrivateKeyButton, loadPublicKeyButton;

    private JButton signTextButton, verifyTextButton, signFileButton, verifyFileButton;
    private JButton chooseFileButton; // Nút chọn file
    private JLabel messages;
    private JTextField publicKeyField, privateKeyField;
    private JComboBox<String> hashAlgorithmComboBox;

    public RSADSView() {
        // Cấu hình JFrame
        setTitle("RSA Digital Signature Tool");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 250));

        // Left Panel: Quản lý key
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(7, 1, 10, 10));
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key Management"));
        keyPanel.setBackground(new Color(245, 245, 245));

        genKeyPairButton = createStyledButton("Generate Key Pair");

        loadPrivateKeyButton = createStyledButton("Load private");
        copyPrivateKeyButton = createStyledButton("Copy to File");
        JPanel privateKeyPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        privateKeyPanel.add(copyPrivateKeyButton);
        privateKeyPanel.add(loadPrivateKeyButton);

        copyPublicKeyButton = createStyledButton("Copy to File");
        loadPublicKeyButton = createStyledButton("Load public");
        JPanel publicKeyPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        publicKeyPanel.add(copyPublicKeyButton);
        publicKeyPanel.add(loadPublicKeyButton);

        publicKeyField = new JTextField();
        publicKeyField.setEditable(false);
        publicKeyField.setBackground(Color.LIGHT_GRAY);

        privateKeyField = new JTextField();
        privateKeyField.setEditable(false);
        privateKeyField.setBackground(Color.LIGHT_GRAY);

        keyPanel.add(genKeyPairButton);
        keyPanel.add(new JLabel("Public Key:"));
        keyPanel.add(publicKeyField);
        keyPanel.add(publicKeyPanel);
        keyPanel.add(new JLabel("Private Key:"));
        keyPanel.add(privateKeyField);
        keyPanel.add(privateKeyPanel);

        // Right Panel: Hashing and Signing
//        JPanel operationPanel = new JPanel();
//        operationPanel.setLayout(new GridLayout(8, 1, 10, 10));
//        operationPanel.setBorder(BorderFactory.createTitledBorder("Hàm băm và chữ kí điện tử"));
//        operationPanel.setBackground(new Color(245, 245, 245));

        hashAlgorithmComboBox = new JComboBox<>(new String[]{"SHA-256", "SHA-1", "MD5"});

        signTextButton = createStyledButton("Sign Text");
        verifyTextButton = createStyledButton("Verify Text");
        signFileButton = createStyledButton("Sign File");
        verifyFileButton = createStyledButton("Verify File");

//        operationPanel.add(new JLabel("Chọn Thuật toán băm:"));
//        operationPanel.add(hashAlgorithmComboBox);
//
//        operationPanel.add(signTextButton);
//        operationPanel.add(verifyTextButton);
//        operationPanel.add(signFileButton);
//        operationPanel.add(verifyFileButton);

        mainPanel.add(keyPanel);
//        mainPanel.add(operationPanel);

        // Input output
        JPanel ioPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        ioPanel.setBorder(BorderFactory.createTitledBorder("Input and Output"));
        ioPanel.setBackground(new Color(230, 240, 250));

        inputTextArea = new JTextArea(4, 30);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);

        outputTextArea = new JTextArea(4, 30);
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.LIGHT_GRAY);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        chooseFileButton = createStyledButton("Choose File");

        inputPanel.add(inputScrollPane, BorderLayout.CENTER);
        inputPanel.add(chooseFileButton, BorderLayout.EAST);

        ioPanel.add(createLabeledComponent("Input:", inputPanel));
        ioPanel.add(createLabeledComponent("Output:", outputScrollPane));

        // Messages
        messages = new JLabel("Thông báo", SwingConstants.CENTER);
        messages.setFont(new Font("Arial", Font.BOLD, 16));
        messages.setForeground(Color.DARK_GRAY);

        // Thêm panel vào frame
        add(mainPanel, BorderLayout.NORTH);
        add(ioPanel, BorderLayout.CENTER);
        add(messages, BorderLayout.SOUTH);

        // Hiển thị giao diện
        setVisible(true);
    }

    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(140, 30));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // Getter và Setter cho Input và Output TextArea
    public JTextArea getInputTextArea() {
        return inputTextArea;
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }

    public void setOutputTextArea(String text) {
        outputTextArea.setText(text);
    }

    // Getter cho các nút chức năng
    public JButton getGenKeyPairButton() {
        return genKeyPairButton;
    }

    public JButton getCopyPrivateKeyButton() {
        return copyPrivateKeyButton;
    }

    public JButton getCopyPublicKeyButton() {
        return copyPublicKeyButton;
    }

    public JButton getLoadPrivateKeyButton() {
        return loadPrivateKeyButton;
    }

    public JButton getLoadPublicKeyButton() {
        return loadPublicKeyButton;
    }




    public JButton getSignTextButton() {
        return signTextButton;
    }

    public JButton getVerifyTextButton() {
        return verifyTextButton;
    }

    public JButton getSignFileButton() {
        return signFileButton;
    }

    public JButton getVerifyFileButton() {
        return verifyFileButton;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public JTextField getPublicKeyField() {
        return publicKeyField;
    }

    public void setPublicKeyField(String text) {
        publicKeyField.setText(text);
    }

    public JTextField getPrivateKeyField() {
        return privateKeyField;
    }

    public void setPrivateKeyField(String text) {
        privateKeyField.setText(text);
    }

    public JComboBox<String> getHashAlgorithmComboBox() {
        return hashAlgorithmComboBox;
    }

    public void setMessage(String message) {
        messages.setText(message);
    }
}
