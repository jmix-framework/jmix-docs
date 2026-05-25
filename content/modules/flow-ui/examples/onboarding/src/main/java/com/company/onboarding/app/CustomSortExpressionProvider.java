package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.data.impl.DefaultJpqlSortExpressionProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CustomSortExpressionProvider extends DefaultJpqlSortExpressionProvider {

    @Override
    public String getDatatypeSortExpression(MetaPropertyPath metaPropertyPath, boolean sortDirectionAsc) {
        if (metaPropertyPath.getMetaClass().getJavaClass().equals(Department.class)
                && "num".equals(metaPropertyPath.toPathString())) {
            return String.format("CAST({E}.%s BIGINT)", metaPropertyPath);
        }
        return String.format("{E}.%s", metaPropertyPath);
    }
}
