package Varhub.error;

/**
 * @author Akemi0Homura
 */
public class RepeatingVariable extends RuntimeException {
    /**
     * 重复
     */
    public RepeatingVariable(final String message) {
        super(message);
    }
}
