package com.renaissance.core.web.request;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Wilson
 */
public abstract class QuerydslSearch {

    private BooleanExpression booleanExpression;

    public abstract Predicate generatePredicate();

    /**
     * 添加单个条件判断语句
     *
     * @param newExpressionSupplier
     * @param value
     */
    protected void addExpression(Supplier<BooleanExpression> newExpressionSupplier, Object value) {
        if(isAvailable(value)) {
            addExpression(newExpressionSupplier.get());
        }
    }

    /**
     * 添加用or拼接的两个判断语句
     *
     * @param newExpressionSupplier1
     * @param value1
     * @param newExpressionSupplier2
     * @param value2
     */
    protected void addOrExpression(Supplier<BooleanExpression> newExpressionSupplier1, Object value1, Supplier<BooleanExpression> newExpressionSupplier2, Object value2) {
        if(isAvailable(value1) && isAvailable(value2)) {
            addExpression(newExpressionSupplier1.get().or(newExpressionSupplier2.get()));
        } else if(isAvailable(value1) && !isAvailable(value2)) {
            addExpression(newExpressionSupplier1.get());
        } else if(!isAvailable(value1) && isAvailable(value2)) {
            addExpression(newExpressionSupplier2.get());
        }
    }

    protected BooleanExpression getBooleanExpression() {
        return booleanExpression;
    }


    private void addExpression(BooleanExpression newExpression) {
        if(booleanExpression == null) {
            booleanExpression = newExpression;
        } else {
            booleanExpression = booleanExpression.and(newExpression);
        }
    }

    private boolean isAvailable(Object value) {
        if(value == null) {
            return false;
        }
        if(value instanceof String && StringUtils.isBlank((String)value)) {
            return false;
        }
        if(value instanceof Collection && ((Collection)value).isEmpty()) {
            return false;
        }
        return true;
    }
}
