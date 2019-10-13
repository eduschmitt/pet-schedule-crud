package com.sippulse.pet.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe utilizada para definir o formato de um LocalDateTime na serialização.
 * @author schmitt
 *
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
	
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:m");

    public LocalDateTimeSerializer(){
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
        gen.writeString(value.format(formatter));
    }
}