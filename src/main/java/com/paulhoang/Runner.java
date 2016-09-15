package com.paulhoang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.util.Set;

import static spark.Spark.*;

/**
 * Created by paul on 02/08/2016.
 */
public class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);
    private static final String SLEEP_PARAM = "sleep";
    private static final String DIE_PARAM = "die";
    private static final String RESPONSE_MESSAGE = "Mills slept for: ";
    private static final long DEFAULT_WAIT_TIME = 5000L;

    public static void main(String[] args) {
        get("/", (req, res) -> "Hi, your on deadendpoint a simulator for slow and deadendpoints. " +
                "Send your requests to /api?sleep=<numberInMilliseconds> or /api?die=true using any HTTP Method");

        get("/api", ((request, response) -> sleepOrDie(request)));
        post("/api", ((request, response) -> sleepOrDie(request)));
        put("/api", ((request, response) -> sleepOrDie(request)));
        patch("/api", ((request, response) -> sleepOrDie(request)));
        options("/api", ((request, response) -> sleepOrDie(request)));
        delete("/api", ((request, response) -> sleepOrDie(request)));
        head("/api", ((request, response) -> sleepOrDie(request)));
        connect("/api", ((request, response) -> sleepOrDie(request)));
        trace("/api", ((request, response) -> sleepOrDie(request)));
    }

    public static String sleepOrDie(final Request request) {
        LOG.info("Dealing with HTTP method: {}", request.requestMethod());
        final Set<String> queryParams = request.queryParams();
        if (queryParams.contains(SLEEP_PARAM)) {
            final String sleepTimeInMills = request.queryParams(SLEEP_PARAM);
            try {
                final long sleepTime = Long.parseLong(sleepTimeInMills);
                sleep(sleepTime);
                return RESPONSE_MESSAGE + sleepTime;
            } catch (final NumberFormatException e) {
                LOG.error("Could not convert {} to number {}", sleepTimeInMills, e);
            }
        } else if (queryParams.contains(DIE_PARAM)) {
            sleep(DEFAULT_WAIT_TIME);
            Thread.currentThread().interrupt();
        } else {
            sleep(DEFAULT_WAIT_TIME);
            return RESPONSE_MESSAGE + DEFAULT_WAIT_TIME;
        }
        return RESPONSE_MESSAGE + 0;
    }

    public static void sleep(final long sleepTime) {
        try {
            LOG.info("Sleeping for {} milliseconds", sleepTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            LOG.warn("Exception thrown during sleep {}", e);
            e.printStackTrace();
        }
    }
}
