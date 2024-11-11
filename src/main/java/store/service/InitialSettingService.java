package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.common.CommonMessageConstants;
import store.common.FileNameConstants;
import store.model.Product;
import store.model.Promotion;
import store.model.Stock;

public class InitialSettingService {
    private final FileService fileService;

    public InitialSettingService(FileService fileService) {
        this.fileService = fileService;
    }

    private static void saveGeneralStock(Map<String, Stock> stocks, Stock stock, int quantity) {
        if (!stocks.containsKey(stock.getName())) {
            stock.setGeneralQuantity(quantity);
            stocks.put(stock.getName(), stock);
            return;
        }
        stocks.get(stock.getName()).setGeneralQuantity(quantity);
    }

    private static void savePromotionStock(Map<String, Stock> stocks, Stock stock, int quantity, Product product) {
        if (!stocks.containsKey(product.getName())) {
            stock.setPromotionQuantity(quantity);
            stocks.put(product.getName(), stock);
            return;
        }
        stocks.get(product.getName()).setPromotionQuantity(quantity);
    }

    public Map<String, Promotion> convertFileToPromotions() {
        List<String> promotionFile = fileService.readFile(FileNameConstants.PROMOTION_FILE_NAME);
        return getPromotionMap(promotionFile);
    }

    protected Map<String, Promotion> getPromotionMap(List<String> promotionFile) {
        Map<String, Promotion> promotions = new HashMap<>();
        for (String file : promotionFile) {
            List<String> line = Arrays.stream(file.split(",")).toList();
            Promotion promotion = parsePromotion(line);
            if (promotion != null) {
                promotions.put(line.get(0), promotion);
            }
        }
        return promotions;
    }

    private Promotion parsePromotion(List<String> line) {
        String name = line.get(0);
        int requiredQuantity = Integer.parseInt(line.get(1));
        int presentQuantity = Integer.parseInt(line.get(2));
        LocalDateTime startDate = LocalDate.parse(line.get(3), DateTimeFormatter.ISO_DATE).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(line.get(4), DateTimeFormatter.ISO_DATE).atTime(LocalTime.MAX);

        if (isBetweenStartDateAndEndDate(startDate, endDate)) {
            return new Promotion(name, requiredQuantity, presentQuantity, startDate, endDate);
        }
        return null;
    }

    private boolean isBetweenStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = DateTimes.now();
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            return true;
        }
        return false;
    }

    public Map<String, Stock> convertFileToStocks(Map<String, Promotion> promotions) {
        List<String> productFile = fileService.readFile(FileNameConstants.PRODUCT_FILE_NAME);
        return getStocks(productFile, promotions);
    }

    protected Map<String, Stock> getStocks(List<String> productFile, Map<String, Promotion> promotions) {
        Map<String, Stock> stocks = new LinkedHashMap<>();

        for (String file : productFile) {
            List<String> line = Arrays.stream(file.split(",")).toList();
            saveStock(line, stocks, promotions);
        }

        return stocks;
    }

    private Product parseProduct(String name, String price, String promotionName, Map<String, Promotion> promotions) {
        if (promotions.containsKey(promotionName)) {
            return new Product(name, Integer.parseInt(price), promotionName);
        }
        return new Product(name, Integer.parseInt(price), CommonMessageConstants.NULL_STRING);
    }

    private void saveStock(List<String> line, Map<String, Stock> stocks, Map<String, Promotion> promotions) {
        Product product = parseProduct(line.get(0), line.get(1), line.get(3), promotions);
        Stock stock = new Stock(product);

        int quantity = Integer.parseInt(line.get(2));
        if (product.isPromotionProduct()) {
            savePromotionStock(stocks, stock, quantity, product);
            return;
        }

        saveGeneralStock(stocks, stock, quantity);
    }
}
