package Varhub.error;

/**
 * @author Akemi0Homura
 */
public class DataTypeIsNull extends RuntimeException{
    /**
     * 数据类型并不存在
     */
    public DataTypeIsNull(String s){
        super(s);
    }
}
