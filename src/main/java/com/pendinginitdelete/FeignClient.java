package com.pendinginitdelete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pendinginitdelete.interfaces.Requests;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class FeignClient
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Decoder DECODER = new JacksonDecoder(OBJECT_MAPPER);
    private static final Encoder ENCODER = new JacksonEncoder(OBJECT_MAPPER);

    public Feign.Builder prepareFeignBuilder()
    {
        Feign.Builder feignClient = Feign.builder().requestInterceptor(template ->
                        template
                                .header("Content-Type", "application/json")
                                .header("X-Client-Id", "dashboard")
                )
                .decoder(DECODER)
                .encoder(new FormEncoder(ENCODER));

        return feignClient;
    }

    public <T> T getClient(Class<T> cls, String url)
    {
        return prepareFeignBuilder().target(cls, url);
    }
}