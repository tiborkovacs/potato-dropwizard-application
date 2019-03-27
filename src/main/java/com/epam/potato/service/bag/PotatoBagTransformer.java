package com.epam.potato.service.bag;

import com.epam.potato.api.bag.PotatoBag;
import com.epam.potato.core.bag.PotatoBagEntity;
import com.epam.potato.core.supplier.SupplierEntity;
import com.epam.potato.db.supplier.SupplierDAO;
import com.epam.potato.service.supplier.exception.SupplierNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class PotatoBagTransformer {

    private final SupplierDAO supplierDAO;

    PotatoBagTransformer(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    PotatoBagEntity transform(PotatoBag potatoBag) {
        PotatoBagEntity potatoBagEntity = new PotatoBagEntity();

        potatoBagEntity.setNumberOfPotatoes(potatoBag.getNumberOfPotatoes());
        potatoBagEntity.setSupplierEntity(getSupplierEntity(potatoBag.getSupplierName()));
        potatoBagEntity.setPackedDate(getPackedDate(potatoBag.getPackedDateTime()));
        potatoBagEntity.setPrice(potatoBag.getPrice());

        return potatoBagEntity;
    }

    PotatoBag transform(PotatoBagEntity potatoBagEntity) {
        return new PotatoBag.Builder()
            .withId(potatoBagEntity.getId())
            .withNumberOfPotatoes(potatoBagEntity.getNumberOfPotatoes())
            .withSupplierName(potatoBagEntity.getSupplierEntity().getName())
            .withPackedDateTime(getPackedDateTime(potatoBagEntity.getPackedDate()))
            .withPrice(potatoBagEntity.getPrice())
            .build();
    }

    private SupplierEntity getSupplierEntity(String supplierName) {
        return supplierDAO.findByName(supplierName)
            .orElseThrow(() -> new SupplierNotFoundException("Supplier doesn't exist with name: " + supplierName));
    }

    private Date getPackedDate(LocalDateTime packedDateTime) {
        return Date.from(packedDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime getPackedDateTime(Date packedDate) {
        return LocalDateTime.ofInstant(packedDate.toInstant(), ZoneId.systemDefault());
    }

}
