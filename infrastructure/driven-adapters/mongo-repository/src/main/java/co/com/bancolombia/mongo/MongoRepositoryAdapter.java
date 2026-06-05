package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import co.com.bancolombia.mongo.model.FranchiseDocument;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<
        Franchise,
        FranchiseDocument,
        String,
        MongoDBRepository>
        implements FranchiseRepository
{

    public MongoRepositoryAdapter(
            MongoDBRepository repository,
            ObjectMapper mapper) {

        super(
                repository,
                mapper,
                d -> mapper.map(d, Franchise.class)
        );
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }
}
