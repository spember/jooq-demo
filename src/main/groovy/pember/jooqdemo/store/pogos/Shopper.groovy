package pember.jooqdemo.store.pogos

import groovy.transform.CompileStatic

import javax.persistence.Column

/**
 * @author Steve Pember
 */
@CompileStatic
class Shopper {

    @Column(name="id")
    UUID id

    @Column(name="first_name")
    String firstName

    @Column(name="last_name")
    String lastName

    @Column(name="email")
    String email
}
