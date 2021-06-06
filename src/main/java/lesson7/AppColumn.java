package lesson7;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AppColumn {
    boolean isPrimaryKey() default false;
}
