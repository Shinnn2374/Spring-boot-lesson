package com.example.REST_controllers.repository;

import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.web.model.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderSpecification
{
    static Specification<Order> withFilter(OrderFilter filter)
    {
        return Specification.where(byProductName(filter.getProductName()))
                .and(byCostRange(filter.getMinCost(),filter.getMaxCost()))
                .and(byClientId(filter.getClientId()))
                .and(byCreatedAtdBefore(filter.getCreatedBefore()))
                .and(byUpdatedAtBefore(filter.getUpdatedBefore()));
    }

    static Specification<Order> byProductName(String productName)
    {
        return ((root, query, criteriaBuilder) -> {
            if (productName == null)
            {
                return null;
            }
            return criteriaBuilder.equal(root.get("productName"), productName);

        });
    }

    static Specification<Order> byCostRange(BigDecimal minCost, BigDecimal maxCost)
    {
        return ((root, query, criteriaBuilder) -> {
            if (minCost == null && maxCost == null) {
                return null;
            }
            if (minCost == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
            }
            if (maxCost == null)
            {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
            }
            return criteriaBuilder.between(root.get("cost"), minCost, maxCost);
        });
    }

    static Specification<Order> byClientId(Long clientId)
    {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null)
            {
                return null;
            }
            return criteriaBuilder.equal(root.get("clientId"), clientId);
        };
    }

    static Specification<Order> byCreatedAtdBefore(Instant createdAtdBefore)
    {
        return ((root, query, criteriaBuilder) -> {
            if (createdAtdBefore == null)
            {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtdBefore);
        });

    }

    static Specification<Order> byUpdatedAtBefore(Instant updatedAtBefore)
    {
        return ((root, query, criteriaBuilder) -> {
           if (updatedAtBefore == null)
           {
               return null;
           }
           return criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), updatedAtBefore);
        });
    }
}
