import pember.jooqdemo.core.db.DatabaseConfig
import pember.jooqdemo.core.db.DatabaseModule
import pember.jooqdemo.store.StoreModule
import pember.jooqdemo.store.http.StoreUriMappings
import ratpack.func.Action
import ratpack.server.BaseDir

import static ratpack.groovy.Groovy.ratpack
import static ratpack.rx.RxRatpack.initialize

ratpack {
    serverConfig { config ->

        port(5050)

        config
                .baseDir(BaseDir.find()).onError(Action.throwException()).yaml("config.yaml")
                .onError(Action.noop()).yaml("/config_${System.getProperty("app.env")}.yaml")
                .onError(Action.noop()).yaml("/../../config-local.yaml")
                .env() // override local params with incoming Environment params
                .require("/db", DatabaseConfig)
    }
    bindings {
        initialize()
        module DatabaseModule
        module StoreModule
    }

    handlers {
        path {
            byMethod {
                get {
                    render "Ready"
                }
            }
        }

        prefix("store") {
            all chain(registry.get(StoreUriMappings))
        }
    }
}