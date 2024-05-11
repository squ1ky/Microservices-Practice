package com.katharsys.clients.notification;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public record NotificationRequest(Integer toCustomerId,
                                  String toCustomerEmail,
                                  String sender,
                                  String message,
                                  @JsonSerialize(using = LocalDateTimeSerializer.class)
                                  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                  LocalDateTime sentAt) {
}
