package com.epam.potato.api.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = Supplier.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, value = "id")
public class Supplier {

    @Min(0)
    private final long id;
    @NotNull
    private final String name;

    private Supplier(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {

        private long id;
        private String name;

        public Builder withId(long id) {
            this.id = id;

            return this;
        }

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Supplier build() {
            return new Supplier(this);
        }

    }

}
