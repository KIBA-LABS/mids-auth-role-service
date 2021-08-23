package com.mids.auth.role.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class SuccessResponse {
	@Schema(description = "Success message", required = true)
    private String status;

	public SuccessResponse(String status) {
		super();
		this.status = status;
	}

	
    
    
    
    
}
