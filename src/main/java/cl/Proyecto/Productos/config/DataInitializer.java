package cl.Proyecto.Productos.config;

import cl.Proyecto.Productos.model.Producto;
import cl.Proyecto.Productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) {
            return;
        }

        // imagen = null -> el front muestra la inicial del nombre en vez de una imagen rota
        productoRepository.saveAll(List.of(
                producto("KUR-001", "Peluche Kuromi Clásico", "Peluche suave de Kuromi de 30 cm", "Peluches", "Sanrio", "Kuromi 30cm", 14990.0, 20),
                producto("KUR-002", "Mochila Kuromi", "Mochila escolar con diseño de Kuromi", "Kuromi", "Sanrio", "Mochila Classic", 24990.0, 12),
                producto("ANI-001", "Figura Goku Super Saiyan", "Figura articulada de Goku, 18 cm", "Anime", "Bandai", "S.H.Figuarts Goku", 39990.0, 8),
                producto("ANI-002", "Poster Studio Ghibli", "Poster A3 con ilustración de Studio Ghibli", "Anime", "Ghibli", "Poster A3", 7990.0, 40),
                producto("FIG-001", "Figura Pikachu", "Figura coleccionable de Pikachu, 12 cm", "Figuras", "Pokémon", "Pikachu 12cm", 12990.0, 15),
                producto("PEL-001", "Peluche Stitch", "Peluche de Stitch de 40 cm", "Peluches", "Disney", "Stitch 40cm", 19990.0, 5),
                producto("SUP-001", "Figura Spider-Man", "Figura de Spider-Man con accesorios, 15 cm", "Superhéroes", "Marvel", "Spider-Man Legends", 29990.0, 10),
                producto("SUP-002", "Funko Batman", "Funko Pop de Batman edición clásica", "Superhéroes", "Funko", "Pop Batman", 16990.0, 3)
        ));
    }

    private Producto producto(String codigo, String nombre, String descripcion, String categoria,
                              String marca, String modelo, Double precio, Integer stock) {
        return Producto.builder()
                .codigo(codigo)
                .nombre(nombre)
                .descripcion(descripcion)
                .categoria(categoria)
                .marca(marca)
                .modelo(modelo)
                .precio(precio)
                .stock(stock)
                .imagen(null)
                .build();
    }
}
