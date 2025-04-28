package com.example.gridscircles.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.ErrorException;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@SpringBootTest
@DisplayName("Product Service 테스트")
class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 등록 테스트")
    void saveProductTest() throws Exception {
        MockMultipartFile mockFile =
            new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                "image.jpg".getBytes());

        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
            .name("상품명")
            .category(Category.DRINK)
            .price("1,000")
            .description("설명")
            .file(mockFile)
            .build();

        Product savedProduct = productService.saveProduct(productCreateRequest);

        assertThat(savedProduct.getName()).isEqualTo(productCreateRequest.getName());
        assertThat(savedProduct.getCategory()).isEqualTo(productCreateRequest.getCategory());
        assertThat(savedProduct.getPrice()).isEqualTo(1000);
        assertThat(savedProduct.getDescription()).isEqualTo(productCreateRequest.getDescription());
        assertThat(savedProduct.getImage()).isEqualTo(mockFile.getBytes());
        assertThat(savedProduct.getContentType()).isEqualTo(mockFile.getContentType());
        assertThat(savedProduct.getDel_yn()).isEqualTo("N");
    }

    @Test
    @DisplayName("상품 저장 파일 오류 테스트 ")
    void saveProductImageExceptionTest() throws Exception {

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        Mockito.when(mockFile.getBytes()).thenThrow(new IOException("파일 읽기 실패"));

        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
            .name("상품명")
            .category(Category.DRINK)
            .price("1,000")
            .description("설명")
            .file(mockFile)
            .build();

        assertThatThrownBy(
            () -> {
                productService.saveProduct(productCreateRequest);
            }
        ).isInstanceOf(ErrorException. class);

    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void findByIdProductTest() throws Exception {
        MockMultipartFile mockFile =
            new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                "image.jpg".getBytes());

        Product product = Product.builder()
            .name("상품명")
            .description("상품설명")
            .price(1000)
            .category(Category.DRINK)
            .image(mockFile.getBytes())
            .contentType(mockFile.getContentType())
            .del_yn("N")
            .build();
        productRepository.save(product);

        ProductResponse productResponse = productService.findProductById(product.getId());

        assertThat(productResponse.getName()).isEqualTo(product.getName());
        assertThat(productResponse.getDescription()).isEqualTo(product.getDescription());
        assertThat(productResponse.getPrice()).isEqualTo("1,000");
        assertThat(productResponse.getCategory()).isEqualTo(product.getCategory());
        assertThat(productResponse.getBase64EncodeImage()).isEqualTo(
            Base64.getEncoder().encodeToString(product.getImage()));
        assertThat(productResponse.getContentType()).isEqualTo(product.getContentType());
    }

    @Test
    @DisplayName("상품 상세 조회 오류 테스트 ")
    void findByIdProductExceptionTest() throws Exception {

        Long maxId = productRepository.findAll()
            .stream()
            .mapToLong(Product::getId)
            .max()
            .orElse(0L);

        Long nonExistentId = maxId + 1;

        assertThatThrownBy(
            () -> {
                productService.findProductById(nonExistentId);
            }
        ).isInstanceOf(ErrorException. class);

    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductByIdTest() throws Exception {
        MockMultipartFile mockFile =
            new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                "image.jpg".getBytes());

        Product product = Product.builder()
            .name("상품명")
            .description("상품설명")
            .price(1000)
            .category(Category.DRINK)
            .image(mockFile.getBytes())
            .contentType(mockFile.getContentType())
            .del_yn("N")
            .build();
        productRepository.save(product);

        productService.deleteProductById(product.getId());

        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        assertThat(optionalProduct.isPresent()).isTrue();

        Product deletedProduct = optionalProduct.get();

        assertThat(deletedProduct.getName()).isEqualTo(product.getName());
        assertThat(deletedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(deletedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(deletedProduct.getCategory()).isEqualTo(product.getCategory());
        assertThat(deletedProduct.getImage()).isEqualTo(product.getImage());
        assertThat(deletedProduct.getContentType()).isEqualTo(product.getContentType());
        assertThat(deletedProduct.getDel_yn()).isEqualTo("Y");
    }

    @Nested
    @DisplayName("상품 수정 테스트")
    class updateProductTests {

        @Test
        @DisplayName("상품 수정 테스트- 이미지 업로드 x")
        void updateProductTestWithoutImage() throws Exception {
            MockMultipartFile mockFile =
                new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                    "image.jpg".getBytes());

            Product product = Product.builder()
                .name("상품명")
                .description("상품설명")
                .price(1000)
                .category(Category.DRINK)
                .image(mockFile.getBytes())
                .contentType(mockFile.getContentType())
                .del_yn("N")
                .build();
            productRepository.save(product);

            MockMultipartFile emptyFile = new MockMultipartFile("file", "", "image/jpeg",
                new byte[0]);

            ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .id(product.getId())
                .name("상품명 수")
                .description("상품설정 수정")
                .price("2000")
                .category(Category.BREAD)
                .file(emptyFile)
                .base64EncodeImage(Base64.getEncoder().encodeToString(emptyFile.getBytes()))
                .contentType(emptyFile.getContentType())
                .build();
            productService.updateProduct(productUpdateRequest);

            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            assertThat(optionalProduct.isPresent()).isTrue();

            Product updatedProduct = optionalProduct.get();

            assertThat(updatedProduct.getName()).isEqualTo(productUpdateRequest.getName());
            assertThat(updatedProduct.getCategory()).isEqualTo(productUpdateRequest.getCategory());
            assertThat(updatedProduct.getPrice()).isEqualTo(2000);
            assertThat(updatedProduct.getDescription()).isEqualTo(
                productUpdateRequest.getDescription());
            assertThat(updatedProduct.getImage()).isEqualTo(
                Base64.getDecoder().decode(productUpdateRequest.getBase64EncodeImage()));
            assertThat(updatedProduct.getContentType()).isEqualTo(
                productUpdateRequest.getContentType());
            assertThat(updatedProduct.getDel_yn()).isEqualTo("N");
        }

        @Test
        @DisplayName("상품 수정 테스트 - 이미지 업로드 o")
        void updateProductTestWithImage() throws Exception {
            MockMultipartFile mockFile =
                new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                    "image.jpg".getBytes());

            Product product = Product.builder()
                .name("상품명")
                .description("상품설명")
                .price(1000)
                .category(Category.DRINK)
                .image(mockFile.getBytes())
                .contentType(mockFile.getContentType())
                .del_yn("N")
                .build();
            productRepository.save(product);

            MockMultipartFile updatedFile = new MockMultipartFile("updated_name",
                "updated_image.png", "image/png", "image.jpg".getBytes());

            ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .id(product.getId())
                .name("상품명 수")
                .description("상품설정 수정")
                .price("2000")
                .category(Category.BREAD)
                .file(updatedFile)
                .base64EncodeImage(Base64.getEncoder().encodeToString(mockFile.getBytes()))
                .contentType(mockFile.getContentType())
                .build();
            productService.updateProduct(productUpdateRequest);

            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            assertThat(optionalProduct.isPresent()).isTrue();

            Product updatedProduct = optionalProduct.get();

            assertThat(updatedProduct.getName()).isEqualTo(productUpdateRequest.getName());
            assertThat(updatedProduct.getCategory()).isEqualTo(productUpdateRequest.getCategory());
            assertThat(updatedProduct.getPrice()).isEqualTo(2000);
            assertThat(updatedProduct.getDescription()).isEqualTo(
                productUpdateRequest.getDescription());
            assertThat(updatedProduct.getImage()).isEqualTo(
                productUpdateRequest.getFile().getBytes());
            assertThat(updatedProduct.getContentType()).isEqualTo(
                productUpdateRequest.getFile().getContentType());
            assertThat(updatedProduct.getDel_yn()).isEqualTo("N");
        }

        @Test
        @DisplayName("상품 수정 오류 테스트 ")
        void updateProductExceptionTest () throws Exception {

            MockMultipartFile emptyFile = new MockMultipartFile("file", "", "image/jpeg",
                new byte[0]);

            Long maxId = productRepository.findAll()
                .stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0L);

            Long nonExistentId = maxId + 1;

            ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .id(nonExistentId)
                .name("상품명 수")
                .description("상품설정 수정")
                .price("2000")
                .category(Category.BREAD)
                .file(emptyFile)
                .base64EncodeImage(Base64.getEncoder().encodeToString(emptyFile.getBytes()))
                .contentType(emptyFile.getContentType())
                .build();

            assertThatThrownBy(
                () -> {
                    productService.updateProduct(productUpdateRequest);
                }
            ).isInstanceOf(ErrorException.class);


        }

        @Test
        @DisplayName("상품 수정 오류 테스트 - 이미지 오류")
        void updateProductImageExceptionTest () throws Exception {

            MockMultipartFile mockFile =
                new MockMultipartFile("image_name", "origin_image.jpg", "image/jpeg",
                    "image.jpg".getBytes());

            Product product = Product.builder()
                .name("상품명")
                .description("상품설명")
                .price(1000)
                .category(Category.DRINK)
                .image(mockFile.getBytes())
                .contentType(mockFile.getContentType())
                .del_yn("N")
                .build();
            productRepository.save(product);

            MultipartFile updatedFile = Mockito.mock(MultipartFile.class);

            Mockito.when(updatedFile.getBytes()).thenThrow(new IOException("파일 읽기 실패"));

            ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .id(product.getId())
                .name("상품명 수")
                .description("상품설정 수정")
                .price("2000")
                .category(Category.BREAD)
                .file(updatedFile)
                .base64EncodeImage(Base64.getEncoder().encodeToString(mockFile.getBytes()))
                .contentType(mockFile.getContentType())
                .build();

            assertThatThrownBy(
                () -> {
                    productService.updateProduct(productUpdateRequest);
                }
            ).isInstanceOf(Exception.class);

        }

    }
}