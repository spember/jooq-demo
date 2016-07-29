package pember.jooqdemo.store.pogos.reports

import groovy.transform.CompileStatic
import groovy.transform.ToString
import pember.jooqdemo.generated.tables.Shopper

import javax.persistence.Column

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
class ShopperWithCount  {

    @Column(name="id")
    UUID id

    @Column(name="email")
    String email

    @Column(name="count")
    Integer count = 0
}
