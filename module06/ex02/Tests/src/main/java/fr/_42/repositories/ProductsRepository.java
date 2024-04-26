package fr._42.repositories;

import fr._42.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
    public void update(Product product);
//    void save(Product product);
//    void delete(Long id);
}
