package pember.jooqdemo.store.services

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.jooq.Results
import pember.jooqdemo.store.db.OrderTable
import pember.jooqdemo.store.db.ProductTable
import pember.jooqdemo.store.pogos.Order

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
class OrderService {



    private DSLContext jooq

    @Inject ProductService(DSLContext jooq) {
        this.jooq = jooq
    }


    Order fetchMostRecent() {
        // fetchAny() -> grab the first record
        jooq.select(OrderTable.ID, OrderTable.SHOPPER_ID, OrderTable.TOTAL_SALE,
                OrderTable.DATE, OrderTable.DETAILS)
            .from(OrderTable.TABLE)
            .orderBy(OrderTable.DATE.desc()) // since we're only grabbing the first
            .fetchAny()
            .into(Order)
    }
}
