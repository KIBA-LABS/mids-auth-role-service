package com.mids.auth.role.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	List<T> content;
    @Schema(description = "Current page number")
    int pageNumber;
    @Schema(description = "Size of the page or limit")
    int pageSize;
    @Schema(description = "Total number of pages")
    int totalPages;
    @Schema(description = "Total records")
    long totalElements;
    @Schema(description = "First page or not")
    boolean first;
    @Schema(description = "Last page or not")
    boolean last;
    @Schema(description = "Number of records in current page")
    int numberOfElements;
	
    
    
    
}