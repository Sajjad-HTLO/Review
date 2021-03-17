//package com.sajad.demo.converter;
//
//import com.sajad.demo.domain.Product;
//import com.sajad.demo.dto.ProductUpdateDto;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductUpdateDtoToProductConverter implements Converter<ProductUpdateDto, Product> {
//
//    @Override
//    public Product convert(ProductUpdateDto updateDto) {
//        Product product = new Product();
//
//        product.setVisible(updateDto.isVisible());
//
//        return product;
//    }
//}
