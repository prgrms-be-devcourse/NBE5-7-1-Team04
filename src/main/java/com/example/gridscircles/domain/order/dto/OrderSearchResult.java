package com.example.gridscircles.domain.order.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearchResult {

    private List<OrdersSearchResponse> ordersList;
    private boolean hasData;
    private int currentPage;
    private int totalPages;
    private int size;

    @Builder
    public OrderSearchResult(List<OrdersSearchResponse> ordersList, boolean hasData, int currentPage, int totalPages, int size) {
        this.ordersList = ordersList;
        this.hasData = hasData;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.size = size;

    }

}
