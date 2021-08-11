package com.mids.auth.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    List<T> content;
    @ApiModelProperty(notes = "Current page number")
    int pageNumber;
    @ApiModelProperty(notes = "Size of the page or limit")
    int pageSize;
    @ApiModelProperty(notes = "Total number of pages")
    int totalPages;
    @ApiModelProperty(notes = "Total records")
    long totalElements;
    @ApiModelProperty(notes = "First page or not")
    boolean first;
    @ApiModelProperty(notes = "Last page or not")
    boolean last;
    @ApiModelProperty(notes = "Number of records in current page")
    int numberOfElements;
	
    
    
    
}