package pember.jooqdemo.store

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import pember.jooqdemo.store.http.ApiUrlMappings
import pember.jooqdemo.store.http.StoreUriMappings
import pember.jooqdemo.store.services.DataInitializationService
import pember.jooqdemo.store.services.ProductService
import pember.jooqdemo.store.services.ReportService

/**
 * @author Steve Pember
 */
class StoreModule extends AbstractModule {

    @Override
    protected void configure() {

        [
                StoreUriMappings.class,
                ApiUrlMappings.class,

                ProductService.class,
                DataInitializationService.class,
                ReportService.class

        ].each {Class c -> bind(c).in(Scopes.SINGLETON)}

    }
}
