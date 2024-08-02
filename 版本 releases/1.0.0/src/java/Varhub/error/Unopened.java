package Varhub.error;
/**
 * @author Akemi0Homura
 */
public class Unopened extends RuntimeException{
    /**
     * 未开启
     */
    public Unopened(final String message) {
        super(message);
    }
}
