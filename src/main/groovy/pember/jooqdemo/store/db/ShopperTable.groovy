package pember.jooqdemo.store.db

import org.jooq.Field
import org.jooq.Table
import org.jooq.impl.TableImpl

import static org.jooq.impl.DSL.field
import static org.jooq.impl.DSL.table

/**
 * @author Steve Pember
 */
class ShopperTable extends TableImpl {
    public static final ShopperTable TABLE = new ShopperTable();

    //static final Table TABLE = table("shopper")

    static final Field ID = field("id", UUID)
    static final Field FIRST_NAME = field("first_name", String)
    static final Field LAST_NAME = field("last_name", String)
    static final Field EMAIL = field("email", String)


    ShopperTable(String name) {
        super(name)
    }

    ShopperTable() {
        this("shopper");
    }

//    /**
//     * Create an aliased <code>public.event</code> table reference
//     */
//    public EventTable(java.lang.String alias) {
//        this(alias, EventTable.EVENT_TABLE);
//    }
}
