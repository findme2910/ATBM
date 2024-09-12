package bean;

import log.AbsModel;
import lombok.Data;

@Data
public class Category extends AbsModel {
    private int id;
    private String nameCategory;
    private int status;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Category() {
    }

    public Category(int id, String nameCategory) {
        this.id = id;
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }

    @Override
    public String getTable() {
        return "Doanh mục";
    }

    @Override
    public String beforeData() {
        return this.toString();
    }

    @Override
    public String afterData() {
        return this.toString();
    }
}