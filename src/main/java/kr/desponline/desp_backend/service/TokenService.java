package kr.desponline.desp_backend.service;

// ... 기타 임포트 ...

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import java.time.Duration;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Service
public class TokenService {

    @Value("${minecraft.public-api.authenticate-token-url}")
    private String url;

    public CertificationResultDTO authenticate(String nickname, String authenticationCode) {
        // TcpClient를 사용하여 타임아웃 설정
        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(10)) // 읽기 타임아웃 설정 (예: 10초)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000); // 연결 타임아웃 설정 (예: 10000 밀리초)

        WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        AccessCredentialDTO request = new AccessCredentialDTO(nickname, authenticationCode);

        return webClient.post()
            .uri(url)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(jsonNode -> {
                boolean isValid = jsonNode.get("isValid").asBoolean();
                int remainingAttempts = jsonNode.get("remainingAttempts").asInt();
                return new CertificationResultDTO(remainingAttempts, isValid);
            })
            .block();
    }
}
