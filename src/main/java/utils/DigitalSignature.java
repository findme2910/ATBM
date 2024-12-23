package utils;

import exceptions.DigitalSignatureException;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Getter
public class DigitalSignature {
    private static final String ALGO = "RSA";
    private static final String SIGNATURE_ALGO = "SHA256withRSA";
    private static final int KEY_SIZE = 2048;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public DigitalSignature() {
    }
    // Tao khoa neu chua co khoa
    public void generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGO);
        keyGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyGen.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // Lưu khóa riêng dưới dạng Base64 vào file
    public void savePrivateKeyToFile(String privateKeyFile) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(privateKeyFile);
        fileOut.write(Base64.getEncoder().encodeToString(this.privateKey.getEncoded()).getBytes(StandardCharsets.UTF_8));
        fileOut.close();
    }

    // Lưu khóa công khai dưới dạng Base64 vào file
    public void savePublicKeyToFile(String publicKeyFile) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(publicKeyFile);
        fileOut.write(Base64.getEncoder().encodeToString(this.publicKey.getEncoded()).getBytes(StandardCharsets.UTF_8));
        fileOut.close();
    }

    // Đọc khóa riêng từ file và giải mã từ Base64
    public void loadPrivateKeyFromFile(String privateKeyFile) throws Exception {
        File file = new File(privateKeyFile);
        if (!file.exists()) {
            throw new Exception("File private key không tồn tại");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String privateKeyBase64 = reader.readLine();
            byte[] decodedKey = Base64.getDecoder().decode(privateKeyBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
        }
    }

    // Đọc khóa công khai từ file và giải mã từ Base64
    public void loadPublicKeyFromFile(String publicKeyFile) throws DigitalSignatureException {
        File file = new File(publicKeyFile);
        if (!file.exists()) {
            throw new DigitalSignatureException("File public key không tồn tại");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String publicKeyBase64 = reader.readLine();
            byte[] decodedKey = Base64.getDecoder().decode(publicKeyBase64);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPrivateKey(String privateKeyBase64) throws DigitalSignatureException {

        try {
            System.out.println("PrivateKeyBase 64 : " + privateKeyBase64);
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            loadPrivateKey(privateKeyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Không tìm thấy thuật toán tạo key", e);
        } catch (InvalidKeySpecException e) {
            throw new DigitalSignatureException("Private key không hợp lệ!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DigitalSignatureException(e.getMessage(), e);
        }

    }

    public void loadPrivateKey(byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = null;

        keyFactory = KeyFactory.getInstance(ALGO);
        this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));

    }

    public void loadPublicKey(String publicKeyBase64) throws DigitalSignatureException {

        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            loadPublicKey(publicKeyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Không tìm thấy thuật toán tạo key", e);
        } catch (InvalidKeySpecException e) {
            throw new DigitalSignatureException("Public key không hợp lệ!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DigitalSignatureException(e.getMessage(), e);
        }
    }

    public void loadPublicKey(byte[] publicKeyByte) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance(ALGO);
        this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));

    }

    // Chuyển key thanh dang base64
    public String keyToBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // sign voi private key
    public String signDataBase64(String data) throws DigitalSignatureException {
        if (this.privateKey == null) {
            throw new DigitalSignatureException("Private key không tồn tại.");
        }

        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGO);

            signature.initSign(this.privateKey);

            signature.update(data.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Thuật toán ký không được hỗ trợ: " + SIGNATURE_ALGO, e);
        } catch (InvalidKeyException e) {
            throw new DigitalSignatureException("Private key không hợp lệ.", e);
        } catch (SignatureException e) {
            throw new DigitalSignatureException("Lỗi khi ký dữ liệu.", e);
        }
    }

    //Ký trả về mảng byte
    public byte[] signData(String data) throws DigitalSignatureException {

        if (this.privateKey == null) {
            throw new DigitalSignatureException("Private key không tồn tại.");
        }

        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGO);

            signature.initSign(this.privateKey);

            signature.update(data.getBytes());

            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Thuật toán ký không được hỗ trợ: " + SIGNATURE_ALGO, e);
        } catch (InvalidKeyException e) {
            throw new DigitalSignatureException("Private key không hợp lệ.", e);
        } catch (SignatureException e) {
            throw new DigitalSignatureException("Lỗi khi ký dữ liệu.", e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new DigitalSignatureException(e.getMessage());
        }
    }

    public String signData(byte[] data) throws DigitalSignatureException {
        if (this.privateKey == null) {
            throw new DigitalSignatureException("Private key không tồn tại.");
        }

        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGO);
            signature.initSign(this.privateKey);
            signature.update(data);

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Thuật toán ký không được hỗ trợ: " + SIGNATURE_ALGO, e);
        } catch (InvalidKeyException e) {
            throw new DigitalSignatureException("Private key không hợp lệ.", e);
        } catch (SignatureException e) {
            throw new DigitalSignatureException("Lỗi khi ký dữ liệu.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DigitalSignatureException(e.getMessage());
        }
    }

    // verìy with public key
    public boolean verifySignature(String data, String signatureBase64) throws DigitalSignatureException {
        if (this.publicKey == null) {
            throw new DigitalSignatureException("Public key không tồn tại.");
        }

        try {
            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
            Signature signature = Signature.getInstance(SIGNATURE_ALGO);
            signature.initVerify(this.publicKey);
            signature.update(data.getBytes());
            boolean result = signature.verify(signatureBytes);
            System.out.println(result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new DigitalSignatureException("Thuật toán xác minh không được hỗ trợ: " + SIGNATURE_ALGO, e);
        } catch (InvalidKeyException e) {
            throw new DigitalSignatureException("Public key không hợp lệ.", e);
        } catch (SignatureException e) {
            throw new DigitalSignatureException("Lỗi khi xác minh chữ ký.", e);
        } catch (IllegalArgumentException e) {
            throw new DigitalSignatureException("Chữ ký không hợp lệ.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DigitalSignatureException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            DigitalSignature ds = new DigitalSignature();

            // Tạo cặp khóa mới
            ds.generateKeyPair();

//            ds.loadPublicKeyFromFile("/home/ngoctaiphan/WorkSpace/Test/Keys/publickey");
            // In thông tin khóa
            String privateKeyBase64 = ds.keyToBase64(ds.getPrivateKey());
            String publicKeyBase64 = ds.keyToBase64(ds.getPublicKey());
            System.out.println("Private Key: " + privateKeyBase64);
            System.out.println("Public Key: " + publicKeyBase64);
            ds.savePublicKeyToFile("D:/Study/Four_year/ATTT/testkey/publickey");
            ds.savePrivateKeyToFile("D:/Study/Four_year/ATTT/testkey/privatekey");
//            // Dữ liệu cần ký
//            String data = "This is a test message.";
//
//            // Tạo chữ ký
////            String signatureBase64 = ds.signDataBase64(data);
//            String signatureBase64 = ds.signDataBase64(data);
//            System.out.println("Generated Signature (Base64): " + signatureBase64);

            // Tải lại khóa từ Base64
//            ds.loadPublicKey(publicKeyBase64);
//            ds.loadPrivateKey(privateKeyBase64);
//            ds.savePublicKeyToFile("/home/ngoctaiphan/WorkSpace/Test/Keys/publickey");

            // Xác minh chữ ký
//            boolean isValid = ds.verifySignature(data, signatureBase64);
//            System.out.println("Signature valid: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
