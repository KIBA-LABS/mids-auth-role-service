package com.mids.auth.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    @ApiModelProperty(value = "error message", required = true)
    private List<String> errors;

	
    
    
}
