package utils;

import exceptions.DigtialSignatureException;

import java.security.*;
import java.security.spec.*;
import java.io.*;
import java.util.Base64;

public class DigitalSignature {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    // Tao khoa neu chua co khoa
    public void generateKeyPair(String algorithm, int keySize) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(keySize);
        KeyPair keyPair = keyGen.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // Lưu khóa riêng vào file
    public void savePrivateKeyToFile(String privateKeyFile) throws Exception {
        try (ObjectOutputStream privateKeyOut = new ObjectOutputStream(new FileOutputStream(privateKeyFile))) {
            privateKeyOut.writeObject(this.privateKey);
        }
    }

    // save public key
    public void savePublicKeyToFile(String publicKeyFile) throws Exception {
        try (ObjectOutputStream publicKeyOut = new ObjectOutputStream(new FileOutputStream(publicKeyFile))) {
            publicKeyOut.writeObject(this.publicKey);
        }
    }

    // load private key tu file
    public void loadPrivateKeyFromFile(String privateKeyFile) throws Exception {
        File file = new File(privateKeyFile);
        if (!file.exists()) {
            throw new DigtialSignatureException("File public key không tồn tại");
        }
        ObjectInputStream privateKeyIn = new ObjectInputStream(new FileInputStream(file));
        this.privateKey = (PrivateKey) privateKeyIn.readObject();
    }


    // Load public key tu file
    public void loadPublicKeyFromFile(String publicKeyFile) throws Exception {
        File file = new File(publicKeyFile);
        if (!file.exists()) {
            throw new DigtialSignatureException("File public key không tồn tại");
        }
        ObjectInputStream publicKeyIn = new ObjectInputStream(new FileInputStream(file));
        this.publicKey = (PublicKey) publicKeyIn.readObject();

    }

    // Chuyển key thanh dang base64
    public String keyToBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public void loadPrivateKeyFromBase64(String privateKeyBase64) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
    }

    public void loadPublicKeyFromBase64(String publicKeyBase64) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    // sign voi private key
    public String signDataBase64(String data) throws Exception {
        if (this.privateKey == null) {
            throw new DigtialSignatureException("Private key không tồn tại");
        }
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(this.privateKey);
        signature.update(data.getBytes());
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
//Ký trả về mảng byte
    public byte[] signData(String data) throws Exception {
        if (this.privateKey == null) {
            throw new DigtialSignatureException("Private key không tồn tại");
        }
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(this.privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    public String signData(byte[] data) throws Exception {
        if (this.privateKey == null) {
            throw new DigtialSignatureException("Private key không tồn tại");
        }
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(this.privateKey);
        signature.update(data);

        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // verify voi publickey
    public boolean verifySignature(String data, String signatureBase64) throws Exception {
        if (this.publicKey == null) {
            throw new DigtialSignatureException("Public key không tồn tại");
        }
        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(this.publicKey);
        signature.update(data.getBytes());
        return signature.verify(signatureBytes);
    }

    // Phương thức main để kiểm thử
    public static void main(String[] args) {
        try {
            DigitalSignature ds = new DigitalSignature();

            // Tạo cặp khóa mới
            ds.generateKeyPair("RSA", 2048);

            // In thông tin khóa
            String privateKeyBase64 = ds.keyToBase64(ds.getPrivateKey());
            String publicKeyBase64 = ds.keyToBase64(ds.getPublicKey());
            System.out.println("Private Key: " + privateKeyBase64);
            System.out.println("Public Key: " + publicKeyBase64);

            // Dữ liệu cần ký
            String data = "This is a test message.";

            // Tạo chữ ký
//            String signatureBase64 = ds.signDataBase64(data);
            String signatureBase64 = ds.signDataBase64(data);
            System.out.println("Generated Signature (Base64): " + signatureBase64);

            // Tải lại khóa từ Base64
            ds.loadPublicKeyFromBase64(publicKeyBase64);
            ds.loadPrivateKeyFromBase64(privateKeyBase64);

            // Xác minh chữ ký
            boolean isValid = ds.verifySignature(data, signatureBase64);
            System.out.println("Signature valid: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
