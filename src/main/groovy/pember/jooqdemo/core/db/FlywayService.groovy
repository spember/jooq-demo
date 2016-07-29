package pember.jooqdemo.core.db

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

/**
 * @author Steve Pember
 */
@CompileStatic
@Slf4j
class FlywayService implements Service {

    private DatabaseConfig config
    Flyway flyway

    @Inject FlywayService(DatabaseConfig c) {
        config = c
    }

    @Override
    void onStart(StartEvent event) throws Exception {
        log.info("Starting Flyway Service")
        // Create the Flyway instance
        flyway = new Flyway();

        // Point it to the database
        flyway.setDataSource(config.url, config.username, config.password);

        // Start the migration
        flyway.migrate();
    }
}