/**
 * 
 */
package com.renaissance.core.utils.excel;

import java.lang.annotation.*;

/**
 * @author Wilson
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelHeader {

    String value() default "";
    
}
