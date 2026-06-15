package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.data.persistence.JpqlSortExpressionSupplier;
import io.jmix.data.persistence.SortExpressionContext;
import org.jspecify.annotations.Nullable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// tag::custom-sort-expression-supplier[]
@Component
@Order(100)
public class CustomSortExpressionSupplier implements JpqlSortExpressionSupplier {

    @Override
    @Nullable
    public String getDatatypeSortExpression(SortExpressionContext context) {
        if (context.metaPropertyPath().getMetaClass().getJavaClass().equals(Department.class)
                && "num".equals(context.metaPropertyPath().toPathString())) {
            return String.format("CAST({E}.%s BIGINT)", context.metaPropertyPath());
        }
        return null;
    }
}
// end::custom-sort-expression-supplier[]
