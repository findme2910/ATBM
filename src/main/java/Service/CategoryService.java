package Service;

import bean.Category;
import dao.CategoryDAO;

import java.util.List;

public class CategoryService {
    private static CategoryService instance;
    public static CategoryService getInstance(){
        if(instance ==null ) instance=new CategoryService();
        return instance;
    }
    public List<Category> getList(){
        return CategoryDAO.getList();
    }
    public List<Category> listCategory(String name,int index){
        return CategoryDAO.listCategory(name, index);
    }
    public boolean insertCategory(String nameCate){
        return CategoryDAO.insertCategory(nameCate);
    }
    public boolean deleteCategory(int idCate){
        return CategoryDAO.deleteCategory(idCate);
    }
    public boolean updateCategory(String categoryName,int idCategory){
       return CategoryDAO.updateCategory(categoryName, idCategory);
    }
    public boolean toggleCategoryStatus(int id, boolean disable) {
        return CategoryDAO.toggleCategoryStatus(id, disable);
    }
    public static void main(String[] args) {
        System.out.println(CategoryService.getInstance().listCategory("",0));
    }


}
