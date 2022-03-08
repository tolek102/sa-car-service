package pl.springacademy.carservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.springacademy.carservice.model.Car;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Car> findAll() {

        final String sqlQuery = "SELECT * FROM cars";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Car.class));
    }

    @Override
    public Optional<Car> findById(final Long id) {

        final String sqlQuery = "SELECT * FROM cars WHERE id = " + id;

        final Car car;
        try {
            car = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Car.class));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (final DataAccessException e) {
            throw e;
        }
        System.out.println("car = " + car);
        return Optional.ofNullable(car);
    }

    @Override
    public void save(final Car car) {

        final String sqlQuery = "INSERT INTO cars VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, null, car.getMark(), car.getModel(), car.getProductionYear(), car.getColor().name());
    }

    @Override
    public void update(final Long id, final Car newCar) {

        final String sqlQuery = "UPDATE cars SET mark=?, model=?, production_year=?, color=? WHERE id = ?";

        try {
            jdbcTemplate.update(sqlQuery, newCar.getMark(), newCar.getModel(), newCar.getProductionYear(),
                    newCar.getColor().name(), id);
        } catch (final EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Car with id " + id + " not found");
        } catch (final DataAccessException e) {
            throw e;
        }
    }

    @Override
    public List<Car> findByProductionYearRange(final Integer beginYear, final Integer endYear) {
        final String sqlQuery = "SELECT * FROM cars WHERE production_year >= " + beginYear + " AND production_year <= " + endYear;

        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Car.class));
        } catch (final EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Cars produced between " + beginYear + " and " + endYear + " was not found");
        } catch (final DataAccessException e) {
            throw e;
        }
    }

    @Override
    public void delete(final Long id) {
        final String sqlQuery = "DELETE FROM cars where id = " + id;

        try {
            jdbcTemplate.update(sqlQuery);
        } catch (final EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Car with id " + id + " not found");
        } catch (final DataAccessException e) {
            throw e;
        }
    }
}
