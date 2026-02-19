package com.uteq.edu.ec.ms_clientes;

import org.junit.jupiter.api.Test; // Anotación para pruebas unitarias
import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.boot.test.context.SpringBootTest; // Configuración de pruebas de Spring Boot
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; // Configuración automática de MockMvc
import org.springframework.test.web.servlet.MockMvc; // Simulación de solicitudes HTTP para pruebas

// Importaciones estáticas para MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc 
class MsClientesApplicationTests {

	 @Autowired
    private MockMvc mockMvc;

    @Test
    void debeRetornarClientePorId() throws Exception {

        mockMvc.perform(get("/clientes/api/1"))

                // ASSERTION 1: valida que el microservicio responde correctamente
                .andExpect(status().isOk()) // reponde con el código HTTP -> 200 OK

                //ASSERTION 2: valida que el contrato de la API se cumple
                .andExpect(jsonPath("$.id").value(1)); // el campo id del JSON debe O Devuelve 1
    }

	@Test
	void contextLoads() {
	}

}
