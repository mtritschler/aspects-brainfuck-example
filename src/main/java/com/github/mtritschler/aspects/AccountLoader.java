package com.github.mtritschler.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class AccountLoader {

    private static final int NUM_THREADS = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountLoader.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    @Autowired
    private Bank bank;

    public void refresh() {
        LOGGER.info("Refreshing accounts");
        // Uncommenting the following line will let the calls terminate
//        bank.getAccounts("foo");
        List<Future<?>> futures = new ArrayList<>(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            final int n = i;
            Future<?> future = executorService.submit(() -> {
                LOGGER.info("Loading accounts {}", n);
                LOGGER.info("Got accounts {}", bank.getAccounts(String.valueOf(n)));
            });
            futures.add(future);
        }

        for (Future<?> future : futures) {
            try {
                future.get(5L, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    public void init() {
        refresh();
    }

}
