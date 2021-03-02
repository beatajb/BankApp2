
package com.example.bank.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TransactionSerializer extends StdSerializer<Transaction> {
    
	private static final long serialVersionUID = -7229475179644035385L;

	public TransactionSerializer() {
        this(null);
    }
  
    public TransactionSerializer(Class<Transaction> t) {
        super(t);
    }

    @Override
    public void serialize(
      Transaction value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("Amount", value.getAmount().toString());
        jgen.writeStringField("SourceAccount", value.getAccountSource().getNumber());
        jgen.writeStringField("DestinationAccount", value.getAccountDestination().getNumber());
        jgen.writeEndObject();
    }
}
