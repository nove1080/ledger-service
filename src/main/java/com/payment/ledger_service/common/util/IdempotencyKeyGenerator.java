package com.payment.ledger_service.common.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdempotencyKeyGenerator {

    public static String generate(Object data) {
        String seed = data.toString();
        return UUID.nameUUIDFromBytes(seed.getBytes(StandardCharsets.UTF_8)).toString();
    }

}
