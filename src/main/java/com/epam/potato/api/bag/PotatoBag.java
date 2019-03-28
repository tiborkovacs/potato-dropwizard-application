package com.epam.potato.api.bag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonDeserialize(builder = PotatoBag.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, value = "id")
public class PotatoBag {

    private final UUID id;
    @Min(1)
    @Max(100)
    private final int numberOfPotatoes;
    @NotNull
    private final String supplierName;
    @NotNull
    private final LocalDateTime packedDateTime;
    @Min(1)
    @Max(50)
    private final double price;

    private PotatoBag(Builder builder) {
        id = builder.id;
        numberOfPotatoes = Optional.ofNullable(builder.numberOfPotatoes).orElse(0);
        supplierName = builder.supplierName;
        packedDateTime = builder.packedDateTime;
        price = Optional.ofNullable(builder.price).orElse(0D);
    }

    public UUID getId() {
        return id;
    }

    public int getNumberOfPotatoes() {
        return numberOfPotatoes;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public LocalDateTime getPackedDateTime() {
        return packedDateTime;
    }

    public double getPrice() {
        return price;
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

        private UUID id;
        private Integer numberOfPotatoes;
        private String supplierName;
        private LocalDateTime packedDateTime;
        private Double price;

        public Builder withId(UUID id) {
            this.id = id;

            return this;
        }

        public Builder withNumberOfPotatoes(Integer numberOfPotatoes) {
            this.numberOfPotatoes = numberOfPotatoes;

            return this;
        }

        public Builder withSupplierName(String supplierName) {
            this.supplierName = supplierName;

            return this;
        }

        public Builder withPackedDateTime(LocalDateTime packedDateTime) {
            this.packedDateTime = packedDateTime;

            return this;
        }

        public Builder withPrice(Double price) {
            this.price = price;

            return this;
        }

        public PotatoBag build() {
            return new PotatoBag(this);
        }

    }

}
