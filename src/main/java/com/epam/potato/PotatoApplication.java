package com.epam.potato;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PotatoApplication extends Application<PotatoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PotatoApplication().run(args);
    }

    @Override
    public String getName() {
        return "Potato";
    }

    @Override
    public void initialize(final Bootstrap<PotatoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final PotatoConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
