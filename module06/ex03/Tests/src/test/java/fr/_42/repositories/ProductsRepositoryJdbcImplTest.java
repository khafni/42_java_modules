package _42.repositories;

import fr._42.models.Product;
import fr._42.repositories.ProductsRepositoryJdbcImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class ProductsRepositoryJdbcImplTest {
    // i have to respect the project structure and i want to avoid coupling classes so i did
    // rewrite the datasource creation logic here
    private DataSource ds;
    @BeforeEach
    void init() {
        getConnections();
    }

    @AfterEach
    void teardown() {
        ((EmbeddedDatabase)ds).shutdown();
    }

    @Test
    void isConnectionValid() {
        assertDoesNotThrow(() -> ds.getConnection());
    }
    private void getConnections() {
        ds =  new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0, "macbook pro", 4500),
            new Product(1, "iphone", 2000),
            new Product(2, "lambo exotic", 9000000),
            new Product(3, "porsche 911", 2000000),
            new Product(4, "auddi R6", 50000),
            new Product(5, "rarri", 3000000)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3, "porsche 911", 2000000);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2, "lambo exotic", 9);
    final Product SAVE_PRODUCT = new Product("dacia", 2);

    final Product DELETE_PRODUCT = new Product("iphone", 2000);


    @Test
    void isFindAllCorrect() {
        assertTrue(() -> {
            ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(ds);
            List<Product> list = productsRepositoryJdbc.findAll();
            return list.equals(EXPECTED_FIND_ALL_PRODUCTS);
        });
    }

    @Test
    void isFindByIDCorrect() {
        assertTrue(() -> {
            ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(ds);
            Optional<Product> product =  productsRepositoryJdbc.findById(EXPECTED_FIND_BY_ID_PRODUCT.getIdentifier());
            return product.isPresent() && product.get().getName().equals(EXPECTED_FIND_BY_ID_PRODUCT.getName()) && product.get().getPrice() == EXPECTED_FIND_BY_ID_PRODUCT.getPrice();
        });
    }
    @Test
    void isUpdateCorrect() {
        assertTrue(() -> {
            ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(ds);
            productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
            Optional<Product> modProduct = productsRepositoryJdbc.findById(EXPECTED_UPDATED_PRODUCT.getIdentifier());
            return modProduct.isPresent() && modProduct.get().getPrice() == EXPECTED_UPDATED_PRODUCT.getPrice();
        });
    }

    @Test
    void isSaveCorrect() {
        assertTrue(() -> {
            ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(ds);
            productsRepositoryJdbc.update(SAVE_PRODUCT);
            List<Product> product_list = productsRepositoryJdbc.findAll();
            for (Product p: product_list)
                if (p.getName().equals(SAVE_PRODUCT.getName()) && p.getPrice() == SAVE_PRODUCT.getPrice())
                    return true;
            return false;
        });
    }

    @Test
    void isDeleteCorrect() {
        assertTrue(()-> {
            ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(ds);
            productsRepositoryJdbc.delete(DELETE_PRODUCT);
            List<Product> product_list = productsRepositoryJdbc.findAll();
            for (Product p: product_list)
                if (p.getName().equals(DELETE_PRODUCT.getName()) && p.getPrice() == DELETE_PRODUCT.getPrice())
                    return false;
            return true;
        });
    }
}