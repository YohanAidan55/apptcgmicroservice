package com.aidan.collectionservice.client.dto.tcg.lorcana;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aidan.collectionservice.client.dto.LorcanaProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("lorcana-sealed")
public class LorcanaSealedDto extends LorcanaProductDto {
}
