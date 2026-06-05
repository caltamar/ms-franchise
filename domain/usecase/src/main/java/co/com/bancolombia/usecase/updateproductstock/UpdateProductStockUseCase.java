package co.com.bancolombia.usecase.updateproductstock;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateProductStockUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(
            String franchiseId,
            String branchId,
            String productId,
            Integer stock) {

        return repository.findById(franchiseId)
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.FRANCHISE_NOT_FOUND
                                )
                        )
                )
                .flatMap(franchise ->
                        findBranch(franchise, branchId)
                                .flatMap(branch ->
                                        findProduct(branch, productId)
                                                .map(product -> {
                                                    product.setStock(stock);
                                                    return franchise;
                                                })
                                )
                )
                .flatMap(repository::save);
    }

    private Mono<Branch> findBranch(
            Franchise franchise,
            String branchId) {

        return Flux.fromIterable(franchise.getBranches())
                .filter(branch -> branch.getId().equals(branchId))
                .next()
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.BRANCH_NOT_FOUND
                                )
                        )
                );
    }

    private Mono<Product> findProduct(
            Branch branch,
            String productId) {

        return Flux.fromIterable(branch.getProducts())
                .filter(product -> product.getId().equals(productId))
                .next()
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.PRODUCT_NOT_FOUND
                                )
                        )
                );
    }
}
