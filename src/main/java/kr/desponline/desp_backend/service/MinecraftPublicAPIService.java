package kr.desponline.desp_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.dto.BasicUserInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
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
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    public BasicUserInfoDTO getUuidByNickname(String nickname) {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
        RequestHeadersSpec<?> headersSpec = requestHeadersUriSpec.uri(
            "https://api.mojang.com/users/profiles/minecraft/" + nickname);

        return headersSpec.exchangeToFlux(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToFlux(BasicUserInfoDTO.class);
            } else {
                return null;
            }
        }).blockFirst();
    }

    public BasicUserInfoDTO getNicknameByUuid(String uuid) {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
        RequestHeadersSpec<?> headersSpec = requestHeadersUriSpec.uri(
            "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);

        return headersSpec.exchangeToFlux(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToFlux(BasicUserInfoDTO.class);
            } else {
                return null;
            }
        }).blockFirst();
    }

    public boolean pingToServer(String address) {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
        RequestHeadersSpec<?> headersSpec = requestHeadersUriSpec.uri(
            "https://api.mcsrvstat.us/3/" + address);

        return headersSpec.exchangeToMono(response -> {
            if (response.statusCode().is2xxSuccessful()) {
                return response.bodyToMono(String.class)
                    .mapNotNull(body -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode rootNode = objectMapper.readTree(body);
                            return rootNode.path("debug").path("ping").asText();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    });
            } else {
                return Mono.empty();
            }
        }).blockOptional().isPresent();
    }
}
