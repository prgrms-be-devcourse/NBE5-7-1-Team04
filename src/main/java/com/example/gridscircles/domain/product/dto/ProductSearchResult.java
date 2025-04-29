package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchResult {
    private List<ProductSearchResponse> productsList;
    private boolean hasData;
    private int currentPage;
    private int totalPages;
    private int size;

    @Builder
    public ProductSearchResult(List<ProductSearchResponse> productsList, boolean hasData, int currentPage, int totalPages, int size) {
        this.productsList = productsList;
        this.hasData = hasData;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.size = size;

    }
}
