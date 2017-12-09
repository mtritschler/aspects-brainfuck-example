package com.github.mtritschler.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class BankImpl implements Bank {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankImpl.class);

    @Override
    public Map<String, String> getAccounts(String q) {
        LOGGER.info("Listing accounts for {}", q);
        return Collections.singletonMap(q, "q");
    }

}
