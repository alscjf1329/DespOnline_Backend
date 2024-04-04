package kr.desponline.desp_backend.service;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.dto.UuidResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.netty.http.client.HttpClient;

public class MinecraftPublicAPIService {

    private final WebClient client;

    public MinecraftPublicAPIService() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        this.client = WebClient.builder()
            .baseUrl("https://api.mojang.com")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    public UuidResponseDTO getUuidByNickname(String nickname) {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
        RequestHeadersSpec<?> headersSpec = requestHeadersUriSpec.uri(
            "/users/profiles/minecraft/" + nickname);

        return headersSpec.exchangeToFlux(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToFlux(UuidResponseDTO.class);
            } else {
                return null;
            }
        }).blockFirst();
    }
}
