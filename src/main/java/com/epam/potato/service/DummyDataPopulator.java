package com.epam.potato.service;

import com.epam.potato.core.bag.PotatoBagEntity;
import com.epam.potato.core.supplier.SupplierEntity;
import com.epam.potato.db.bag.PotatoBagDAO;
import com.epam.potato.db.supplier.SupplierDAO;
import com.google.common.collect.ImmutableMultimap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DummyDataPopulator extends Task {

    private static final List<String> SUPPLIER_NAMES = List.of("De Coster", "Owel", "Patatas Ruben", "Yunnan Spices");
    private static final ThreadLocalRandom RND = ThreadLocalRandom.current();
    private static final int MAX_NUMBER_OF_POTATO_BAGS_PER_SUPPLIER = 5;

    private final SupplierDAO supplierDAO;
    private final PotatoBagDAO potatoBagDAO;
    private final SessionFactory sessionFactory;

    public DummyDataPopulator(SupplierDAO supplierDAO, PotatoBagDAO potatoBagDAO, SessionFactory sessionFactory) {
        super("dummyPopulator");

        this.supplierDAO = supplierDAO;
        this.potatoBagDAO = potatoBagDAO;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, PrintWriter printWriter) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            ManagedSessionContext.bind(session);
            Transaction transaction = session.beginTransaction();
            try {
                List<SupplierEntity> suppliers = persistDummySuppliers();

                suppliers.forEach(this::persistDummyPotatoBags);

                transaction.commit();
            }
            catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }
    }

    private List<SupplierEntity> persistDummySuppliers() {
        List<SupplierEntity> supplierEntities = SUPPLIER_NAMES.stream()
            .map(this::createSupplier)
            .collect(Collectors.toList());

        List<SupplierEntity> savedSupplierEntities = new ArrayList<>(supplierEntities.size());

        for (SupplierEntity supplierEntity : supplierEntities) {
            savedSupplierEntities.add(supplierDAO.create(supplierEntity));
        }

        return savedSupplierEntities;
    }

    private SupplierEntity createSupplier(String supplierName) {
        SupplierEntity supplierEntity = new SupplierEntity();

        supplierEntity.setName(supplierName);

        return supplierEntity;
    }

    private void persistDummyPotatoBags(SupplierEntity supplierEntity) {
        IntStream.range(0, RND.nextInt(MAX_NUMBER_OF_POTATO_BAGS_PER_SUPPLIER) + 1)
            .mapToObj(index -> createPotatoBag(supplierEntity))
            .forEach(potatoBagDAO::create);
    }

    private PotatoBagEntity createPotatoBag(SupplierEntity supplierEntity) {
        PotatoBagEntity potatoBagEntity = new PotatoBagEntity();

            potatoBagEntity.setNumberOfPotatoes(RND.nextInt(101));
            potatoBagEntity.setSupplierEntity(supplierEntity);
            potatoBagEntity.setPackedDate(getRandomDate());
            potatoBagEntity.setPrice(getRandomPryce());

            return potatoBagEntity;
    }

    private Date getRandomDate() {
        long minDay = LocalDate.of(2019, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = RND.nextLong(minDay, maxDay);

        int randomHour = RND.nextInt(8, 19);
        int randomMinute = RND.nextInt(0, 60);

        return Date.from(LocalDate.ofEpochDay(randomDay).atTime(randomHour, randomMinute).atZone(ZoneId.systemDefault()).toInstant());
    }

    private double getRandomPryce() {
        double price = (RND.nextDouble() * 49) + 1;

        return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
