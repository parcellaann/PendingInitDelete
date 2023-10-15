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
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Decoder DECODER = new JacksonDecoder(OBJECT_MAPPER);
    private static final Encoder ENCODER = new JacksonEncoder(OBJECT_MAPPER);

    public Feign.Builder prepareFeignBuilder(String bearer, String client_id)
    {
        return Feign.builder().requestInterceptor(template ->
                        template
                                .header("Content-Type", "application/json")
                                .header("X-Client-Id", "dashboard")
                                .header("Authorization", bearer)
                                .header("X-Impersonate", client_id)
                )
                .decoder(DECODER)
                .encoder(new FormEncoder(ENCODER));
    }

    public <T> T getClient(Class<T> cls, String url, String bearer, String client_id)
    {
        return prepareFeignBuilder(bearer, client_id).target(cls, url);
    }
}