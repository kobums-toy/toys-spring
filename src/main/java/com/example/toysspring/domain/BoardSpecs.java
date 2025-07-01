package com.example.toysspring.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BoardSpecs {
    public enum SearchKey {
        ID("BoardId"),
        TITLE("BoardTitle"),
        CONTENT("BoardContent");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Specification<Board> searchWith(Map<SearchKey, Object> searchKeyword) {
        return (Specification<Board>) ((root, query, builder) -> {
                List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
                return builder.and(predicate.toArray(new Predicate[0]));
            });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword, Root<Board> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        for (SearchKey key : searchKeyword.keySet()) {
            switch (key) {
            case ID:
            case TITLE:
            case CONTENT:
                predicate.add(builder.like(root.get(key.value), "%" + searchKeyword.get(key) + "%"));
                break;
            }
        }
        return predicate;
    }
}
