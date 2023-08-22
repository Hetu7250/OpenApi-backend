package com.hetu.openapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OpenapiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenapiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("to_baidu", r -> r.path("/baidu")
						.uri("http://baidu.com"))
				.route("to_yupi", r -> r.path("/yupi")
						.uri("http://yupi.icu"))
				.build();
	}

}