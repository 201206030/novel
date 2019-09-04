package xyz.zinglizingli.books.po;

import java.util.ArrayList;
import java.util.List;

public class CategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CategoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Byte value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Byte value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Byte value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Byte value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Byte value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Byte value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Byte> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Byte> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Byte value1, Byte value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Byte value1, Byte value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andGetUrlIsNull() {
            addCriterion("get_url is null");
            return (Criteria) this;
        }

        public Criteria andGetUrlIsNotNull() {
            addCriterion("get_url is not null");
            return (Criteria) this;
        }

        public Criteria andGetUrlEqualTo(String value) {
            addCriterion("get_url =", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlNotEqualTo(String value) {
            addCriterion("get_url <>", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlGreaterThan(String value) {
            addCriterion("get_url >", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlGreaterThanOrEqualTo(String value) {
            addCriterion("get_url >=", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlLessThan(String value) {
            addCriterion("get_url <", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlLessThanOrEqualTo(String value) {
            addCriterion("get_url <=", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlLike(String value) {
            addCriterion("get_url like", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlNotLike(String value) {
            addCriterion("get_url not like", value, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlIn(List<String> values) {
            addCriterion("get_url in", values, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlNotIn(List<String> values) {
            addCriterion("get_url not in", values, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlBetween(String value1, String value2) {
            addCriterion("get_url between", value1, value2, "getUrl");
            return (Criteria) this;
        }

        public Criteria andGetUrlNotBetween(String value1, String value2) {
            addCriterion("get_url not between", value1, value2, "getUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNull() {
            addCriterion("req_url is null");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNotNull() {
            addCriterion("req_url is not null");
            return (Criteria) this;
        }

        public Criteria andReqUrlEqualTo(String value) {
            addCriterion("req_url =", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotEqualTo(String value) {
            addCriterion("req_url <>", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThan(String value) {
            addCriterion("req_url >", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThanOrEqualTo(String value) {
            addCriterion("req_url >=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThan(String value) {
            addCriterion("req_url <", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThanOrEqualTo(String value) {
            addCriterion("req_url <=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLike(String value) {
            addCriterion("req_url like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotLike(String value) {
            addCriterion("req_url not like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlIn(List<String> values) {
            addCriterion("req_url in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotIn(List<String> values) {
            addCriterion("req_url not in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlBetween(String value1, String value2) {
            addCriterion("req_url between", value1, value2, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotBetween(String value1, String value2) {
            addCriterion("req_url not between", value1, value2, "reqUrl");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}