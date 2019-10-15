package fresher.RockPaperScissors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void createGame() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlcm5hbWUiOiJ0aHVjIiwiZXhwIjoxNTY1MDEzOTgzfQ.vKBYDpnMtXAX9fArahjJKeiJ6zpS0fzWDOqtPOQe7Mc";
        String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MDIiLCJ1c2VybmFtZSI6InRodWMxIiwiZXhwIjoxNTY1MDExNTc4fQ.Po8CNzN3dGVVYiM_RmzUNfsY2VoC9IAlHb1vU257mpc";
        String wrongTypeToken = "xxx.xxx.xxx";
        mvc.perform(MockMvcRequestBuilders
                .post("/games")
                .header("token", fakeToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .post("/games")
                .header("token", "")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        mvc.perform(MockMvcRequestBuilders
                .post("/games")
                .header("token", wrongTypeToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .post("/games")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void playGame() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlcm5hbWUiOiJ0aHVjIiwiZXhwIjoxNTY1MDEzOTgzfQ.vKBYDpnMtXAX9fArahjJKeiJ6zpS0fzWDOqtPOQe7Mc";
        String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MDIiLCJ1c2VybmFtZSI6InRodWMxIiwiZXhwIjoxNTY1MDExNTc4fQ.Po8CNzN3dGVVYiM_RmzUNfsY2VoC9IAlHb1vU257mpc";
        String wrongTypeToken = "xxx.xxx.xxx";
        String idGameExist = "2";
        String idGameNotExist ="1000";

        mvc.perform(MockMvcRequestBuilders
                .post("/games/"+idGameExist)
                .param("userplay","2")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders
                .post("/games/"+idGameExist)
                .param("userplay","2")
                .header("token", wrongTypeToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .post("/games/" + idGameNotExist)
                .param("userplay","2")
                .header("token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .post("/games/"+idGameExist)
                .param("userplay","2")
                .header("token", "")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .post("/games/1")
                .param("userplay","2")
                .header("token", fakeToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void getHistoryGame() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlcm5hbWUiOiJ0aHVjIiwiZXhwIjoxNTY1MDEzOTgzfQ.vKBYDpnMtXAX9fArahjJKeiJ6zpS0fzWDOqtPOQe7Mc";
        String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MDIiLCJ1c2VybmFtZSI6InRodWMxIiwiZXhwIjoxNTY1MDExNTc4fQ.Po8CNzN3dGVVYiM_RmzUNfsY2VoC9IAlHb1vU257mpc";
        String wrongTypeToken = "xxx.xxx.xxx";
        mvc.perform(MockMvcRequestBuilders
                .get("/users/games/turns")
                .header("token", wrongTypeToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                .get("/users/games/turns")
                .header("token", "")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        mvc.perform(MockMvcRequestBuilders
                .get("/users/games/turns")
                .header("token", fakeToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        mvc.perform(MockMvcRequestBuilders
                .get("/users/games/turns")
                .header("token", token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
