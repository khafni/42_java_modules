package fr._42.repositories;

import fr._42.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                    SELECT * FROM PRODUCT;
                """)) {
            ResultSet rs = ps.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Integer productId = rs.getInt("id");
                String productName = rs.getString("name");
                Double productPrice = rs.getDouble("price");
                list.add(new Product(productId, productName, productPrice));
            }
            return list;
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                        SELECT * FROM PRODUCT
                        WHERE id = ?
                """)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                return Optional.of(product);
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product)
    {
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                UPDATE PRODUCT
                SET name = ?, price = ?
                WHERE id = ?
                """)) {
            ps.setLong(3, product.getIdentifier());
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

    }

    public void save(Product product){
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                INSERT INTO PRODUCT (name, price)
                VALUES
                (?, ?)
                SET name = ?, price = ?
                WHERE id = ?
                """)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    public void delete(Product product){
        try (Connection conn = dataSource.getConnection(); var ps = conn.prepareStatement("""
                DELETE FROM PRODUCT
                WHERE name = ? and price = ?
                """)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }
}
