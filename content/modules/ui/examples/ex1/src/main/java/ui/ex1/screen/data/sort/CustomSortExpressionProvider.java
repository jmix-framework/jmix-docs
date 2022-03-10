package ui.ex1.screen.data.sort;

import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.data.impl.DefaultJpqlSortExpressionProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ui.ex1.entity.Order;

// tag::provider[]
@Primary
@Component("sample_JpqlSortExpressionProvider")
public class CustomSortExpressionProvider
        extends DefaultJpqlSortExpressionProvider {

    @Override
    public String getDatatypeSortExpression(MetaPropertyPath metaPropertyPath, boolean sortDirectionAsc) {
        if (metaPropertyPath.getMetaClass().getJavaClass().equals(Order.class)
                && "num".equals(metaPropertyPath.toPathString())) {
            return String.format("CAST({E}.%s BIGINT)", metaPropertyPath);
        }
        return String.format("{E}.%s", metaPropertyPath);
    }

}
// end::provider[]
