package Varhub.connector;

/**
 * 该接口定义了哈希表最基础的方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public interface StockpilePlus extends Stockpile,SetUp {

    /**
     * 集合模式下的方法<br>
     * 添加数据失败会抛异常<br>
     * 关闭严格模式就会返回false<br>
     * 关闭集合模式就会直接走putObject方法<br>
     * @param name 数据名称
     * @param value 数据
     * @return false or true
     */
    Boolean addObject(String name,Object value);

    /**
     * 集合模式下的方法<br>
     * 修改数据失败会抛异常<br>
     * 关闭严格模式就会返回false<br>
     * 关闭集合模式就会直接走putObject方法<br>
     * @param name 数据名称
     * @param value 数据
     * @return false or true
     */
    Boolean setObject(String name,Object value);

    /**
     * 获取数据失败会抛异常<br>
     * 关闭严格模式就会返回false<br>
     * @param name 数据名称
     * @param object 数据或对象
     * @return Object
     */
    @Override
    Object getObject(String name, Object object);
}
