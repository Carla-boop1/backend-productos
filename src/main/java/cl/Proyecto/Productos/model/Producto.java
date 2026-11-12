package cl.Proyecto.Productos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único del producto (el front lo usa para carrito y wishlist). */
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String categoria;

    private String marca;

    private String modelo;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    /** URL de la imagen. Si es null, el front muestra la inicial del nombre. */
    @Column(length = 1000)
    private String imagen;
}
