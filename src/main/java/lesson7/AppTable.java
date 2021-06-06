package lesson7;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface AppTable {
    String name();
}
