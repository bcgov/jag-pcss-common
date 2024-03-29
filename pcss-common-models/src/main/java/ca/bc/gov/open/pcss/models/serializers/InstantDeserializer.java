package ca.bc.gov.open.pcss.models.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstantDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        try {
            if (jsonParser.getText().split("-")[0].length() < 4) {
                var sfd = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSS a", Locale.US);
                sfd.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sfd.parse(jsonParser.getText()).toInstant();
            } else {
                var sfd = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
                sfd.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sfd.parse(jsonParser.getText()).toInstant();
            }
        } catch (ParseException e) {
            try {
                var sfd = new SimpleDateFormat("dd-MMM-yy", Locale.US);
                sfd.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sfd.parse(jsonParser.getText()).toInstant();
            } catch (ParseException e2) {
                try {
                    var sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    sfd.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return sfd.parse(jsonParser.getText()).toInstant();
                } catch (ParseException e3) {
                    return null;
                }
            }
        }
    }
}
