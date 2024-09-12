package log;

public abstract class AbsModel implements IModel{
    public abstract String getTable();
    public abstract String beforeData();
    public abstract String afterData();

}
