package com.ttag.api.utils;

/**
 * RabbitMQ常量类
 */
public class MQConstants {
    public static final String CALCULATE_SCORE_QUEUE = "score.calculate.direct.queue1";

    public static final String SCORE_EXCHANGE = "score.direct";

    public static final String CALCULATE_SCORE_KEY = "score.calculate";

    public static final String ORDER_CANCEL_QUEUE = "order.cancel.queue1";

    public static final String ORDER_EXCHANGE_DELAY = "order.direct.delay";

    public static final String ORDER_CANCEL_KEY = "order.cancel";

    public static final String SAVE_LOGISTICS_QUEUE = "logistics.save.queue1";

    public static final String SAVE_LOGISTICS_KEY = "logistics.save";

    public static final String ORDER_EXCHANGE = "order.direct";
}
