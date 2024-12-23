package toolDS;



import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;


public class RSADSController {
    private final RSADSModel model;
    private final RSADSView view;
    private boolean enableOutputClick = false; 

    public RSADSController(RSADSView view, RSADSModel model) {
        this.view = view;
        this.model = model;

        initController();
    }

    private void initController() {
        view.getGenKeyPairButton().addActionListener(e -> handleGenerateKeyPair());
        view.getCopyPublicKeyButton().addActionListener(e -> handleCopyKey(view.getPublicKeyField().getText(), "Public Key"));
        view.getCopyPrivateKeyButton().addActionListener(e -> handleCopyKey(view.getPrivateKeyField().getText(), "Private Key"));
        view.getHashAlgorithmComboBox().addActionListener(e -> handleChangeHashAlgorithm());
        view.getSignTextButton().addActionListener(e -> handleSignText());
        view.getVerifyTextButton().addActionListener(e -> handleVerifyText());
        view.getSignFileButton().addActionListener(e -> handleSignFile());
        view.getVerifyFileButton().addActionListener(e -> handleVerifyFile());
        view.getChooseFileButton().addActionListener(e -> handleChooseFile());
        view.getLoadPublicKeyButton().addActionListener(e -> handleLoadKey(true));
        view.getLoadPrivateKeyButton().addActionListener(e -> handleLoadKey(false));
        
        
        view.getOutputTextArea().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleFieldClick(view.getOutputTextArea().getText());
            }
        });

      
    }

    private void handleGenerateKeyPair() {
        try {
            model.generateKeyPair(1024); 
            view.setPublicKeyField(model.getPublicKeyString());
            view.setPrivateKeyField(model.getPrivateKeyString());
            view.setMessage("Key pair generate thành công!");
        } catch (Exception e) {
            view.setMessage("Lỗi khi generate KeyPair " + e.getMessage());
        }
    }

    private void handleCopyKey(String key, String keyType) {
        if (key.isEmpty()) {
            view.setMessage(keyType + " đang trống!");
            return;
        }

        int choice = JOptionPane.showOptionDialog(view,
                "Copy " + keyType + " vào clipboard hoặc lưu vào file?",
                "Copy Key",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Clipboard", "File"},
                "Clipboard");

        if (choice == JOptionPane.YES_OPTION) {
            copyToClipboard(key);
            view.setMessage(keyType + " đã copy vào clipboard!");
        } else if (choice == JOptionPane.NO_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                }

                @Override
                public String getDescription() {
                    return "Text Files (*.txt)";
                }
            });

            int result = fileChooser.showSaveDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                }
                try (FileWriter writer = new FileWriter(selectedFile)) {
                    writer.write(key);
                    view.setMessage(keyType + " đã lưu vào file thành công: " + selectedFile.getAbsolutePath());
                } catch (Exception e) {
                    view.setMessage("Lỗi khi lưu: " + keyType + ": " + e.getMessage());
                }
            }
        }
    }

    private void handleFieldClick(String text) {
        if (text.isEmpty()) {
            view.setMessage("Output đang trống");
        } else if(!enableOutputClick) {
        	copyToClipboard(text);
            view.setMessage("Đã lưu output vào clipboard!");
        }
        else {
        	int choice = JOptionPane.showOptionDialog(
                    view,
                    "Lưu nội dung vào clipboard hoặc file?",
                    "Lưu output",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Clipboard", "File"},
                    "Clipboard"
            );

            if (choice == JOptionPane.YES_OPTION) {
                copyToClipboard(text);
                view.setMessage("Đã lưu output vào clipboard!");
            } else if (choice == JOptionPane.NO_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "Text Files (*.txt)";
                    }
                });

                int result = fileChooser.showSaveDialog(view);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }
                    try (FileWriter writer = new FileWriter(selectedFile)) {
                        writer.write(text);
                        view.setMessage("Đã lưu output vào file: " + selectedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        view.setMessage("Lỗi khi lưu file: " + ex.getMessage());
                    }
                }
            }
        }
        
    }


    private void handleSignText() {
        try {
            String inputText = view.getInputTextArea().getText();
            if (model.getPrivateKeyString() == null || model.getPrivateKeyString().isEmpty()) {
                return;
            }
            if (inputText.isEmpty()) {
                view.setMessage("Input đang trống");
                return;
            }

            String signature = model.signText(inputText);
            view.setOutputTextArea(signature);
            view.setMessage("Kí thành công text. Hãy lưu chữ kí để xác nhận! ");
            enableOutputClick = true;

        } catch (Exception e) {
            view.setMessage("Lỗi khi kí text: " + e.getMessage());
        }
    }

    private void handleVerifyText() {
        try {
            String inputText = view.getInputTextArea().getText();
            if (inputText.isEmpty()) {
                view.setMessage("Vui lòng nhập văn bản cần xác nhận!");
                return;
            }


            JPanel panel = new JPanel(new BorderLayout(10, 10));
            JTextField signatureField = new JTextField();
            JButton loadFileButton = new JButton("Load File");

            panel.add(new JLabel("Nhập chữ ký hoặc chọn file:"), BorderLayout.NORTH);
            panel.add(signatureField, BorderLayout.CENTER);
            panel.add(loadFileButton, BorderLayout.EAST);

            loadFileButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "Text Files (*.txt)";
                    }
                });

                int result = fileChooser.showOpenDialog(view);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        String signature = Files.readString(file.toPath());
                        signatureField.setText(signature);
                    } catch (Exception ex) {
                        view.setMessage("Lỗi khi đọc file chữ ký: " + ex.getMessage());
                    }
                }
            });

            int result = JOptionPane.showConfirmDialog(view, panel, "Verify Text", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String signature = signatureField.getText();
                if (signature.isEmpty()) {
                    view.setMessage("Chữ ký trống! Không thể xác nhận văn bản.");
                    return;
                }

                boolean isValid = model.verifyText(inputText, signature);
                view.setMessage(isValid ? "Chữ ký hợp lệ!" : "Chữ ký không hợp lệ!");
            }
        } catch (Exception e) {
            view.setMessage("Lỗi khi xác nhận văn bản: " + e.getMessage());
        }
    }



    private void handleSignFile() {
        try {
            String filePath = view.getInputTextArea().getText();
            if (model.getPrivateKeyString() == null || model.getPrivateKeyString().isEmpty()) { 
                return;
            }
            if (filePath.isEmpty()) {
                view.setMessage("Vùng input trống! ");
                return;
            }
            String signature = model.signFile(filePath);
            view.setOutputTextArea(signature);
            view.setMessage("Kí file thành công, Hãy lưu chữ ký để xác nhận");
            enableOutputClick = true;
        } catch (Exception e) {
            view.setMessage("Lỗi khi kí file: " + e.getMessage());
        }
    }

    private void handleVerifyFile() {
        try {
            String filePath = view.getInputTextArea().getText();

            if (filePath.isEmpty()) {
                view.setMessage("Vui lòng nhập đường dẫn file cần xác nhận!");
                return;
            }

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            JTextField signatureField = new JTextField();
            JButton loadFileButton = new JButton("Load File");

            panel.add(new JLabel("Nhập chữ ký hoặc chọn file:"), BorderLayout.NORTH);
            panel.add(signatureField, BorderLayout.CENTER);
            panel.add(loadFileButton, BorderLayout.EAST);

            loadFileButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "Text Files (*.txt)";
                    }
                });

                int result = fileChooser.showOpenDialog(view);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        String signature = Files.readString(file.toPath());
                        signatureField.setText(signature);
                    } catch (Exception ex) {
                        view.setMessage("Lỗi khi đọc file chữ ký: " + ex.getMessage());
                    }
                }
            });

            int result = JOptionPane.showConfirmDialog(view, panel, "Verify File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String signature = signatureField.getText();
                if (signature.isEmpty()) {
                    view.setMessage("Chữ ký trống! Không thể xác nhận file.");
                    return;
                }

                boolean isValid = model.verifyFile(filePath, signature);
                view.setMessage(isValid ? "Chữ ký hợp lệ!" : "Chữ ký không hợp lệ!");
            }
        } catch (Exception e) {
            view.setMessage("Lỗi khi xác nhận File: " + e.getMessage());
        }
    }


    private void handleChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            view.getInputTextArea().setText(selectedFile.getAbsolutePath());
            view.setMessage("File đã chọn : " + selectedFile.getName());
        }
    }

    private void handleChangeHashAlgorithm() {
        String algorithm = (String) view.getHashAlgorithmComboBox().getSelectedItem();
        try {
            model.setHashAlgorithm(algorithm);
            view.setMessage("Thuật toán băm hiện tại:  " + algorithm);
        } catch (Exception e) {
            view.setMessage("Lỗi khi thay đổi thuật toán băm : " + e.getMessage());
        }
    }
    private void handleLoadKey(boolean isPublicKey) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextField keyField = new JTextField();
        JButton fileButton = new JButton("Browse File");
        JLabel messageLabel = new JLabel("Nhập " + (isPublicKey ? "Public" : "Private") + " Key hoặc chọn file");
        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(keyField, BorderLayout.CENTER);
        panel.add(fileButton, BorderLayout.EAST);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                }

                @Override
                public String getDescription() {
                    return "Text Files (*.txt)";
                }
            });

            int fileResult = fileChooser.showOpenDialog(view);
            if (fileResult == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String fileKey = Files.readString(file.toPath());
                    keyField.setText(fileKey);
                } catch (Exception ex) {
                    view.setMessage("Lỗi reading file: " + ex.getMessage());
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(view, panel,
                "Load " + (isPublicKey ? "Public" : "Private") + " Key",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String key = keyField.getText();
            if (key.isEmpty()) {
                view.setMessage("Key trống! ");
                return;
            }

            try {
                if (isPublicKey) {
                    model.setPublicKey(key);
                    view.setPublicKeyField(key);
                    view.setMessage("Public Key load thành công!");
                } else {
                    model.setPrivateKey(key);
                    view.setPrivateKeyField(key);
                    view.setMessage("Private Key load thành công!");
                }
            } catch (Exception e) {
                view.setMessage("Lỗi khi load key: " + e.getMessage());
            }
        }
    }
    private void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit()
               .getSystemClipboard()
               .setContents(new StringSelection(text), null);
    }

}
