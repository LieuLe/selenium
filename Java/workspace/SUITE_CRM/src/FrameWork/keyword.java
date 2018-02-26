package FrameWork;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface keyword {	
	String Author() default "Thien Vo";
	String Date() default "Jan 1 2017";
	String Description() default "This is keyword";
	String[] Params() default "";
}
