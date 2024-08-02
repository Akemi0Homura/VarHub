package Varhub.connector;

/**
 * 该接口定义了哈希表最基础的方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public interface Repository extends SetUp {
    /**
     * 这个方法为添加数据<br>
     * 如果你添加的数据在同类同属性下重名，那么将会抛出异常终止程序<br>
     * 可以在barn传递false关闭严格模式
     * @param name 数据名称
     * @param value 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return true or false
     */
    Boolean addObject(String name,Object value,Object object,boolean barn);

    /**
     * 这个方法是修改数据<br>
     * 如果修改的数据不存在就会抛出异常终止程序<br>
     * 可以在barn传递false关闭严格模式
     * @param name 数据名称
     * @param value 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return true or false
     */
    Boolean setObject(String name,Object value,Object object,boolean barn);

    /**
     * 这个方法是获取数据<br>
     * 如果获取的数据不存在就会抛出异常终止程序<br>
     * 可以在barn传递false关闭严格模式
     * @param name 数据名称
     * @param type 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return true or false
     */
    Object getObject(String name, Object type,Object object,boolean barn);

    /**
     * 删除数据
     * @param name 数据名称
     * @param type 数据类型
     * @param object 类
     * @return true or false
     */
    Boolean removeObject(String name,Object type,Object object);

    void putObject(String name,Object value,Object object);
}
