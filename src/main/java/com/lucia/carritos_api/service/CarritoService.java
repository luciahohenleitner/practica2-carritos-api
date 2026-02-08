package com.lucia.carritos_api.service;

import com.lucia.carritos_api.model.Carrito;
import com.lucia.carritos_api.repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository repo;

    public CarritoService(CarritoRepository repo) {
        this.repo = repo;
    }

    public Carrito crear(Carrito c) {
        c.setIdCarrito(null);
        return repo.save(c);
    }

    public Carrito obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Carrito> listar() {
        return repo.findAll();
    }

    public Carrito actualizar(Long id, Carrito c) {
        if (!repo.existsById(id)) return null;
        c.setIdCarrito(id);
        return repo.save(c);
    }

    public boolean borrar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}