package Varhub.connector;

/**
 * 该接口定义了一些系统设置
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public interface SetUp {
    /**
     * 这是集合模式，开启后操作逻辑为集合模式，关闭后为哈希表模式
     * @param Model 开关
     */
    void setMode(boolean Model);

    /**
     * 这是严格模式，开启后达不成目的后就会抛异常，关闭后就会返回false或者null
     * @param Model 开关
     */
    void strictModel(boolean Model);

}
