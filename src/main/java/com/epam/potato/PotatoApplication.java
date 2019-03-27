package com.epam.potato;

import com.epam.potato.core.bag.PotatoBagEntity;
import com.epam.potato.core.supplier.SupplierEntity;
import com.epam.potato.db.bag.PotatoBagDAO;
import com.epam.potato.db.supplier.SupplierDAO;
import com.epam.potato.resources.bag.PotatoBagResource;
import com.epam.potato.resources.supplier.SupplierResource;
import com.epam.potato.service.DummyDataPopulator;
import com.epam.potato.service.bag.PotatoBagService;
import com.epam.potato.service.supplier.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.hibernate.SessionFactory;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PotatoApplication extends Application<PotatoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PotatoApplication().run(args);
    }

    private final HibernateBundle<PotatoConfiguration> hibernateBundle = new HibernateBundle<>(SupplierEntity.class, PotatoBagEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(PotatoConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "Potato";
    }

    @Override
    public void initialize(final Bootstrap<PotatoConfiguration> bootstrap) {
        super.initialize(bootstrap);

        bootstrap.addBundle(hibernateBundle);

        bootstrap.addBundle(new MigrationsBundle<PotatoConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(PotatoConfiguration configuration) {
            return configuration.getDataSourceFactory();
            }
        });

        ObjectMapper objectMapper = bootstrap.getObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        bootstrap.setObjectMapper(objectMapper);
    }

    @Override
    public void run(final PotatoConfiguration configuration, final Environment environment) {
        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();

        final SupplierDAO supplierDAO = new SupplierDAO(sessionFactory);
        final SupplierService supplierService = new SupplierService(supplierDAO);
        environment.jersey().register(new SupplierResource(supplierService));

        final PotatoBagDAO potatoBagDAO = new PotatoBagDAO(sessionFactory);
        final PotatoBagService potatoBagService = new PotatoBagService(potatoBagDAO, supplierDAO);
        environment.jersey().register(new PotatoBagResource(potatoBagService));

        environment.admin().addTask(new DummyDataPopulator(supplierDAO, potatoBagDAO, sessionFactory));
    }

}
