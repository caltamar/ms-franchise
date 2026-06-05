package co.com.bancolombia.usecase.deleteproduct;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(
            String franchiseId,
            String branchId,
            String productId) {

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

                    boolean removed = branch.getProducts()
                            .removeIf(product ->
                                    product.getId().equals(productId));

                    if (!removed) {
                        throw new BusinessException(
                                BusinessErrorMessage.PRODUCT_NOT_FOUND);
                    }

                    return franchise;
                })
                .flatMap(repository::save);
    }
}
