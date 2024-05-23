package kr.desponline.desp_backend.service;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.dto.GameRewardMailRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class GameMailService {

    @Value("${minecraft.security-api.send-mail-api}")
    private String sendMailAPIAddress;

    private WebClient client;

    public GameMailService() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
            .responseTimeout(Duration.ofMillis(3000))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(3000, TimeUnit.MILLISECONDS)));

        this.client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    public boolean sendMail(GameRewardMailRequestDTO gameMailRequestDTO) {
        // 성공 상태 코드 확인
        // 성공 시 true 반환
        // 실패 시 false 반환
        return Boolean.TRUE.equals(client.post()
            .uri(sendMailAPIAddress)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(gameMailRequestDTO))
            .retrieve()
            .onStatus(HttpStatusCode::is2xxSuccessful, response -> Mono.empty()) // 성공 상태 코드 확인
            .bodyToMono(Void.class)
            .then(Mono.fromCallable(() -> true)) // 성공 시 true 반환
            .defaultIfEmpty(false) // 실패 시 false 반환
            .block());
    }
}
