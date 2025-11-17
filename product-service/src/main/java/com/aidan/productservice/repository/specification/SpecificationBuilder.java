package com.aidan.productservice.repository.specification;

import com.aidan.productservice.controller.dto.ProductFilter;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SpecificationBuilder {

    private SpecificationBuilder() {}

    public static <T> Specification<T> fromFilter(ProductFilter filter) {
        if (filter == null) return (root, query, cb) -> cb.conjunction();

        List<Specification<T>> specs = new ArrayList<>();

        if (filter.getName() != null && !filter.getName().isBlank()) {
            Operation op = Operation.fromString(filter.getNameOperation().name());
            specs.add(buildSpec("productName", op, filter.getName()));
        }

        boolean useOr = filter.getCombineOperator() == ProductFilter.CombineOperator.OR;
        return combineSpecs(specs, useOr);
    }

    private static <T> Specification<T> combineSpecs(List<Specification<T>> specs, boolean useOr) {
        if (specs.isEmpty()) return (root, query, cb) -> cb.conjunction();
        return useOr ? specs.stream().reduce(Specification::or).get()
                : specs.stream().reduce(Specification::and).get();
    }

    private enum Operation { EQ, LIKE, GT, LT, GTE, LTE;
        static Operation fromString(String s) {
            try { return Operation.valueOf(s.toUpperCase()); }
            catch (Exception e) { return EQ; }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Specification<T> buildSpec(String field, Operation op, String value) {
        return (root, query, cb) -> {
            Path<?> p = root.get(field);
            Class<?> type = p.getJavaType();

            switch (op) {
                case LIKE:
                    if (!type.equals(String.class)) return cb.conjunction();
                    return cb.like(cb.lower((Path<String>) p), "%" + value.toLowerCase() + "%");

                case EQ:
                    return cb.equal(p, castToType(type, value));

                case GT:
                    return buildComparablePredicate(cb, p, type, value, Comparison.GT);

                case LT:
                    return buildComparablePredicate(cb, p, type, value, Comparison.LT);

                case GTE:
                    return buildComparablePredicate(cb, p, type, value, Comparison.GTE);

                case LTE:
                    return buildComparablePredicate(cb, p, type, value, Comparison.LTE);

                default:
                    return cb.equal(p, castToType(type, value));
            }
        };
    }

    private enum Comparison { GT, LT, GTE, LTE }

    @SuppressWarnings("unchecked")
    private static <Y extends Comparable<? super Y>> jakarta.persistence.criteria.Predicate
    buildComparablePredicate(jakarta.persistence.criteria.CriteriaBuilder cb,
                             Path<?> p, Class<?> type, String value, Comparison comp) {
        Y casted = (Y) castToType(type, value);

        Path<Y> path = (Path<Y>) p;

        return switch (comp) {
            case GT -> cb.greaterThan(path, casted);
            case LT -> cb.lessThan(path, casted);
            case GTE -> cb.greaterThanOrEqualTo(path, casted);
            case LTE -> cb.lessThanOrEqualTo(path, casted);
        };
    }

    private static Object castToType(Class<?> type, String value) {
        try {
            if (type.equals(String.class)) return value;
            if (type.equals(Integer.class)) return Integer.valueOf(value);
            if (type.equals(Long.class)) return Long.valueOf(value);
            if (type.equals(Double.class)) return Double.valueOf(value);
            if (type.equals(Float.class)) return Float.valueOf(value);
            if (type.equals(Boolean.class)) return Boolean.valueOf(value);
            if (type.equals(UUID.class)) return UUID.fromString(value);
            if (type.equals(LocalDate.class)) return LocalDate.parse(value);
            if (type.equals(LocalDateTime.class)) return LocalDateTime.parse(value);
        } catch (DateTimeParseException | IllegalArgumentException ignored) {}
        return value;
    }
}
