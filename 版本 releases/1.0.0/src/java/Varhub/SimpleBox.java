package Varhub;

import Varhub.connector.Stockpile;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个实现类实现了Stockpile所有方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public class SimpleBox implements Stockpile {

    private static volatile SimpleBox instance;

    private final ConcurrentHashMap<Object,ConcurrentHashMap<String,Object>> map= new ConcurrentHashMap<>();

    /**
     * 这个方法先判断数据类型是否存在，如果存在就创建出新哈希表，然后和数据类型绑定，
     * 然后存入数值或者修改数值
     * @param name 数据名称
     * @param value 数据或对象
     */
    @Override
    public void putObject(String name, Object value) {
        map.computeIfAbsent(value.getClass(),k->new ConcurrentHashMap<>());
        map.get(value.getClass()).put(name,value);
    }

    /**
     * 首先先判断数据类型是否存在，如果存在再用三元运算来返回数据<br>
     * 如果不先判断数据类型是否存在的话，会报错
     * @param name 数据名称
     * @param type 数据或对象
     * @return Object
     */
    @Override
    public Object getObject(String name, Object type) {
        if(map.get(type)!=null){
            return map.get(type).get(name)!=null?map.get(type).get(name):null;
        }else{
            return null;
        }
    }

    /**
     * 和getObject方法思路一样，不做具体解析
     * @param name 数据名称
     * @param type 数据或对象
     * @return true or false
     */
    @Override
    public Boolean removeObject(String name, Object type) {
        if(map.get(type.getClass())!=null){
            map.get(type.getClass()).remove(name);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "simpleBox{\n" +
                "map=" + map +
                "\n}";
    }

    //以下为单例模式，不需要看

    public static SimpleBox getSimpleBox() {
        if (instance == null) {
            synchronized (SimpleBox.class) {
                if (instance == null) {
                    instance = new SimpleBox();
                }
            }
        }
        return instance;
    }

    private SimpleBox(){
        if (instance != null) {
            throw new IllegalStateException("该类为单例模式,禁止多次获取对象");
        }
    }

    protected Object readResolve() {
        return getSimpleBox();
    }

}

