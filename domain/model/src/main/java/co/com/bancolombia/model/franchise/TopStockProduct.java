package co.com.bancolombia.model.franchise;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopStockProduct {

    private String branchName;
    private String productName;
    private Integer stock;
}
