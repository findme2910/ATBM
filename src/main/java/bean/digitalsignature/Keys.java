package bean.digitalsignature;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class Keys implements Serializable {
    private int id;
    private int userId;
    private String publicKey;
    private Timestamp createdAt;
    private boolean status;
    private String userSignature;

    public Keys() {
    }




    public Keys(int id, int userId, String publicKey, Timestamp createdAt, boolean status, String userSignature) {
        this.id = id;
        this.userId = userId;
        this.publicKey = publicKey;
        this.createdAt = createdAt;
        this.status = status;
        this.userSignature = userSignature;
    }
    public Keys(int userId, String publicKey) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Keys{" +
                "id=" + id +
                ", userId=" + userId +
                ", publicKey='" + publicKey + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", userSignature='" + userSignature + '\'' +
                '}' + "\r\n";
    }
}
