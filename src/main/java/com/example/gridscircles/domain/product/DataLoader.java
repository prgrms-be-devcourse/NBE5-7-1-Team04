package com.example.gridscircles.domain.product;

import static com.example.gridscircles.domain.product.enums.Category.COFFEE;
import static com.example.gridscircles.domain.product.enums.Category.DRINK;

import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("productDataLoader")
@RequiredArgsConstructor
public class DataLoader {

    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
      if (productRepository.count() == 0) {
        productRepository.save(new Product(
            "아메리카노",
            COFFEE,
            "진한 에스프레소에 물을 더한 음료",
            3000,
            "/images/americano.jpg"
        ));

        productRepository.save(new Product(
            "토피넛라떼",
            COFFEE,
            "토피넛 들어간 음료",
            5000,
            "/images/latte.jpg"
        ));

        productRepository.save(new Product(
            "딸기스무디",
            DRINK,
            "딸기에 우유를 더한 음료",
            9000,
            "/images/strawbarry.jpg"
        ));

        productRepository.save(new Product(
            "에스프레소",
            COFFEE,
            "에스프레소",
            1000,
            "/images/americano1.jpg"
        ));


      }
    }
}



