package co.com.bancolombia.usecase.updateproductstock;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import lombok.RequiredArgsConstructor;
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
                .switchIfEmpty(Mono.error(
                        new BusinessException(
                                BusinessErrorMessage.FRANCHISE_NOT_FOUND)))
                .map(franchise -> {

                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() ->
                                    new BusinessException(
                                            BusinessErrorMessage.BRANCH_NOT_FOUND));

                    Product product = branch.getProducts()
                            .stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() ->
                                    new BusinessException(
                                            BusinessErrorMessage.PRODUCT_NOT_FOUND));

                    product.setStock(stock);

                    return franchise;
                })
                .flatMap(repository::save);
    }
}
