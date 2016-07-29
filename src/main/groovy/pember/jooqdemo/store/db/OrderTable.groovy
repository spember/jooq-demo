package pember.jooqdemo.store.db

import org.jooq.Field
import org.jooq.Table
import pember.jooqdemo.core.db.JooqUtils

import java.sql.Timestamp

import static org.jooq.impl.DSL.field
import static org.jooq.impl.DSL.table

/**
 * @author Steve Pember
 */
class OrderTable {

    final static Table TABLE = table('purchase_order')

    final static Field ID = field('id', UUID)
    final static Field SHOPPER_ID = field('shopper_id', UUID)
    final static Field TOTAL_SALE = field('total_sale', Integer)
    final static Field DATE = field('date', Timestamp)
    final static Field DETAILS = field('details', JooqUtils.MAP_JSONB)
}
