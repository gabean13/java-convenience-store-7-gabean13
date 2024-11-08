package store.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.common.FileNameConstants;
import store.model.Product;
import store.model.Promotion;
import store.model.Stock;

public class InitialSettingService {
    private final FileService fileService;

    public InitialSettingService(FileService fileService) {
        this.fileService = fileService;
    }

    public Map<String, Promotion> convertFileToPromotions() {
        List<String> promotionFile = fileService.readFile(FileNameConstants.PROMOTION_FILE_NAME);
        return getPromotionMap(promotionFile);
    }

    protected Map<String, Promotion> getPromotionMap(List<String> promotionFile) {
        Map<String, Promotion> promotions = new HashMap<>();
        for (String file : promotionFile) {
            List<String> line = Arrays.stream(file.split(",")).toList();
            promotions.put(line.get(0), parsePromotion(line));
        }
        return promotions;
    }

    private Promotion parsePromotion(List<String> line) {
        String name = line.get(0);
        int requiredQuantity = Integer.parseInt(line.get(1));
        int presentQuantity = Integer.parseInt(line.get(2));
        LocalDateTime startDate = LocalDate.parse(line.get(3), DateTimeFormatter.ISO_DATE).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(line.get(4), DateTimeFormatter.ISO_DATE).atTime(LocalTime.MAX);

        return new Promotion(name, requiredQuantity, presentQuantity, startDate, endDate);
    }

    public List<Stock> convertFileToStocks() {
        List<String> productFile = fileService.readFile(FileNameConstants.PRODUCT_FILE_NAME);
        return getStocks(productFile);
    }

    protected List<Stock> getStocks(List<String> productFile) {
        List<Stock> stocks = new ArrayList<>();

        for (String file : productFile) {
            List<String> line = Arrays.stream(file.split(",")).toList();
            saveStock(line, stocks);
        }

        return stocks;
    }

    private void saveStock(List<String> line, List<Stock> stocks) {
        Product product = parseProduct(line.get(0), line.get(1), line.get(3));
        Stock stock = new Stock(product);

        int quantity = Integer.parseInt(line.get(2));
        if (product.isPromotionProduct()) {
            savePromotionStock(stocks, stock, quantity, product);
            return;
        }

        saveGeneralStock(stocks, stock, quantity);
    }

    private Product parseProduct(String name, String price, String promotionName) {
        return new Product(name, Integer.parseInt(price), promotionName);
    }

    private static void saveGeneralStock(List<Stock> stocks, Stock stock, int quantity) {
        if (!stocks.contains(stock)) {
            stock.saveGeneralQuantity(quantity);
            stocks.add(stock);
            return;
        }
        stocks.get(stocks.indexOf(stock)).saveGeneralQuantity(quantity);
    }

    private static void savePromotionStock(List<Stock> stocks, Stock stock, int quantity, Product product) {
        if (!stocks.contains(stock)) {
            stock.savePromotionQuantity(quantity);
            stocks.add(stock);
            return;
        }
        stocks.get(stocks.indexOf(product)).savePromotionQuantity(quantity);
    }
}
