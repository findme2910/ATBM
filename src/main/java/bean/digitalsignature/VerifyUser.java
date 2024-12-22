package bean.digitalsignature;

import exceptions.DigitalSignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.Hash;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class VerifyUser implements Serializable {
    private int id;
    private Date createAt;
    private String privateKey;

    public VerifyUser(int id, Date createAt, String privateKey) throws DigitalSignatureException {
        this.id = id;
        this.createAt = createAt;
        this.privateKey = Hash.hash(privateKey);
    }
}
