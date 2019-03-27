package com.epam.potato.service.supplier;

import com.epam.potato.api.supplier.Supplier;
import com.epam.potato.core.supplier.SupplierEntity;

class SupplierTransformer {

    SupplierEntity transform(Supplier supplier) {
        SupplierEntity supplierEntity = new SupplierEntity();

        supplierEntity.setName(supplier.getName());

        return supplierEntity;
    }

    Supplier transform(SupplierEntity supplierEntity) {
        return new Supplier.Builder()
            .withId(supplierEntity.getId())
            .withName(supplierEntity.getName())
            .build();
    }

}
