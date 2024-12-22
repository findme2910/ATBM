package Service.DigitalSignature;

import bean.User;
import bean.digitalsignature.Keys;
import bean.digitalsignature.OrderSign;
import bean.digitalsignature.SignedOrder;
import bean.digitalsignature.VerifyUser;
import dao.digitalsignature.IDAO;
import dao.digitalsignature.KeyDAO;
import dao.digitalsignature.SignedOrderDAO;
import exceptions.DigitalSignatureException;
import utils.DigitalSignature;

import static utils.Hash.hash;


public class DigitalSignatureService {
    private DigitalSignature digitalSignature;
    private IDAO<Keys> keysDAO;
    private IDAO<SignedOrder> signedOrderDAO;

    public DigitalSignatureService() {
        this.digitalSignature = new DigitalSignature();
        this.keysDAO = new KeyDAO();
        this.signedOrderDAO = new SignedOrderDAO();

    }

    public String getPrivateKey() {
        return digitalSignature.keyToBase64(digitalSignature.getPrivateKey());
    }

    public String getPublicKey() {
        return digitalSignature.keyToBase64(digitalSignature.getPublicKey());
    }

    public void genKey() throws DigitalSignatureException {
        digitalSignature.generateKeyPair();
    }
    public void verifyUser(User user, String privateKey) throws DigitalSignatureException {
        Keys key = keysDAO.get(user.getId());
        String publicKey = key.getPublicKey();
        //Load public key
        digitalSignature.loadPublicKey(publicKey);
        //Lấy thông tin ngừoi dùng đã được hash
        String userInfor = new VerifyUser(user.getId(), user.getCreateAt(), privateKey).toString();
//Xác thực ngừoi dùng
        System.out.println("Xác thực ngừoi dùng");
        System.out.println(digitalSignature.verifySignature(userInfor, key.getUserSignature()));

    }



    //Ký đơn hàng
    public void signOrder(OrderSign orderSign, String privateKey, User user) throws DigitalSignatureException {

        this.digitalSignature.loadPrivateKey(privateKey);//load private key
        System.out.println("load private key");
        System.out.println(digitalSignature.keyToBase64(digitalSignature.getPrivateKey()));
        //Hash đơn hàng
        String hashOrder = hash(orderSign.toString());
        int keyId = keysDAO.get(user.getId()).getId();
        //Ký đơn hàng
        System.out.println("Ký đơn hàng");
        String signedOrder = digitalSignature.signDataBase64(hashOrder);
        System.out.println(signedOrder);
        System.out.println("Insert đơn hàng");
        signedOrderDAO.insert(new SignedOrder(orderSign.getId(), keyId, signedOrder, 1));

        System.out.println("Ký thành công!");

    }




    //Xác thực đơn hàng
    public void verifySignature(OrderSign orderSign, User user) throws DigitalSignatureException {
        //Lấy public key đã lưu trữ
        String publicKey = keysDAO.get(user.getId()).getPublicKey();
        this.digitalSignature.loadPublicKey(publicKey);//load public key
//        Hash đơn hàng
        String hashOrder = hash(orderSign.toString());
        //Lấy đơn hàng đã ký
        String signedOrder = signedOrderDAO.get(orderSign.getId()).getSignOrder();
        //Kiểm tra
        boolean verify = digitalSignature.verifySignature(hashOrder, signedOrder);
//        String message =verfify?""
        System.out.println(verify);
    }

    public void saveKeyWithUser(User user, String privateKey, String publicKey) throws DigitalSignatureException {

        digitalSignature = new DigitalSignature();
        digitalSignature.loadPublicKey(publicKey);
        digitalSignature.loadPrivateKey(privateKey);
        //Chữ ký định danh ngừoi dùng : id + thời gian tại tài khoản + hash private key
        String userSignature = digitalSignature.signDataBase64(new VerifyUser(user.getId(), user.getCreateAt(), privateKey).toString());
        //insert chỉ lấy userId, publicKey và userSignature
        keysDAO.insert(new Keys(0, user.getId(), publicKey, null, true, userSignature));

    }

    public boolean isExitsKeys(User user) {
        return keysDAO.get(user.getId()) != null;
    }
//    public static void main(String[] args) {
//        DigitalSignatureService digitalSignatureService = new DigitalSignatureService();
//    }

}
