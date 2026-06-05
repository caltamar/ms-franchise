package co.com.bancolombia.usecase.gettopstockproducts;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.TopStockProduct;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;

@RequiredArgsConstructor
public class GetTopStockProductsUseCase {

    private final FranchiseRepository repository;

    public Flux<TopStockProduct> execute(String franchiseId) {

        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new BusinessException(
                                BusinessErrorMessage.FRANCHISE_NOT_FOUND)))
                .flatMapMany(franchise ->
                        Flux.fromIterable(franchise.getBranches())
                                .map(branch -> {

                                    Product product = branch.getProducts()
                                            .stream()
                                            .max(Comparator.comparing(Product::getStock))
                                            .orElse(null);

                                    if (product == null) {
                                        return null;
                                    }

                                    return TopStockProduct.builder()
                                            .branchName(branch.getName())
                                            .productName(product.getName())
                                            .stock(product.getStock())
                                            .build();
                                })
                                .filter(Objects::nonNull)
                );
    }
}
