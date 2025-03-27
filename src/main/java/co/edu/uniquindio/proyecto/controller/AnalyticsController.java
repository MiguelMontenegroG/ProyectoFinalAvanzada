package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.response.AnalyticsResponse;
import co.edu.uniquindio.proyecto.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints para análisis de reportes")
@SecurityRequirement(name = "BearerAuth")
@Validated
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(
            summary = "Generar reportes analíticos",
            description = "Obtiene métricas agregadas de los reportes ciudadanos. Requiere rol ADMIN.",
            parameters = {
                    @Parameter(
                            name = "metricas",
                            description = "Lista de métricas a calcular",
                            required = true,
                            in = ParameterIn.QUERY,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            type = "string",
                                            allowableValues = {"categorias", "estados", "historial", "tendencias"},
                                            example = "categorias"
                                    )
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Múltiples métricas",
                                            value = "categorias,estados"
                                    ),
                                    @ExampleObject(
                                            name = "Una métrica",
                                            value = "tendencias"
                                    )
                            }
                    ),
                    @Parameter(
                            name = "fechaInicio",
                            description = "Fecha de inicio para filtrar (opcional)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date", example = "2023-01-01")
                    ),
                    @Parameter(
                            name = "fechaFin",
                            description = "Fecha de fin para filtrar (opcional)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date", example = "2023-12-31")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reportes generados exitosamente",
                            content = @Content(schema = @Schema(implementation = AnalyticsResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parámetros inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso no autorizado"
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reportes generados exitosamente",
                            content = @Content(schema = @Schema(implementation = AnalyticsResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parámetros inválidos",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }

    )
    @GetMapping("/reportes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsResponse> generarReportesAnaliticos(
            @RequestParam(name = "metricas") List<String> metricas,
            @RequestParam(name = "fechaInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(hidden = true) LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(hidden = true) LocalDate fechaFin) {

        return ResponseEntity.ok(analyticsService.generarReportes(metricas, fechaInicio, fechaFin));
    }
}