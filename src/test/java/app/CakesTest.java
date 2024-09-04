package app;

import app.controller.CakeController;
import app.dto.CakeDTO;
import app.model.Cake;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreIngApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CakesTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CakeController cakeController;
    ObjectMapper m = new ObjectMapper();

    @Test
    public void unAuthorizedCake() throws Exception {

        List<CakeDTO> allCakes = this.cakeController.getAllCakes();
        mvc.perform(MockMvcRequestBuilders.get("/server/cakes/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "admin", roles = "EMPLOYEE")
    public void employeeCakeFlow() throws Exception {

        List<CakeDTO> allCakes = this.cakeController.getAllCakes();
        mvc.perform(MockMvcRequestBuilders.get("/server/cakes/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Cake c1 = new Cake(1L, "Chocolate Strawberry", "Rich chocolate flavour", null);

        this.mvc.perform(MockMvcRequestBuilders.post("/server/cakes/new")
                        .content(m.writeValueAsString(c1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        int i = this.cakeController.getAllCakes().size();
        assertEquals(1, i);

        Cake c2 = new Cake(1L, "Chocolate Strawberry", "Rich chocolate flavour", null);
        List<CakeDTO> l = cakeController.getAllCakes();
        Long id = l.get(0).getId();
        this.mvc.perform(MockMvcRequestBuilders.put("/server/cakes/" + id).content(m.writeValueAsString(c2))
                        .content(m.writeValueAsString(c2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        i = this.cakeController.getAllCakes().size();
        assertEquals(1, i);

        this.mvc.perform(MockMvcRequestBuilders.delete("/server/cakes/" + id))
                .andDo(print())
                .andExpect(status().isForbidden());
        i = this.cakeController.getAllCakes().size();
        assertEquals(1, i);
    }


}
