package smart.cst.pwc.needs;

public interface NeedClick {

    void onAddClick(int position, int count);

    void onMinusClick(int position, int count);

    void onAvailable(boolean isAvailable, int position);

    void onDistance(int position, String value);
}
