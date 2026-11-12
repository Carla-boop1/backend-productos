package cl.Proyecto.Productos.repository;

import cl.Proyecto.Productos.model.Producto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    @Query("select distinct p.categoria from Producto p order by p.categoria")
    List<String> findCategorias();

    /**
     * Misma lógica de filtros que hace el front: nombre contiene texto,
     * categoría exacta y rango de precio. Los filtros en null se ignoran.
     */
    @Query("""
            select p from Producto p
            where lower(p.nombre) like lower(concat('%', :texto, '%'))
              and (:categoria is null or p.categoria = :categoria)
              and (:minPrecio is null or p.precio >= :minPrecio)
              and (:maxPrecio is null or p.precio <= :maxPrecio)
            """)
    List<Producto> buscar(@Param("texto") String texto,
                          @Param("categoria") String categoria,
                          @Param("minPrecio") Double minPrecio,
                          @Param("maxPrecio") Double maxPrecio,
                          Sort sort);
}
