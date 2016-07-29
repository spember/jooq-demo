package pember.jooqdemo.store.services

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import pember.jooqdemo.store.db.ShopperTable
import pember.jooqdemo.store.pogos.Shopper

/**
 * @author Steve Pember
 */
@CompileStatic
@Slf4j
class ShopperService {
    @Inject DSLContext jooq

    List<Shopper> list() {
        jooq.select().from(ShopperTable.TABLE).fetchInto(Shopper)
    }
}
