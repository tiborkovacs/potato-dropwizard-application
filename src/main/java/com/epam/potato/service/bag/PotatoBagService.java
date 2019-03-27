package com.epam.potato.service.bag;

import com.epam.potato.api.bag.PotatoBag;
import com.epam.potato.core.bag.PotatoBagEntity;
import com.epam.potato.db.bag.PotatoBagDAO;
import com.epam.potato.db.supplier.SupplierDAO;
import com.epam.potato.service.bag.exception.InvalidPotatoBagException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PotatoBagService {

    private final PotatoBagDAO potatoBagDAO;
    private final PotatoBagTransformer potatoBagTransformer;

    public PotatoBagService(PotatoBagDAO potatoBagDAO, SupplierDAO supplierDAO) {
        this.potatoBagDAO = potatoBagDAO;
        potatoBagTransformer = new PotatoBagTransformer(supplierDAO);
    }

    public PotatoBag create(PotatoBag potatoBag) {
        return Optional.ofNullable(potatoBag)
            .map(potatoBagTransformer::transform)
            .map(potatoBagDAO::create)
            .map(potatoBagTransformer::transform)
            .orElseThrow(() -> new InvalidPotatoBagException("Unable to create entity: " + potatoBag));
    }

    public List<PotatoBag> getPotatoBags(int count) {
        List<PotatoBagEntity> potatoBagEntities;
        if (count < 0) {
            potatoBagEntities = potatoBagDAO.findAll();
        }
        else if (count == 0) {
            potatoBagEntities = Collections.emptyList();
        }
        else {
            potatoBagEntities = potatoBagDAO.findAll(count);
        }

        return potatoBagEntities.stream()
            .map(potatoBagTransformer::transform)
            .collect(Collectors.toList());
    }

}
