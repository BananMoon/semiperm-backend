package com.project.semipermbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Base64;

@EnableJpaAuditing
@SpringBootApplication
public class SemipermBackendApplication {

	public static void main(String[] args) {
//		String text = "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJhMjFhYjE2YmNjZWZlNmNlYWUxNzRjNzllMTMxZjUwNCIsInN1YiI6IjI5NTE2OTg5NTUiLCJhdXRoX3RpbWUiOjE2OTEzMjMwNTgsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLrrLjsnKTsp4AiLCJleHAiOjE2OTEzNDQ2NTgsImlhdCI6MTY5MTMyMzA1OCwicGljdHVyZSI6Imh0dHA6Ly9rLmtha2FvY2RuLm5ldC9kbi84THBvMS9idHNsYlFiNHlaWC9peWxzYXBhRkFsZ2VYMDhJb0dKSVgxL2ltZ18xMTB4MTEwLmpwZyIsImVtYWlsIjoiYmFuYW45OUBuYXZlci5jb20ifQ.CTZ1bTdbp5j8mJ13mApF-HYmu1Ul4RO1t79_S9mAllOnNPg8m9k_DQi0E7DRxHqzJn0a2iyL1NFIKC9k4d8tdgLnEOQPxXDd4_2KonbxjL0EDSkPTYzd1Jct8KRQrBFsYAl6P7vqj3auTZgITn43SaqNy74baWW-3t58KqYQ6_WFqai8gULm7nxZ-iCYTyB4RWnyno0byMCLW0Ky7cfEJr7kOnIwghlwUP0Q3LqmsquaEhiz3mlOEfaMXdSSB2ok8ppFcuAGkFTv2wF4t5KSSW-83SQqEhd4wuOQDLjBNS-3XSNJPrHMUv26LNzwhTCc3IPcmj6w-eSh5pxbqmWSJw";
//		byte[] encodedBytes = text.getBytes();
//
//		Base64.Decoder decoder = Base64.getDecoder();
//		byte[] decode = decoder.decode(encodedBytes);
//		System.out.println(new String(decode));
		SpringApplication.run(SemipermBackendApplication.class, args);
	}

}
