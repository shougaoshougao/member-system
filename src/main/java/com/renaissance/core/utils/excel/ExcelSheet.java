/**
 * 
 */
package com.renaissance.core.utils.excel;

import java.lang.annotation.*;

/**
 * @author Wilson
 *
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {

    String value() default "";
    
}
