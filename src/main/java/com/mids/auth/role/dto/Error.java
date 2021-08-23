package com.mids.auth.role.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
	@Schema(description = "error message", required = true)
    private List<String> errors;

	
    
    
}
