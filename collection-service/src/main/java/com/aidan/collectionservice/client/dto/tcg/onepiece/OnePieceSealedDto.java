package com.aidan.collectionservice.client.dto.tcg.onepiece;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aidan.collectionservice.client.dto.OnePieceProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("onepiece-sealed")
public class OnePieceSealedDto extends OnePieceProductDto {
}
