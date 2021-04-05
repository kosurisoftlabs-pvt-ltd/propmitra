package com.portal.app.helper;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

public class PropertyCriteriaConsumer  implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    public PropertyCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root r) {
        super();
        this.predicate = predicate;
        this.builder = builder;
        this.r= r;
    }

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.or(predicate, builder
                    .greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.or(predicate, builder.lessThanOrEqualTo(
                    r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if(StringUtils.isNotBlank(param.getParentKey())) {
                if (r.get(param.getParentKey()).get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.or(predicate, builder.like(
                            r.get(param.getParentKey()).get(param.getKey()), "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.or(predicate, builder.equal(
                            r.get(param.getKey()), param.getValue()));
                }
            } else {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.or(predicate, builder.like(
                            r.get(param.getKey()), "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.or(predicate, builder.equal(
                            r.get(param.getKey()), param.getValue()));
                }
            }
        }
    }

    public Predicate getPredicate() {
        return predicate;
    }
}
