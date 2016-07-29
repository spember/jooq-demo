package pember.jooqdemo.store.http

import com.google.inject.Inject
import pember.jooqdemo.store.services.ProductService
import pember.jooqdemo.store.services.ReportService
import pember.jooqdemo.store.services.ShopperService
import ratpack.groovy.handling.GroovyChainAction

import static ratpack.jackson.Jackson.json

/**
 * @author Steve Pember
 */
class ApiUrlMappings extends GroovyChainAction {
    @Inject ProductService productService
    @Inject ShopperService shopperService
    @Inject ReportService reportService

    @Override
    void execute() throws Exception {
        path("shoppers") {
            byMethod {
                get {
                    render json(shopperService.list())
                }
            }
        }

        path("products") {
            byMethod {
                get {
                    render json(productService.list())
                }
            }
        }
        prefix("reports") {
            path("overview") {
                byMethod {
                    get {
                        render json(reportService.overview())
                    }
                }
            }

            path("dateTotals") {
                byMethod{
                    get {
                        render json(reportService.moneyMadeByDay())
                    }
                }
            }

            path('leaderboard') {
                byMethod {
                    get {
                        render json(reportService.topShoppers())
                    }
                }
            }
        }


    }


}
