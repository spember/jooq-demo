package pember.jooqdemo.store.pogos.reports

import javax.persistence.Column

/**
 * @author Steve Pember
 */
class ShopperTotal {

    @Column(name="first_name")
    String firstName

    @Column(name="last_name")
    String lastName


    @Column(name="total")
    Integer total
}
