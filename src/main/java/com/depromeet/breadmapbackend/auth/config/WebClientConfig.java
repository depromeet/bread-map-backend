package com.depromeet.breadmapbackend.auth.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class WebClientConfig {  // Spring WebClient Configuration

    @Bean
    public WebClient webClient() {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024*1024*50)) // 50MB
                .build(); // Spring WebFlux에서는 codec처리를 위한 in-memory buffer를 256KB로 설정되어 있음 -> 이 값을 늘리기 위한 설정
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true)); // 로그 상세 정보 확인

        return WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.from(
                                        TcpClient
                                                .create()
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                                                .doOnConnected(conn -> conn.addHandler(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS)))
                                )
                ))
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor( // client filters - request log 설정
                        clientRequest -> {
                            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientRequest);
                        }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor( // client filters - response log 설정
                        clientResponse -> {
                            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientResponse);
                        }
                ))
                .defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                .build();
    }
}
