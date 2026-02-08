# Práctica 2 - API REST Carrito

API REST en Spring Boot que implementa CRUD para el recurso **Carrito**.

## Modelo Carrito

- idCarrito (Long)
- idArticulo (Long)
- descripcion (String)
- unidades (Integer)
- precioFinal (Decimal)

## Endpoints

| Método | Ruta | Body | Descripción | Respuestas |
|-------:|------|------|-------------|------------|
| POST | /carritos | JSON Carrito (sin idCarrito) | Crea un carrito | 201, 400 |
| GET | /carritos | - | Lista todos los carritos | 200 |
| GET | /carritos/{id} | - | Obtiene un carrito por id | 200, 404 |
| PUT | /carritos/{id} | JSON Carrito | Actualiza un carrito existente | 200, 404 |
| DELETE | /carritos/{id} | - | Borra un carrito por id | 204, 404 |

## Ejemplos (curl)

### Crear
```bash
curl -i -X POST http://localhost:8080/carritos \
  -H "Content-Type: application/json" \
  -d '{"idArticulo":10,"descripcion":"Camiseta","unidades":2,"precioFinal":39.98}'