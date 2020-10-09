package com.example.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class ExchangeApplicationTests {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository repository;

    @Test
    void test() throws IllegalAccessException, JsonProcessingException {
        String from = "USD";
        String to = "INR";

        ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
        exchangeValue.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port"))));

        List<Field> fields = Arrays.stream(exchangeValue.getClass().getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .collect(Collectors.toList());
        for (Field f : fields) {
            f.setAccessible(true);
            System.out.println(String.format("%s: %s", f.getName(), f.get(exchangeValue)));
        }

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(exchangeValue);
        System.out.println(json);

        assert String.valueOf(exchangeValue.getPort()).equals(environment.getProperty("server.port"));
    }

}
