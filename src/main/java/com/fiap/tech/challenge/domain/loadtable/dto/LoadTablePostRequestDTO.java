package com.fiap.tech.challenge.domain.loadtable.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoadTablePostRequestDTO extends LoadTableRequestDTO {
}