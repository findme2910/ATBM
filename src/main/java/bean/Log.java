package bean;


import log.IModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements IModel {
    private int id;
    private String ip;
    private String action;
    private String level;
    private String address;
    private String previousValue;
    private String currentValue;
    private Timestamp create_at;
    private Timestamp update_at;


    @Override
    public String getTable() {
        return null;
    }

    @Override
    public String beforeData() {
        return null;
    }

    @Override
    public String afterData() {
        return null;
    }
}
