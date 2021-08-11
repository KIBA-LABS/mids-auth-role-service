package com.mids.auth.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SuccessResponse {
    @ApiModelProperty(value = "Success message", required = true)
    private String status;

	public SuccessResponse(String status) {
		super();
		this.status = status;
	}

	
    
    
    
    
}
