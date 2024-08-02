package Varhub.error;

/**
 * @author Akemi0Homura
 */
public class DataIsNull extends RuntimeException{
    /**
     * 数据并不存在
     */
    public DataIsNull(String s){
        super(s);
    }
}
