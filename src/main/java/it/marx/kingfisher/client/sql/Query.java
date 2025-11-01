package it.marx.kingfisher.client.sql;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Query {

    private List<String> fields = new ArrayList<>();
    private List<WhereCondition> whereConditions = new ArrayList<>();
    private Integer limit;
    private Integer offset;
    private String sortField;
    private SortDirection sortDirection;

    public enum SortDirection {
        ASC("asc"),
        DESC("desc");

        private final String value;

        SortDirection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum WhereOperator {
        EQUALS("="),
        NOT_EQUALS("!="),
        CONTAINS("~"),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        GREATER_OR_EQUAL(">="),
        LESS_OR_EQUAL("<=");

        private final String value;

        WhereOperator(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum LogicalOperator {
        AND("&"),
        OR("|");

        private final String value;

        LogicalOperator(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Data
    public static class WhereCondition {
        private String field;
        private WhereOperator operator;
        private Object value;
        private LogicalOperator logicalOperator;

        public WhereCondition(String field, WhereOperator operator, Object value) {
            this.field = field;
            this.operator = operator;
            this.value = value;
            this.logicalOperator = null;
        }

        public WhereCondition(String field, WhereOperator operator, Object value, LogicalOperator logicalOperator) {
            this.field = field;
            this.operator = operator;
            this.value = value;
            this.logicalOperator = logicalOperator;
        }
    }

    // Metodi di utilità per aggiungere condizioni WHERE
    public Query addWhereEquals(String field, Object value) {
        whereConditions.add(new WhereCondition(field, WhereOperator.EQUALS, value));
        return this;
    }

    public Query addWhereEqualsIn(String field, List<?> values) {
        whereConditions.add(new WhereCondition(field, WhereOperator.EQUALS, values));
        return this;
    }

    public Query addWhereContains(String field, Object value) {
        whereConditions.add(new WhereCondition(field, WhereOperator.CONTAINS, value));
        return this;
    }

    public Query addWhereNotEquals(String field, Object value) {
        whereConditions.add(new WhereCondition(field, WhereOperator.NOT_EQUALS, value));
        return this;
    }

    public Query addWhereGreaterThan(String field, Integer value) {
        whereConditions.add(new WhereCondition(field, WhereOperator.GREATER_THAN, value));
        return this;
    }

    public Query addWhereLessThan(String field, Integer value) {
        whereConditions.add(new WhereCondition(field, WhereOperator.LESS_THAN, value));
        return this;
    }

    public Query addWhereCondition(String field, WhereOperator operator, Object value) {
        whereConditions.add(new WhereCondition(field, operator, value));
        return this;
    }

    public Query addWhereCondition(String field, WhereOperator operator, Object value,
            LogicalOperator logicalOperator) {
        whereConditions.add(new WhereCondition(field, operator, value, logicalOperator));
        return this;
    }

    public Query and() {
        if (!whereConditions.isEmpty()) {
            whereConditions.get(whereConditions.size() - 1).setLogicalOperator(LogicalOperator.AND);
        }
        return this;
    }

    public Query or() {
        if (!whereConditions.isEmpty()) {
            whereConditions.get(whereConditions.size() - 1).setLogicalOperator(LogicalOperator.OR);
        }
        return this;
    }

    public Query addField(String field) {
        fields.add(field);
        return this;
    }

    public Query addFields(String... fields) {
        for (String field : fields) {
            this.fields.add(field);
        }
        return this;
    }

    public Query setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Query setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Query setSortField(String sortField, SortDirection direction) {
        this.sortField = sortField;
        this.sortDirection = direction;
        return this;
    }

    public String buildQuery() {
        StringBuilder output = new StringBuilder();

        // FIELDS
        output.append("fields ");
        if (fields.isEmpty()) {
            output.append("*");
        } else {
            for (int i = 0; i < fields.size(); i++) {
                output.append(fields.get(i));
                if (i < fields.size() - 1) {
                    output.append(", ");
                }
            }
        }
        output.append("; ");

        // WHERE
        if (!whereConditions.isEmpty()) {
            output.append("where ");
            for (int i = 0; i < whereConditions.size(); i++) {
                WhereCondition condition = whereConditions.get(i);
                output.append(condition.getField())
                        .append(" ")
                        .append(condition.getOperator().getValue())
                        .append(" ");

                // Gestione lista di valori
                if (condition.getValue() instanceof List) {
                    List<?> values = (List<?>) condition.getValue();
                    output.append("(");
                    for (int j = 0; j < values.size(); j++) {
                        Object val = values.get(j);
                        if (val instanceof String) {
                            output.append("\"").append(val).append("\"");
                        } else {
                            output.append(val);
                        }
                        if (j < values.size() - 1) {
                            output.append(", ");
                        }
                    }
                    output.append(")");
                } else {
                    // Aggiungi valore con quote per stringhe
                    if (condition.getValue() instanceof String) {
                        output.append("\"").append(condition.getValue()).append("\"");
                    } else {
                        output.append(condition.getValue());
                    }
                }

                // Aggiungi operatore logico se presente e non è l'ultima condizione
                if (i < whereConditions.size() - 1 && condition.getLogicalOperator() != null) {
                    output.append(" ")
                            .append(condition.getLogicalOperator().getValue())
                            .append(" ");
                }
            }
            output.append("; ");
        }

        // SORT
        if (sortField != null && sortDirection != null) {
            output.append("sort ")
                    .append(sortField)
                    .append(" ")
                    .append(sortDirection.getValue())
                    .append("; ");
        }

        // LIMIT
        if (limit != null) {
            output.append("limit ").append(limit).append("; ");
        }

        // OFFSET
        if (offset != null) {
            output.append("offset ").append(offset).append("; ");
        }

        return output.toString().trim();
    }
}
