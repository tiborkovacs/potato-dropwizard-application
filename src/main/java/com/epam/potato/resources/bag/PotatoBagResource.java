package com.epam.potato.resources.bag;

import com.epam.potato.api.bag.PotatoBag;
import com.epam.potato.service.bag.PotatoBagService;
import com.epam.potato.service.bag.exception.UnableToCreatePotatoBagException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.hibernate.UnitOfWork;
import java.util.List;
import java.util.OptionalInt;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/api/bags")
@Produces(MediaType.APPLICATION_JSON)
public class PotatoBagResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagResource.class);

    private final PotatoBagService potatoBagService;

    public PotatoBagResource(PotatoBagService potatoBagService) {
        this.potatoBagService = potatoBagService;
    }

    @GET
    @UnitOfWork
    public List<PotatoBag> getPotatoBags(@QueryParam("count") OptionalInt count) {
        return potatoBagService.getPotatoBags(count.orElse(3));
    }

    @POST
    @UnitOfWork
    public PotatoBag createPotatoBag(@Valid PotatoBag potatoBag) {
        try {
            return potatoBagService.create(potatoBag);
        }
        catch (Exception exception) {
            LOGGER.error("Failed to create entity", exception);

            throw new UnableToCreatePotatoBagException("Unable to create entity: " + potatoBag + ", error cause: " + exception.getMessage());
        }
    }

}
