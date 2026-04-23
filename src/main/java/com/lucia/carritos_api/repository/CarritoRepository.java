package com.lucia.carritos_api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucia.carritos_api.model.Carrito;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarritoRepository {

    private final Map<Long, Carrito> data = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path rutaFichero = Paths.get("carritos.json");

    public CarritoRepository() {
        cargarDesdeFichero();
    }

    public Carrito save(Carrito carrito) {
        if (carrito.getIdCarrito() == null) {
            carrito.setIdCarrito(seq.getAndIncrement());
        }
        data.put(carrito.getIdCarrito(), carrito);
        guardarEnFichero();
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
        guardarEnFichero();
    }

    private void cargarDesdeFichero() {
        try {
            if (Files.notExists(rutaFichero)) {
                Files.writeString(rutaFichero, "[]", StandardCharsets.UTF_8);
            }

            String contenido = Files.readString(rutaFichero, StandardCharsets.UTF_8).trim();

            if (contenido.isEmpty()) {
                contenido = "[]";
            }

            List<Carrito> lista = mapper.readValue(
                    contenido,
                    new TypeReference<List<Carrito>>() {}
            );

            data.clear();

            for (Carrito carrito : lista) {
                data.put(carrito.getIdCarrito(), carrito);
            }

            long maxId = lista.stream()
                    .map(Carrito::getIdCarrito)
                    .filter(Objects::nonNull)
                    .max(Long::compareTo)
                    .orElse(0L);

            seq.set(maxId + 1);

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar los carritos desde el fichero", e);
        }
    }

    private void guardarEnFichero() {
        try {
            List<Carrito> lista = new ArrayList<>(data.values());
            mapper.writerWithDefaultPrettyPrinter().writeValue(rutaFichero.toFile(), lista);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar los carritos en el fichero", e);
        }
    }
}