package com.epam.potato.db.supplier;

import com.epam.potato.core.supplier.SupplierEntity;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;

public class SupplierDAO extends AbstractDAO<SupplierEntity> {

    public SupplierDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<SupplierEntity> findByName(String supplierName) {
        Query<SupplierEntity> query = namedQuery("com.epam.potato.core.supplier.SupplierEntity.findByName");

        query.setParameter("name", supplierName);

        return Optional.ofNullable(query.getSingleResult());
    }

    public SupplierEntity create(SupplierEntity supplierEntity) {
        return persist(supplierEntity);
    }

    @SuppressWarnings("unchecked")
    public List<SupplierEntity> findAll() {
        return list(namedQuery("com.epam.potato.core.supplier.SupplierEntity.findAll"));
    }

}
