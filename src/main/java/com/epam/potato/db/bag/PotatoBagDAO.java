package com.epam.potato.db.bag;

import com.epam.potato.core.bag.PotatoBagEntity;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.stream.Collectors;

public class PotatoBagDAO extends AbstractDAO<PotatoBagEntity> {

    public PotatoBagDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PotatoBagEntity create(PotatoBagEntity potatoBagEntity) {
        return persist(potatoBagEntity);
    }

    @SuppressWarnings("unchecked")
    public List<PotatoBagEntity> findAll() {
        return list(namedQuery("com.epam.potato.core.bag.PotatoBagEntity.findAll"));
    }

    public List<PotatoBagEntity> findAll(int count) {
        return findAll().stream()
            .limit(count)
            .collect(Collectors.toList());
    }

}
