package co.com.bancolombia.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/franchises",
                    beanClass = Handler.class,
                    beanMethod = "createFranchise"
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            Handler handler) {

        return route(
                POST("/api/franchises"),
                handler::createFranchise
        ).andRoute(
                POST("/api/franchises/{franchiseId}/branches"),
                handler::addBranch
        ).andRoute(
                POST("/api/franchises/{franchiseId}/branches/{branchId}/products"),
                handler::addProduct
        ).andRoute(
                PATCH("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock"),
                handler::updateStock
        ).andRoute(
                DELETE("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}"),
                handler::deleteProduct
        ).andRoute(
                GET("/api/franchises/{franchiseId}/top-stock-products"),
                handler::getTopStockProducts
        ).andRoute(
                PATCH("/api/franchises/{franchiseId}"),
                handler::updateFranchiseName
        ).andRoute(
                PATCH("/api/franchises/{franchiseId}/branches/{branchId}"),
                handler::updateBranchName
        ).andRoute(
                PATCH("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}"),
                handler::updateProductName
        );
    }
}
