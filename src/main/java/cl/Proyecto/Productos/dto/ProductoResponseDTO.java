package cl.Proyecto.Productos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String marca;
    private String modelo;
    private Double precio;
    private Integer stock;
    private String imagen;
}
