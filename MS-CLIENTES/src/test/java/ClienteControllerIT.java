import com.example.translate.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_then_getByClienteId() throws Exception {
        String body = "{" +
                "\"nombre\":\"Test User\"," +
                "\"genero\":\"M\"," +
                "\"edad\":30," +
                "\"identificacion\":\"999\"," +
                "\"direccion\":\"Quito\"," +
                "\"telefono\":\"0999999999\"," +
                "\"clienteId\":\"test123\"," +
                "\"contrasena\":\"1234\"," +
                "\"estado\":true" +
                "}";

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clienteId").value("test123"))
                .andExpect(jsonPath("$.nombre").value("Test User"));

        mockMvc.perform(get("/clientes/clienteId/{clienteId}", "test123"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clienteId").value("test123"))
                .andExpect(jsonPath("$.nombre").value("Test User"));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(java.util.List.class)));
    }
}
