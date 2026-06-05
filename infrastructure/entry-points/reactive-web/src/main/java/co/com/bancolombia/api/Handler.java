package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.*;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.addbranch.AddBranchUseCase;
import co.com.bancolombia.usecase.addproduct.AddProductUseCase;
import co.com.bancolombia.usecase.createfranchise.CreateFranchiseUseCase;
import co.com.bancolombia.usecase.deleteproduct.DeleteProductUseCase;
import co.com.bancolombia.usecase.gettopstockproducts.GetTopStockProductsUseCase;
import co.com.bancolombia.usecase.updatebranchname.UpdateBranchNameUseCase;
import co.com.bancolombia.usecase.updatefranchisename.UpdateFranchiseNameUseCase;
import co.com.bancolombia.usecase.updateproductname.UpdateProductNameUseCase;
import co.com.bancolombia.usecase.updateproductstock.UpdateProductStockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final AddBranchUseCase addBranchUseCase;
    private final AddProductUseCase addProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetTopStockProductsUseCase getTopStockProductsUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {

        return request.bodyToMono(CreateFranchiseRequest.class)
                .map(dto -> Franchise.builder()
                        .name(dto.name())
                        .build())
                .flatMap(createFranchiseUseCase::execute)
                .flatMap(franchise ->
                        ServerResponse.ok().bodyValue(franchise)
                );
    }

    public Mono<ServerResponse> addBranch(ServerRequest request) {

        String franchiseId = request.pathVariable("franchiseId");

        return request.bodyToMono(AddBranchRequest.class)
                .map(dto -> Branch.builder()
                        .name(dto.name())
                        .build())
                .flatMap(branch ->
                        addBranchUseCase.execute(
                                franchiseId,
                                branch
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response)
                );
    }

    public Mono<ServerResponse> addProduct(ServerRequest request) {

        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");

        return request.bodyToMono(AddProductRequest.class)
                .map(dto -> Product.builder()
                        .name(dto.name())
                        .stock(dto.stock())
                        .build())
                .flatMap(product ->
                        addProductUseCase.execute(
                                franchiseId,
                                branchId,
                                product
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {

        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");

        return request.bodyToMono(UpdateStockRequest.class)
                .flatMap(dto ->
                        updateProductStockUseCase.execute(
                                franchiseId,
                                branchId,
                                productId,
                                dto.stock()
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {

        return deleteProductUseCase.execute(
                        request.pathVariable("franchiseId"),
                        request.pathVariable("branchId"),
                        request.pathVariable("productId")
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> getTopStockProducts(
            ServerRequest request) {

        return ServerResponse.ok()
                .body(
                        getTopStockProductsUseCase.execute(
                                request.pathVariable(
                                        "franchiseId")
                        ),
                        TopStockProductResponse.class
                );
    }

    public Mono<ServerResponse> updateFranchiseName(
            ServerRequest request) {

        String franchiseId =
                request.pathVariable("franchiseId");

        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(dto ->
                        updateFranchiseNameUseCase.execute(
                                franchiseId,
                                dto.name()
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response)
                );
    }

    public Mono<ServerResponse> updateBranchName(
            ServerRequest request) {

        String franchiseId =
                request.pathVariable("franchiseId");

        String branchId =
                request.pathVariable("branchId");

        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(dto ->
                        updateBranchNameUseCase.execute(
                                franchiseId,
                                branchId,
                                dto.name()
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response)
                );
    }

    public Mono<ServerResponse> updateProductName(
            ServerRequest request) {

        String franchiseId =
                request.pathVariable("franchiseId");

        String branchId =
                request.pathVariable("branchId");

        String productId =
                request.pathVariable("productId");

        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(dto ->
                        updateProductNameUseCase.execute(
                                franchiseId,
                                branchId,
                                productId,
                                dto.name()
                        )
                )
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response)
                );
    }
}
