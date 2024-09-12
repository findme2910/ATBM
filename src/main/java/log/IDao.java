package log;

public interface IDao<T extends IModel> {
    boolean selectModel(int id);
    boolean insertModel(AbsModel model,String ip, int level, String address);
    boolean deleteModel(AbsModel model,String ip, int level, String address);
    boolean updateModel(AbsModel modelBf,AbsModel modelAt, String ip, int level, String address);
    boolean login(AbsModel model,String action,String ip, int level, String address);
    boolean signUp(String em,String action,String ip, int level, String address);

}
