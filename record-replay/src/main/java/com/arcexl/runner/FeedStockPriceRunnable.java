package com.arcexl.runner;

import com.arcexl.domain.StockPrice;
import com.arcexl.reader.FeedStockPriceReader;
import com.arcexl.writer.StockPriceWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@ConditionalOnProperty(value = "scrapeStockPriceFromKafka", havingValue = "false")
@Service
public class FeedStockPriceRunnable implements StockPriceRunnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedStockPriceRunnable.class);

    @Autowired
    private FeedStockPriceReader feedStockPriceReader;

    @Autowired
    private StockPriceWriter stockPriceWriter;

    @Override
    public void run() {
        // if feedStockPriceReader is also reading from a stream, we would have put a while loop here. but since it is just reading from a file, we are not
        List<StockPrice> stockPrices = feedStockPriceReader.read();
        for (int i = 0; i < stockPrices.size(); i++) {
            stockPriceWriter.writeStockPrice(stockPrices.get(i));
            if (i % 2 == 0) {
                try {
                    // Intentionally slowing down the rate of production.
                    LOGGER.info("Sync sleeping for 300 millis every 2 price writes");
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void shutdown() {
        // no-op
    }
}
