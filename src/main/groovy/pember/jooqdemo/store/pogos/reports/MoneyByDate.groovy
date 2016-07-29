package pember.jooqdemo.store.pogos.reports

import groovy.transform.CompileStatic

import javax.persistence.Column

/**
 * A 'report' record containing a date plus the total money made that day
 *
 * @author Steve Pember
 */
@CompileStatic
class MoneyByDate {
    @Column(name="day")
    Date date

    @Column(name="total")
    Integer totalInCents
}
