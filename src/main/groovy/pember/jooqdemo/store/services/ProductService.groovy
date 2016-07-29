package pember.jooqdemo.store.services

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import pember.jooqdemo.generated.tables.records.ProductRecord
import pember.jooqdemo.store.db.ProductTable
import pember.jooqdemo.store.pogos.Product
import pember.jooqdemo.store.pogos.ProductAttributes

import static pember.jooqdemo.generated.tables.Product.PRODUCT

/**
 * @author Steve Pember
 */
@CompileStatic
@Slf4j
class ProductService {

    private DSLContext jooq

    @Inject ProductService(DSLContext jooq) {
        this.jooq = jooq
    }

    List<Product> list() {
        jooq.select(ProductTable.SKU, ProductTable.NAME, ProductTable.DESCRIPTION, ProductTable.PRICE_IN_CENTS, ProductTable.ATTRIBUTES ).from(ProductTable.TABLE).fetchInto(Product)
    }

    Integer numberProductsUnder(Integer priceInCents) {
        // fetchOne -> for when you know there will be only one result
        jooq.selectCount().from(ProductTable.TABLE)
                .where(ProductTable.PRICE_IN_CENTS.le(priceInCents))
                .fetchOne() // fetch the Record, then transform it
                .into(Integer)

    }

    boolean create() {

        ProductRecord productRecord = jooq.newRecord(PRODUCT)
        productRecord.sku = "TS123-a"
        productRecord.description = "This is a sample Product"
        productRecord.price = 19999
        productRecord.attributes = [size: ProductAttributes.SIZE_MEDIUM]
        productRecord.store()


    }

}
