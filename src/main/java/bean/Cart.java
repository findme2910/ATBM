package bean;

import lombok.Data;
import org.jdbi.v3.core.mapper.Nested;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Cart implements Serializable {
    private int id;
    @Nested("user")
    private User user;
    private Timestamp createdAt;

    @Nested("user")
    public User getUser() {
        return user;
    }

    @Nested("user")
    public void setUser(User user) {
        this.user = user;
    }
}
