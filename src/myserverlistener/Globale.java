package myserverlistener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Some crude synchronization primitives to make logging work as expected
 * @author Ivan
 */
public class Globale {
    public static AtomicBoolean LoggingUsed = new AtomicBoolean(false);
}
