package cn.boommanpro.web.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boommanpro</a>
 * @date 2019/7/19 14:20
 */
public class LocalDate2LongSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(String.valueOf(value.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli()));
    }
}