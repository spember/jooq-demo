package pember.jooqdemo.store.services

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.jooq.Insert
import org.jooq.InsertValuesStep5
import org.jooq.InsertValuesStepN
import pember.jooqdemo.core.db.JooqService
import pember.jooqdemo.store.db.OrderTable
import pember.jooqdemo.store.db.ProductTable
import pember.jooqdemo.store.db.ShopperTable
import pember.jooqdemo.store.pogos.Product
import pember.jooqdemo.store.pogos.ProductAttributes
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.inject.Inject

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
class DataInitializationService implements Service {

    private ProductService productService
    private DSLContext jooq

    @Inject
    DataInitializationService(DSLContext jooq, ProductService ps) {
        this.jooq = jooq
        this.productService = ps
    }


    @Override
    void onStart(StartEvent event) throws Exception {
        int count = jooq.selectCount().from(ShopperTable.TABLE).fetchOne().into(Integer)
        log.info("Found ${count} shoppers")
        if (count == 0) {
            generateData()
        }

    }


    void generateData() {
        // create people
        int peopleCount = jooq.insertInto(ShopperTable.TABLE, ShopperTable.ID, ShopperTable.FIRST_NAME, ShopperTable.LAST_NAME, ShopperTable.EMAIL)
                .values(UUID.randomUUID(), "Test", "Testington", "test@test.com")
                .values(UUID.randomUUID(), "Matt", "Murdock", "daredevil@gmail.com")
                .values(UUID.randomUUID(), "Jane", "Doe", "jdoe@yahoo.com")
                .values(UUID.randomUUID(), "Alice", "Black", "blackhat@gmail.com")
        .execute()

        log.info("Created ${peopleCount} shoppers")
        List<UUID> shopperUuids = jooq.select(ShopperTable.ID).from(ShopperTable.TABLE).fetchInto(UUID)


        int productCount = jooq.insertInto(ProductTable.TABLE,
                ProductTable.SKU, ProductTable.NAME, ProductTable.DESCRIPTION, ProductTable.PRICE_IN_CENTS, ProductTable.ATTRIBUTES )
            .values("SP-0001", "Small Widget",  "This is a small Widget. It's black", 2500, [color: ProductAttributes.COLOR_BLACK, size: ProductAttributes.SIZE_SMALL, category: ProductAttributes.CATEGORY_ELECTRONICS])
            .values("SP-0002", "Medium Widget", "This is a medium Widget. It's green", 2500, [color: ProductAttributes.COLOR_GREEN, size: ProductAttributes.SIZE_MEDIUM, category: ProductAttributes.CATEGORY_ELECTRONICS])
            .values("SP-0003", "Large Widget",  "This is a large Widget. It's blue", 2500, [color: ProductAttributes.COLOR_BLUE, size: ProductAttributes.SIZE_SMALL, category: ProductAttributes.CATEGORY_ELECTRONICS])

            .values("TS-0001", "Gr8Conf US t-shirt", "The *Official* Gr8conf US tshirt in size medium", 10000, [color: ProductAttributes.COLOR_RED, size:ProductAttributes.SIZE_MEDIUM, category: ProductAttributes.CATEGORY_CLOTHING])
            .values("TS-0002", "Gr8Conf EU t-shirt", "The *Official* Gr8conf EU tshirt in size medium", 10000, [color: ProductAttributes.COLOR_BLUE, size:ProductAttributes.SIZE_MEDIUM, category: ProductAttributes.CATEGORY_CLOTHING])
        .execute()

        log.info("Created ${productCount} products")

        List<Product> products = productService.list()
        //this is a good example of working with CompileStatic

        //Now, generate random orders. Say... 1000
        //get random person
        // choose 1-3 products in a set. Quantity
        InsertValuesStep5 insert = jooq.insertInto(OrderTable.TABLE, OrderTable.ID, OrderTable.SHOPPER_ID, OrderTable.DATE, OrderTable.TOTAL_SALE, OrderTable.DETAILS)

        1000.times {
            insert = generateOrder(insert, shopperUuids, products)
        }
        int orderCount = insert.execute()
        log.info("Created ${orderCount} orders")
    }

    private InsertValuesStep5 generateOrder(InsertValuesStep5 insert, List<UUID> shopperUuids, List<Product> products) {
        Map<String, Integer> results = chooseRandomProducts(products)
        int total = sumOrder(results, products)
        insert.values(UUID.randomUUID(), shopperUuids[Math.random()*shopperUuids.size() as int], new Date() - (Math.random()*365 as int), total, results)
    }

    private Map<String, Integer> chooseRandomProducts(List<Product> products) {
        Set<Product> chosen = []
        ((Math.random()*3 as Integer) + 1).times {
            chosen.add(products[(products.size()*Math.random()) as int])
        }

        Map<String, Integer> results = [:]
        chosen.asList().each {Product p ->
            results[p.sku] = (Math.random()*9 as int)+1
        }
        results
    }

    private Integer sumOrder(Map<String, Integer> order, List<Product> products) {
        Integer sum = 0
        products.each {Product p->
            if (order.containsKey(p.sku)) {
                sum += (p.price*order[p.sku])
            }
        }
        sum
    }
}
