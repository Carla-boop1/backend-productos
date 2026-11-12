package cl.Proyecto.Productos.service;

import cl.Proyecto.Productos.dto.ProductoRequestDTO;
import cl.Proyecto.Productos.dto.ProductoResponseDTO;
import cl.Proyecto.Productos.exception.DuplicateResourceException;
import cl.Proyecto.Productos.exception.ResourceNotFoundException;
import cl.Proyecto.Productos.model.Producto;
import cl.Proyecto.Productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Catálogo con los mismos filtros del front.
     * orden: "asc" | "desc" (por precio); cualquier otro valor = por id (relevancia).
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos(String busqueda, String categoria,
                                                  Double minPrecio, Double maxPrecio, String orden) {
        String texto = busqueda == null ? "" : busqueda.trim();
        String cat = (categoria == null || categoria.isBlank() || categoria.equalsIgnoreCase("todos"))
                ? null : categoria.trim();

        Sort sort = switch (orden == null ? "" : orden.toLowerCase()) {
            case "asc" -> Sort.by(Sort.Order.asc("precio"), Sort.Order.asc("id"));
            case "desc" -> Sort.by(Sort.Order.desc("precio"), Sort.Order.asc("id"));
            default -> Sort.by(Sort.Order.asc("id"));
        };

        return productoRepository.buscar(texto, cat, minPrecio, maxPrecio, sort).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> obtenerCategorias() {
        return productoRepository.findCategorias();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        return mapToDTO(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el código: " + codigo));
        return mapToDTO(producto);
    }

    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        String codigo = dto.getCodigo().trim();
        if (productoRepository.existsByCodigo(codigo)) {
            throw new DuplicateResourceException("Ya existe un producto con el código: " + codigo);
        }
        Producto producto = new Producto();
        aplicarDatos(producto, dto);
        return mapToDTO(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = buscarPorId(id);

        String codigo = dto.getCodigo().trim();
        if (productoRepository.existsByCodigoAndIdNot(codigo, id)) {
            throw new DuplicateResourceException("Ya existe otro producto con el código: " + codigo);
        }
        aplicarDatos(producto, dto);
        return mapToDTO(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock) {
        if (nuevoStock == null || nuevoStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        Producto producto = buscarPorId(id);
        producto.setStock(nuevoStock);
        return mapToDTO(productoRepository.save(producto));
    }

    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con el ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));
    }

    private void aplicarDatos(Producto producto, ProductoRequestDTO dto) {
        producto.setCodigo(dto.getCodigo().trim());
        producto.setNombre(dto.getNombre().trim());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria().trim());
        producto.setMarca(dto.getMarca());
        producto.setModelo(dto.getModelo());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen() == null || dto.getImagen().isBlank() ? null : dto.getImagen().trim());
    }

    private ProductoResponseDTO mapToDTO(Producto p) {
        return ProductoResponseDTO.builder()
                .id(p.getId())
                .codigo(p.getCodigo())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .categoria(p.getCategoria())
                .marca(p.getMarca())
                .modelo(p.getModelo())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .imagen(p.getImagen())
                .build();
    }
}
