package Varhub.connector;

/**
 * 该接口定义了哈希表最基础的方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public interface Stockpile {

    /**
     * 这是一个添加数据和修改数据的方法<br>
     * 如果数据名称不存在就添加，如果存在就会修改名称<br>
     * 不同类型的数据可以重名
     * @param name 数据名称
     * @param value 数据或对象
     */
    void putObject(String name,Object value);

    /**
     * 获取同类型和同名称的数据
     * @param name 数据名称
     * @param type 数据或对象
     * @return 对象
     */
    Object getObject(String name, Object type);

    /**
     * 这个是删除方法，删除指定属性类型下的名称数据
     * @param name 数据名称
     * @param type 数据或对象
     * @return true or false
     */
    Boolean removeObject(String name,Object type);

}

