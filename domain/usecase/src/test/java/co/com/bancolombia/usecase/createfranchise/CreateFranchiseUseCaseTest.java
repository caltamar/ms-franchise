package co.com.bancolombia.usecase.createfranchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private CreateFranchiseUseCase useCase;

    @Test
    void shouldCreateFranchiseSuccessfully() {

        Franchise franchise = Franchise.builder()
                .name("Burger King")
                .build();

        when(repository.save(any(Franchise.class)))
                .thenAnswer(invocation ->
                        Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.execute(franchise))
                .assertNext(result -> {
                    assertNotNull(result.getId());
                    assertEquals("Burger King", result.getName());
                    assertNotNull(result.getBranches());
                    assertTrue(result.getBranches().isEmpty());
                })
                .verifyComplete();

        verify(repository).save(any(Franchise.class));
    }
}
