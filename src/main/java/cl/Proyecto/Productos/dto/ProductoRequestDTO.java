package cl.Proyecto.Productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 30, message = "El código no puede superar los 30 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
    private String categoria;

    @Size(max = 255, message = "La marca no puede superar los 255 caracteres")
    private String marca;

    @Size(max = 255, message = "El modelo no puede superar los 255 caracteres")
    private String modelo;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @Size(max = 1000, message = "La URL de la imagen no puede superar los 1000 caracteres")
    private String imagen;
}
