package pember.jooqdemo.store.services

import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Record
import pember.jooqdemo.generated.tables.PurchaseOrder
import pember.jooqdemo.generated.tables.Shopper
import pember.jooqdemo.store.db.OrderTable
import pember.jooqdemo.store.db.ProductTable
import pember.jooqdemo.store.db.ShopperTable
import pember.jooqdemo.store.pogos.Order
import pember.jooqdemo.store.pogos.reports.MoneyByDate
import pember.jooqdemo.store.pogos.reports.ShopperTotal
import pember.jooqdemo.store.pogos.reports.ShopperWithCount

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

import static org.jooq.impl.DSL.count
import static org.jooq.impl.DSL.field
import static org.jooq.impl.DSL.sum
import static pember.jooqdemo.generated.tables.PurchaseOrder.PURCHASE_ORDER
import static pember.jooqdemo.generated.tables.Shopper.SHOPPER

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
class ReportService {
    @Inject DSLContext jooq

    @Inject OrderService orderService
    @Inject ShopperService shopperService
    @Inject ProductService productService

    Map overview() {

        Map overview = [:]
        overview["test"] = fetchShopper("Matt", "Murdock")
        overview["mostRecentOrder"] =  orderService.fetchMostRecent()
        overview["numProductsOnSale"] = productService.numberProductsUnder(3000)
        overview["totalOrders"] = orderCount()
        overview
    }

    int orderCount() {
        // fetchLazy() -> fetch n number of records at a time
        // This is horribly inefficient (selectCount anyone?) Don't laugh...
        List<Order> orders = jooq.select(OrderTable.ID).from(OrderTable.TABLE)
                .fetchSize(10)
                .fetchLazy() // fetch records in groups!
                .fetchInto(Order)

        // really should have just used selectCount()
        orders.size()


    }


    List<MoneyByDate> moneyMadeByDay() {
        Field day = field('day', Timestamp)

        jooq.select(field("date_trunc('day', date)", Timestamp).as("day"), sum(OrderTable.TOTAL_SALE).as('total'))
                .from(OrderTable.TABLE)
                .groupBy(day)
                .orderBy(day.desc())
        .fetchInto(MoneyByDate)
    }




    /**
     * A Leaderboard!!
     *
     * @return List of shoppers in order of the money they've spent with us, encapsulated in ShopperTotal Objects
     */
    List<ShopperTotal> topShoppers() {
        // todo: grab the shoppers names plus the total amount they've spent, sorted desc
        []
    }








    /*
        In case I forget how to code:
        jooq.select(SHOPPER.ID, SHOPPER.FIRST_NAME, SHOPPER.LAST_NAME,
                sum(PURCHASE_ORDER.TOTAL_SALE).as('total'))
            .from(SHOPPER)
            .join(PURCHASE_ORDER).on(SHOPPER.ID.equal(PURCHASE_ORDER.SHOPPER_ID))
            .groupBy(SHOPPER.ID)
            .orderBy(field('total').desc())
        .fetchInto(ShopperTotal)

     */







    void badQuery(String firstName, String lastName, boolean includeOrders) {
        // select s.id, s.email, count(po.id) from shopper s inner join purchase_order po on po.shopper_id = s.id
        // where last_name = 'Murdock' and first_name ='Matt' group by s.id;

        jooq.connection({ Connection connection ->


            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.id id, s.email email" +
                (includeOrders ? ", count(po.id) " : "") +
                "FROM shopper s " +
                (includeOrders ? "INNER JOIN purchase_order po on po.shopper_id = s.id" : "") +
                " WHERE s.first_name = ? and s.last_name = '" + lastName +"' group by s.id"
            )

            preparedStatement.setString(1, firstName)

            ResultSet resultSet = preparedStatement.executeQuery()

            while(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"))
                String email = resultSet.getString("e_mail")

                // ignore the fact that all we do is log the result
                log.info("I found user '$email ($id)'")
            }
        })

    }

    ShopperWithCount fetchShopper(String firstName, String lastName) {
        Field COUNT = field("count", String)


        ShopperWithCount results = jooq.select(SHOPPER.ID, SHOPPER.EMAIL,
                count(PURCHASE_ORDER.ID))
        .from(SHOPPER)
        .join(PURCHASE_ORDER)
                .on(PURCHASE_ORDER.SHOPPER_ID.equal(SHOPPER.ID))
        .where(SHOPPER.FIRST_NAME.equal(firstName))
        .and(SHOPPER.LAST_NAME.equal(lastName))
        .groupBy(SHOPPER.ID)
        .fetchAny().into(ShopperWithCount)

         log.info("Received ${results}")

         results
    }
}
