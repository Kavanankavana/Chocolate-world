package com.example.chocolate.repository;

import com.example.chocolate.model.Chocolate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChocolateRepository extends JpaRepository<Chocolate, Long> {

    // Spring writes the query for you from the method name
    List<Chocolate> findByType(String type);
}
