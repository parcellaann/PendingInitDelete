package com.pendinginitdelete;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class FeignClient
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Decoder decoder = new JacksonDecoder(objectMapper);
    private static final Encoder encoder = new JacksonEncoder(objectMapper);

    public Feign.Builder prepareFeignBuilder()
    {
        Feign.Builder feignClient = Feign.builder()
                .decoder(decoder)
                .encoder(new FormEncoder(encoder));
        //.requestInterceptor(new RequestSignature());

        return feignClient;
    }

    public <T> T getClient(Class<T> cls, String url)
    {
        return prepareFeignBuilder().target(cls, url);
    }
}