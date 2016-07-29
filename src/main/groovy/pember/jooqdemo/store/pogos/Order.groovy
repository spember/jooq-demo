package pember.jooqdemo.store.pogos

/**
 * @author Steve Pember
 */


class Order {

    UUID id
    // in db as 'shopper_id'
    UUID shopperId
    // in db as 'total_sale'
    Integer totalSale

    Date date

    Map details
}
