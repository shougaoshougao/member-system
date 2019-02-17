/**
 * 
 */
package com.renaissance.core.web.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wilson
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HttpAuthentication {
	
	/**
     * Flag to determine whether to ignore current request or not
     * 
     * @return
     */
    boolean ignore() default false;

    /**
     * authorities allowed to request
     * @return
     */
    String[] allowAuthorities() default {};

}
