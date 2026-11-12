package cl.Proyecto.Productos.controller;

import cl.Proyecto.Productos.dto.ProductoRequestDTO;
import cl.Proyecto.Productos.dto.ProductoResponseDTO;
import cl.Proyecto.Productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Endpoints para la gestión del catálogo e inventario")
public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Obtener catálogo con filtros opcionales",
            description = "Filtros opcionales: busqueda (nombre), categoria, minPrecio, maxPrecio y orden (asc | desc por precio).")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double minPrecio,
            @RequestParam(required = false) Double maxPrecio,
            @RequestParam(required = false) String orden) {
        return ResponseEntity.ok(productoService.obtenerTodos(busqueda, categoria, minPrecio, maxPrecio, orden));
    }

    @Operation(summary = "Listar categorías existentes")
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> obtenerCategorias() {
        return ResponseEntity.ok(productoService.obtenerCategorias());
    }

    @Operation(summary = "Obtener producto por código")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(productoService.obtenerPorCodigo(codigo));
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo producto")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(dto));
    }

    @Operation(summary = "Actualizar datos completos del producto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @Operation(summary = "Actualizar solo el stock")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        return ResponseEntity.ok(productoService.actualizarStock(id, stock));
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
