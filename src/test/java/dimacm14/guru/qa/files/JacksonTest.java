package dimacm14.guru.qa.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import dimacm14.guru.qa.files.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class JacksonTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void parseAndCheckJsonFileTest() throws Exception {
        File json = new File("src\\test\\resources\\user.json");
        UserModel user = objectMapper.readValue(json, UserModel.class);

        Assertions.assertEquals("Ivan", user.getFirstName());
        Assertions.assertEquals(20, user.getAge());
        Assertions.assertEquals(72.55, user.getWeight());
        Assertions.assertTrue(user.getIsStudent());
        Assertions.assertArrayEquals(new String[]{"Sports", "Reading", "Music"}, user.getHobbies());
    }
}
