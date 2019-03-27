package com.epam.potato.service.supplier;

import com.epam.potato.api.supplier.Supplier;
import com.epam.potato.db.supplier.SupplierDAO;
import com.epam.potato.service.supplier.exception.InvalidSupplierException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierService {

    private final SupplierDAO supplierDAO;
    private final SupplierTransformer supplierTransformer = new SupplierTransformer();

    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public Supplier create(Supplier supplier) {
        return Optional.ofNullable(supplier)
            .map(supplierTransformer::transform)
            .map(supplierDAO::create)
            .map(supplierTransformer::transform)
            .orElseThrow(() ->  new InvalidSupplierException("Invalid entity: " + supplier));
    }

    public List<Supplier> getSuppliers() {
        return supplierDAO.findAll().stream()
            .map(supplierTransformer::transform)
            .collect(Collectors.toList());
    }

}
