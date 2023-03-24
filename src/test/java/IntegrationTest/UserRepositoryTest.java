package IntegrationTest;

import com.ioc.dam_final_project.DamFinalProjectApplication;
import com.ioc.dam_final_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;


@SpringBootTest(classes = DamFinalProjectApplication.class)
class UserRepositoryTest {

    @Autowired
    public UserRepository userRepo;
    @BeforeEach
    void setUp() {
    }

    /*@AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void findUserByNombre() {
    }
/*
    @Test
    @DisplayName("Insert a new object into table")
    void insertToTable_OK(){
        var user = new User();
        user.setNombre("Josh");
        user.setApellido("Peres");
        user.setEmail("josh@fantasymail.com");
        user.setTelefono("999-999-999");
        userRepo.save(user);
        Assertions.assertEquals(1, userRepo.count());
    }*/
}