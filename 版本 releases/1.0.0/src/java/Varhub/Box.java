package Varhub;

import Varhub.connector.StockpilePlus;
import Varhub.error.DataIsNull;
import Varhub.error.DataTypeIsNull;
import Varhub.error.RepeatingVariable;
import Varhub.error.Unopened;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个实现类实现了Stockpile所有方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public class Box implements StockpilePlus {
    private static volatile Box instance;

    private boolean setModedata;

    private boolean strictModedata;

    private final ConcurrentHashMap<Object,ConcurrentHashMap<String,Object>> map= new ConcurrentHashMap<>();

    /**
     * 先判断数据类型是否存在，如果存在就创建出新哈希表，然后和数据类型绑定，然后存入数值<br>
     * 如果为严格模式下，存入同数据类型下的数据名称会抛异常<br>
     * 如果关闭集合模式，会直接走putObject方法
     * @param name 数据名称
     * @param value 数据或对象
     */
    @Override
    public Boolean addObject(String name, Object value) {
        if(!setModedata){
            putObject(name,value);
            return true;
        }
        if(map.get(value)!=null){
            if(map.get(value).containsKey(name)){
                if(!strictModedata){
                    return false;
                }
                throw new RepeatingVariable("在同一个数据类型下新增的数据变量名重复");
            }else{
                PUTObject(name,value);
                return true;
            }
        }else{
            PUTObject(name,value);
            return true;
        }
    }

    /**
     * 首先先判断数据类型是否存在，如果不存在就抛异常<br>
     * 如果数据不存在，也会直接抛异常<br>
     * 如果关闭严格模式获取不到对象会返回false<br>
     * 如果关闭集合模式，会直接走putObject方法
     * @param name 数据名称
     * @param value 数据或对象
     * @return true or false
     */
    @Override
    public Boolean setObject(String name, Object value) {
        if(!setModedata){
            putObject(name,value);
            return true;
        }
        if(map.get(value.getClass())!=null){
            if(map.get(value.getClass()).containsKey(name)){
                PUTObject(name,value);
                return true;
            }else{
                if(!strictModedata){
                    return false;
                }
                throw new DataIsNull("你想更改的数据并不存在");
            }
        }else{
            if(!strictModedata){
                return false;
            }
            throw new DataTypeIsNull("你更改的数据类型并不存在");
        }
    }


    /**
     * 首先先判断数据类型是否存在，如果不存在会抛出异常<br>
     * 然后查找数据，如果找不到就会抛出异常<br>
     * 关闭严格模式后，将会返回null<br>
     * @param name 数据名称
     * @param type 数据或对象
     * @return Object
     */
    @Override
    public Object getObject(String name, Object type) {
        if(map.get(type)!=null){
            if(map.get(type).containsKey(name)){
                return map.get(type).get(name);
            }else{
                if(!strictModedata){
                    return null;
                }
                throw new DataIsNull("找不到数据");
            }
        }else{
            if(!strictModedata){
                return null;
            }
            throw new DataTypeIsNull("找不到数据类型");
        }
    }


    /**
     * 和getObject方法思路一样，但是不会抛异常，不做具体解析
     * @param name 数据名称
     * @param type 数据或对象
     * @return true or false
     */
    @Override
    public Boolean removeObject(String name, Object type) {
        if(map.get(type)!=null){
            if(map.get(type).containsKey(name)){
                map.get(type).remove(name);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * 更改集合模式方法
     * @param Model 开关
     */
    @Override
    public void setMode(boolean Model) {
        setModedata=Model;
    }

    /**
     * 更改集合模式方法
     * @param Model 开关
     */
    @Override
    public void strictModel(boolean Model) {
        strictModedata=Model;
    }

    /**
     * 这是从Stockpile接口实现的<br>
     * 在集合模式下调用会抛异常，反之就可以正常使用
     * @param name 数据名称
     * @param value 数据或对象
     */
    @Override
    public void putObject(String name, Object value) {
        if(!setModedata){
            map.computeIfAbsent(value,k->new ConcurrentHashMap<>());
            map.get(value).put(name,value);
        }else{
            throw new Unopened("处于集合模式中，禁止调用该方法");
        }
    }

    /**
     * 这个方法机仅仅只是给addObject和setObject调用而已
     * @param name 数据名称
     * @param value 数据或对象
     */
    private void PUTObject(String name, Object value) {
        map.computeIfAbsent(value.getClass(),k->new ConcurrentHashMap<>());
        map.get(value.getClass()).put(name,value);
    }

    //以下为单例模式，不需要看

    private Box(){
        if (instance != null) {
            throw new IllegalStateException("该类为单例模式,禁止多次获取对象");
        }
        setMode(true);
        strictModel(true);
    }

    public static Box getBox() {
        if (instance == null) {
            synchronized (Box.class) {
                if (instance == null) {
                    instance = new Box();
                }
            }
        }
        return instance;
    }

    protected Object readResolve() {
        return getBox();
    }

}
