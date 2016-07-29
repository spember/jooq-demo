package pember.jooqdemo.store.http

import ratpack.groovy.handling.GroovyChainAction

/**
 * @author Steve Pember
 */
class StoreUriMappings extends GroovyChainAction {

    @Override
    void execute() throws Exception {
        prefix("api") {
            all chain(registry.get(ApiUrlMappings))
        }
    }
}
