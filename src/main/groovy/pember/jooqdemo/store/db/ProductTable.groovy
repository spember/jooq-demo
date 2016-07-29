package pember.jooqdemo.store.db

import org.jooq.Field
import org.jooq.Table
import pember.jooqdemo.core.db.JooqUtils

import static org.jooq.impl.DSL.field
import static org.jooq.impl.DSL.table

/**
 * @author Steve Pember
 */
class ProductTable {
    static final Table TABLE = table("product")

    static final Field SKU = field("sku", String)
    static final Field NAME = field("name", String)
    static final Field DESCRIPTION = field("description", String)
    static final Field PRICE_IN_CENTS = field("price", Integer)
    static final Field ATTRIBUTES = field('attributes', JooqUtils.MAP_JSONB)
}
