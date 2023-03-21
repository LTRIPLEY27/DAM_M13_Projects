package IntegrationTest;

import com.ioc.dam_final_project.DamFinalProjectApplication;
import com.ioc.dam_final_project.model.Admin;
import com.ioc.dam_final_project.repository.AdminRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(classes = DamFinalProjectApplication.class)
class AdminRepositoryTest {

    @Autowired
    private AdminRepository admin;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /*
    @Test
    @DisplayName("Insert a new object into table")
    void insertToTable_OK(){
        var user = new Admin();
        user.setNombre("Josh");
        user.setApellido("Peres");
        user.setEmail("josh@fantasymail.com");
        user.setTelefono("999-999-999");
        user.setTareaList(new ArrayList<>());
        admin.save(user);
        Assertions.assertEquals(1, admin.count());
    }*/
}