package kr.desponline.desp_backend.service;


import static org.assertj.core.api.Assertions.assertThat;

import kr.desponline.desp_backend.dto.BasicUserInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MinecraftPublicAPIServiceTest {

    @LocalServerPort
    private int port;

    private WebTestClient webClient;

    @BeforeEach
    public void setUp() {
        this.webClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    public void testGetUuidByNickname() {
        // Given
        MinecraftPublicAPIService minecraftService = new MinecraftPublicAPIService();

        /* public example
          {
            "id" : "7125ba8b1c864508b92bb5c042ccfe2b",
            "name" : "KrisJelbring"
          }
          **/
        // When
        BasicUserInfoDTO basicUserInfoDTO = minecraftService.getUuidByNickname("KrisJelbring");

        assertThat(basicUserInfoDTO).isNotNull();
        assertThat(basicUserInfoDTO.getUuid()).isEqualTo("7125ba8b1c864508b92bb5c042ccfe2b");
    }

    @Test
    public void testGetNicknameByUuid() {
        // Given
        MinecraftPublicAPIService minecraftService = new MinecraftPublicAPIService();

        /* public example
          {
            "id" : "7125ba8b1c864508b92bb5c042ccfe2b",
            "name" : "KrisJelbring"
          }
          **/
        // When
        BasicUserInfoDTO basicUserInfoDTO = minecraftService.getNicknameByUuid("7125ba8b1c864508b92bb5c042ccfe2b");

        assertThat(basicUserInfoDTO).isNotNull();
        assertThat(basicUserInfoDTO.getNickname()).isEqualTo("KrisJelbring");
    }
}

