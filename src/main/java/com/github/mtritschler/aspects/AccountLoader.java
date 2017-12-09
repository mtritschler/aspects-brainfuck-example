package com.github.mtritschler.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AccountLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountLoader.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private Bank bank;

    public void refresh() {
        LOGGER.info("Refreshing accounts");
        // Uncommenting the following line will let the calls terminate
        // bank.getAccounts("sync");
        try {
            executorService.submit(() -> {
                bank.getAccounts("async");
            }).get(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostConstruct
    public void init() {
        refresh();
    }

}
