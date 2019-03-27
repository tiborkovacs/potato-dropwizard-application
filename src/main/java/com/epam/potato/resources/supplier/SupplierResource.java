package com.epam.potato.resources.supplier;

import com.epam.potato.api.supplier.Supplier;
import com.epam.potato.service.supplier.SupplierService;
import com.epam.potato.service.supplier.exception.UnableToCreateSupplierException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.hibernate.UnitOfWork;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/suppliers")
@Produces(MediaType.APPLICATION_JSON)
public class SupplierResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierResource.class);

    private final SupplierService supplierService;

    public SupplierResource(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GET
    @UnitOfWork
    public List<Supplier> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @POST
    @UnitOfWork
    public Supplier createSupplier(Supplier supplier) {
        try {
            return supplierService.create(supplier);
        }
        catch (Exception exception) {
            LOGGER.error("Failed to create entity", exception);

            throw new UnableToCreateSupplierException("Unable to create entity: " + supplier + ", error cause: " + exception.getMessage());
        }
    }

}
