package com.lucia.carritos_api.repository;

import com.lucia.carritos_api.model.Carrito;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarritoRepository {

    private final Map<Long, Carrito> data = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Carrito save(Carrito carrito) {
        if (carrito.getIdCarrito() == null) {
            carrito.setIdCarrito(seq.getAndIncrement());
        }
        data.put(carrito.getIdCarrito(), carrito);
        return carrito;
    }

    public Optional<Carrito> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<Carrito> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean existsById(Long id) {
        return data.containsKey(id);
    }

    public void deleteById(Long id) {
        data.remove(id);
    }
}