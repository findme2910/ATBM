package Service.DigitalSignature;

import utils.DigitalSignature;

public class DigitalSignatureService {
    private DigitalSignature digitalSignature;

    public DigitalSignatureService(DigitalSignature digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
//Tạo key nếu chưa có
    public void genKey(String algo, int size) throws Exception {
        digitalSignature.generateKeyPair(algo, size);
    }
//Lưu private key
    public void savePrivateKey(String filePath) throws Exception {
        digitalSignature.savePrivateKeyToFile(filePath);
    }
//lưu public key
    public void savePublicKey(String filePath) throws Exception {
        digitalSignature.savePublicKeyToFile(filePath);
    }

    // load  khóa từ file
    public void loadPrivateKey(String privateKeyFile) throws Exception {
        digitalSignature.loadPrivateKeyFromFile(privateKeyFile);
    }
//load public key từ file
    public void loadPublicKey(String publicKeyFile) throws Exception {
        digitalSignature.loadPublicKeyFromFile(publicKeyFile);
    }


    public String getPrivateKeyBase64() {
        return digitalSignature.keyToBase64(digitalSignature.getPrivateKey());
    }

    public String getPublicKeyBase64() {
        return digitalSignature.keyToBase64(digitalSignature.getPublicKey());
    }

    // Ký dữ liệu
    public String signData(String data) throws Exception {
        return digitalSignature.signDataBase64(data);
    }

//    public byte[] signData(byte[] data) throws Exception {
//        return digitalSignature.signData(data);
//    }

    // Xác minh chữ ký
    public boolean verifySignature(String data, String signatureBase64) throws Exception {
        return digitalSignature.verifySignature(data, signatureBase64);
    }

}
