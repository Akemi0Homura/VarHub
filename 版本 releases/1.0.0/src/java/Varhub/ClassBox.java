package Varhub;

import Varhub.connector.Repository;
import Varhub.error.DataTypeIsNull;
import Varhub.error.RepeatingVariable;
import Varhub.error.Unopened;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个实现类实现了Repository所有方法
 * @author Homura Akemi0Homura
 * @version 1.0
 */
public class ClassBox implements Repository {
    private static volatile ClassBox instance;

    private boolean setModedata;

    private final ConcurrentHashMap<Object,ConcurrentHashMap<Object,ConcurrentHashMap<String,Object>>> map= new ConcurrentHashMap<>();

    /**
     * 先判类是否存在，如果存在就创建出新哈希表，然后和类绑定<br>
     * 然后判断数据类型是否存在，存在就再新教哈希表，和类绑定<br>
     * 然后再判断数据名称是否相同,不是的话就存入数据<br>
     * 如果为严格模式下，存入同类同数据类型下的数据名称会抛异常<br>
     * 如果关闭集合模式，会直接走putObject方法
     * @param name 变量名名称
     * @param value 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return false or true
     */
    @Override
    public Boolean addObject(String name, Object value, Object object, boolean barn) {
        if(!setModedata){
            putObject(name,value,object);
            return true;
        }
        if(map.get(object)!=null){
            if(map.get(object).get(value)!=null){
                if(map.get(object).get(value).containsKey(name)){
                    if(barn){
                        throw new RepeatingVariable("在同一个类下数据类型下新增的数据变量名重复");
                    }else{
                        return false;
                    }
                }else{
                    PUTObject(name,value,object);
                    return true;
                }
            }else{
                PUTObject(name,value,object);
                return true;
            }
        }else{
            PUTObject(name,value,object);
            return true;
        }
    }

    /**
     * 一层一层查询数据，查到最后就修改数据<br>
     * 严格模式下，有一环查找不到就抛异常，关闭后就返回false<br>
     * 关闭集合模式会直接走putObject方法
     * @param name 变量名名称
     * @param value 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return false or true
     */
    @Override
    public Boolean setObject(String name, Object value, Object object, boolean barn) {
        if(!setModedata){
            putObject(name,value,object);
            return true;
        }
        if(map.get(object)!=null){
            if(map.get(object).get(value.getClass())!=null){
                if(map.get(object).get(value.getClass()).containsKey(name)){
                    PUTObject(name,value,object);
                    return true;
                }else{
                    if(barn){
                        throw new DataTypeIsNull("你想更改的数据并不存在");
                    }else{
                        return false;
                    }
                }
            }else{
                if(barn){
                    throw new DataTypeIsNull("你更改的数据类型并不存在");
                }else{
                    return false;
                }
            }
        }else{
            if(barn){
                throw new DataTypeIsNull("你更改的类中的变量并不存在");
            }else{
                return false;
            }
        }
    }

    /**
     * 获取数据，一层层找下拉后返回数据<br>
     * 严格模式下中途没找到就会抛异常，关闭后就返回null
     * @param name 变量名名称
     * @param type 数据
     * @param object 类
     * @param barn 严格模式开关
     * @return Object
     */
    @Override
    public Object getObject(String name, Object type, Object object, boolean barn) {
        if(map.get(object)!=null){
            if(map.get(object).get(type)!=null){
                if(map.get(object).get(type).containsKey(name)){
                    return map.get(object).get(type).get(name);
                }else{
                    if(barn){
                        throw new DataTypeIsNull("找不到数据");
                    }else{
                        return null;
                    }
                }
            }else{
                if(barn){
                    throw new DataTypeIsNull("找不到数据类型");
                }else{
                    return null;
                }
            }
        }else{
            if(barn){
                throw new DataTypeIsNull("找不到类");
            }else{
                return null;
            }
        }
    }

    /**
     * 删除数据，每层找不到数据都会返回null，找到后删除数据并且返回true
     * @param name 数据名称
     * @param type 数据类型
     * @param object 类
     * @return false or true
     */
    @Override
    public Boolean removeObject(String name,Object type, Object object) {
        if(map.get(object)!=null){
            if(map.get(object).get(type)!=null){
                if(map.get(object).get(type).containsKey(name)){
                    map.get(object).get(type).remove(name);
                    return true;
                }else{
                    return false;
                }
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
        setModedata = Model;
    }

    /**
     * 由于ClassBox中，可以精确控制每一个方法的严格模式，因此该方法没有意义
     * @param Model 开关
     */
    @Override
    public void strictModel(boolean Model) {

    }

    /**
     * 这个方法机仅仅只是给addObject和setObject调用而已
     * @param name 数据名称
     * @param value 数据或对象
     */
    public void PUTObject(String name, Object value,Object object) {
        map.computeIfAbsent(object,k->new ConcurrentHashMap<>()).computeIfAbsent(value.getClass(),k->new ConcurrentHashMap<>());
        map.get(object).get(value.getClass()).put(name,value);
    }

    /**
     * 在集合模式下调用会抛异常，反之就可以正常使用
     * @param name 数据名称
     * @param value 数据或对象
     */
    @Override
    public void putObject(String name, Object value,Object object) {
        if(setModedata){
            throw new Unopened("处于集合模式中，禁止调用该方法");
        }
        map.computeIfAbsent(object,k->new ConcurrentHashMap<>()).computeIfAbsent(value.getClass(),k->new ConcurrentHashMap<>());
        map.get(object).get(value.getClass()).put(name,value);
    }

    //以下为单例模式，不需要看

    private ClassBox(){
        if (instance != null) {
            throw new IllegalStateException("该类为单例模式,禁止多次获取对象");
        }
        setMode(true);
        strictModel(true);
    }

    public static ClassBox getClassBox() {
        if (instance == null) {
            synchronized (ClassBox.class) {
                if (instance == null) {
                    instance = new ClassBox();
                }
            }
        }
        return instance;
    }

    protected Object readResolve() {
        return getClassBox();
    }

}
