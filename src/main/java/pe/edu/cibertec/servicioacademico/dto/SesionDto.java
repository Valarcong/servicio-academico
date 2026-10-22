package pe.edu.cibertec.servicioacademico.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SesionDto {

    private Integer idAsignacion;
    private Integer numeroSesion;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
}