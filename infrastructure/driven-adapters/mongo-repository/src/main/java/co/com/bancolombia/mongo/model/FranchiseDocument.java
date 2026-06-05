package co.com.bancolombia.mongo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "franchises")
public class FranchiseDocument {

    @Id
    private String id;

    private String name;

    private List<BranchDocument> branches;
}
