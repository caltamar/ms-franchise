package co.com.bancolombia.usecase.gettopstockproducts;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GetTopStockProductsUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private GetTopStockProductsUseCase useCase;

    @Test
    void shouldReturnTopProductPerBranch() {

        Product low = Product.builder()
                .name("Papas")
                .stock(10)
                .build();

        Product high = Product.builder()
                .name("Hamburguesa")
                .stock(50)
                .build();

        Branch branch = Branch.builder()
                .id("B1")
                .name("Sucursal Cali")
                .products(List.of(low, high))
                .build();

        Franchise franchise = Franchise.builder()
                .id("F1")
                .branches(List.of(branch))
                .build();

        when(repository.findById("F1"))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(
                        useCase.execute("F1"))
                .assertNext(result -> {

                    assertEquals(
                            "Sucursal Cali",
                            result.getBranchName());

                    assertEquals(
                            "Hamburguesa",
                            result.getProductName());

                    assertEquals(
                            50,
                            result.getStock());
                })
                .verifyComplete();
    }
}
