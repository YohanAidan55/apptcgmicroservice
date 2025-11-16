package com.aidan.productservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Filtre minimal: name + opération + combinaison (AND/OR)")
public class ProductFilter {

    @Schema(description = "Nom à rechercher")
    private String name;

    @Schema(description = "Opération à appliquer sur le champ name: EQ, LIKE, GT, LT, GTE, LTE (par défaut LIKE)")
    private NameOperation nameOperation = NameOperation.LIKE;

    @Schema(description = "Combinaison des filtres (AND ou OR). Par défaut AND")
    private CombineOperator combineOperator = CombineOperator.AND;

    public enum NameOperation { EQ, LIKE, GT, LT, GTE, LTE }
    public enum CombineOperator { AND, OR }

}
