package utils;

import exceptions.DigitalSignatureException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DSModel {
    private KeyPair keyPair;
    private Signature signature;
    private MessageDigest messageDigest;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    // Constructor với thuật toán RSA cố định để thực hiện chữ kí điện tử
    public DSModel() throws NoSuchAlgorithmException {
        // Khởi tạo thuật toán băm và chữ ký
        this.messageDigest = MessageDigest.getInstance("SHA-256");
        this.signature = Signature.getInstance("SHA256withRSA");

    }

    // Tạo key pair mới với kích thước xác định
    public void generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize, new SecureRandom());
        this.keyPair = keyGen.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    // Ký chuỗi văn bản sau khi băm
    public String signText(String text) throws InvalidKeyException, SignatureException {
        byte[] hashedData = messageDigest.digest(text.getBytes());
        signature.initSign(privateKey);
        signature.update(hashedData);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    // Xác minh chữ ký của chuỗi văn bản sau khi băm
    public boolean verifyText(String text, String signedData) throws DigitalSignatureException {

        try {
            byte[] hashedData = messageDigest.digest(text.getBytes());
            byte[] signedBytes = Base64.getDecoder().decode(signedData);
            signature.initVerify(publicKey);
            signature.update(hashedData);
            return signature.verify(signedBytes);


        } catch (InvalidKeyException e) {
            throw new DigitalSignatureException("Public key không hợp lệ.", e);
        } catch (SignatureException e) {
            throw new DigitalSignatureException("Lỗi khi xác minh chữ ký.", e);
        } catch (IllegalArgumentException e) {
            throw new DigitalSignatureException("Chữ ký không hợp lệ", e);
        }

    }

    // Ký file sau khi băm nội dung
    public String signFile(String filePath) throws IOException, InvalidKeyException, SignatureException {
        byte[] hashedData = hashFileAsBytes(filePath);
        signature.initSign(privateKey);
        signature.update(hashedData);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    // Xác minh chữ ký của file sau khi băm nội dung
    public boolean verifyFile(String filePath, String signedData) throws IOException, InvalidKeyException, SignatureException {
        byte[] hashedData = hashFileAsBytes(filePath);
        byte[] signedBytes = Base64.getDecoder().decode(signedData);
        signature.initVerify(publicKey);
        signature.update(hashedData);
        return signature.verify(signedBytes);
    }

    // Phương thức hash file
    public String hashFile(String filePath) throws IOException {
        byte[] hashedData = hashFileAsBytes(filePath);
        return Base64.getEncoder().encodeToString(hashedData);
    }

    //hash file trả về chuỗi base 64
    private byte[] hashFileAsBytes(String filePath) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bis.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, read);
            }
            return messageDigest.digest();
        }
    }

    // Hash text và trả về chuỗi Base64
    public String hashText(String text) {
        byte[] hashedData = messageDigest.digest(text.getBytes());
        return Base64.getEncoder().encodeToString(hashedData);
    }

    // Trả về Public Key dưới dạng Base64
    public String getPublicKeyString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Trả về Private Key dưới dạng Base64
    public String getPrivateKeyString() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // load Private Key
    public void setPrivateKey(String privateKeyBase64) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
    }

    // load Public Key
    public void setPublicKey(String publicKeyBase64) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    // Cập nhật thuật toán hash
    public void setHashAlgorithm(String algorithm) throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance(algorithm);
    }
}
