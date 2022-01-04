package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.dao.ProductDao;
import ru.gb.entity.Product;
import ru.gb.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductDao productDao;

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public long count(){
        System.out.println(productDao.count());
        return productDao.count();
    }

    public Product save(Product product){
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id){
        return productDao.findById(id).get();
    }

    public List<Product> findAll(){
        return productDao.findAllByStatus(Status.ACTIVE);
    }

    public List<Product> findAll(int page, int size){
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public List<Product> findAllSortedById(){
        return productDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Product> findAllSortedById(int page, int size){
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<Product> findAllSortedByCostIsDesc() {
        return productDao.findAllByStatus(Status.ACTIVE, Sort.by(Sort.Direction.DESC,"cost"));
    }
    public List<Product> findAllSortedByCostIsAsc() {
        return productDao.findAll(Sort.by(Sort.Direction.ASC,"cost"));
    }

    public List<Product> findAllSortedByCostIsAsc(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"cost")));
    }

    public List<Product> findAllSortedByCostIsDesc(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"cost")));
    }



    public void deleteById (Long id){
        Optional<Product> product= productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLED);
            productDao.save(p);
        });
    }
}
