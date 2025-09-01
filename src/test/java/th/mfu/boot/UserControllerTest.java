package th.mfu.boot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    private UserController controller = new UserController();

    @Test
    public void testRegisterUser() {
        User request = new User();
        request.setUsername("john");
        request.setEmail("john@example.com");

        ResponseEntity<String> response = controller.registerUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());

        ResponseEntity<User> user = controller.getUser("john");

        assertEquals("john", user.getBody().getUsername());
    }

    @Test
    public void testRegisterDuplicateUser() {
        User request1 = new User();
        request1.setUsername("brian");
        request1.setEmail("brian@example.com");

        User request2 = new User();
        request2.setUsername("brian");
        request2.setEmail("brian@example.com");

        ResponseEntity<String> response1 = controller.registerUser(request1);
        ResponseEntity<String> response2 = controller.registerUser(request2);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals("User registered successfully", response1.getBody());

        assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
        assertEquals("Username already exists", response2.getBody());
    }

    @Test
    public void testGetUser(){
        User request1 = new User();
        request1.setUsername("brian");
        request1.setEmail("brian@example.com");
        controller.registerUser(request1);

        ResponseEntity<User> user = controller.getUser("brian");
        assertEquals("brian", user.getBody().getUsername());

        ResponseEntity<User> unknownUser = controller.getUser("unknown");
        assertEquals(HttpStatus.NOT_FOUND, unknownUser.getStatusCode());
    }
}
