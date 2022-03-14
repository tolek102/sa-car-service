package pl.springacademy.carservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import pl.springacademy.carservice.model.Car;
import pl.springacademy.carservice.model.Color;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findCarsByProductionYearIsBetween(final @NonNull Integer beginProductionYear, final @NonNull Integer endYear);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cars SET mark = :mark, model = :model, production_year = :productionYear, color = :color WHERE id = :id",
            nativeQuery = true)
    void update(@Param(value = "mark") String mark, @Param(value = "model") String model, @Param(value = "productionYear") Integer productionYear,
            @Param(value = "color") String color, @Param(value = "id") Long id);
}
