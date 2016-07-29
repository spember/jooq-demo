package pember.jooqdemo.store.pogos

import javax.persistence.Column


class Product {
    @Column(name = 'sku')
    String sku

    @Column(name ='name')
    String name

    @Column(name ='description')
    String description

    @Column(name ='price')
    Integer price

    @Column(name ='attributes')
    Map attributes
}
