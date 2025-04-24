package com.example.gridscircles.domain.order.scheduler;

import com.example.gridscircles.domain.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersCompleteScheduler {
    private final OrdersService ordersService;

    @Scheduled(cron = "0 0 14 * * *")
    public void completeOrders() {
        ordersService.completeOrders();
    }
}
