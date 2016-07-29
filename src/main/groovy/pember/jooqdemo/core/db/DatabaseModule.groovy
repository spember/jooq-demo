package pember.jooqdemo.core.db

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
import org.jooq.DSLContext

/**
 * @author Steve Pember
 */
class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FlywayService.class).in(Scopes.SINGLETON)
        bind(JooqService.class).in(Scopes.SINGLETON)

    }

    @Provides
    @Singleton
    public DSLContext jooq(JooqService jooqService) {
        jooqService.create()
    }
}
