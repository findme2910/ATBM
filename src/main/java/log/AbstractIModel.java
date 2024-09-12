package log;

public abstract class AbstractIModel implements IModel{
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
