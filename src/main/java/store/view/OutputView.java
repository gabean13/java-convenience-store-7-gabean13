package store.view;

import java.util.Map;
import store.common.CommonMessageConstants;
import store.common.ErrorMessageConstants;
import store.common.MenuMessageConstants;
import store.model.Receipt;
import store.model.Stock;

public class OutputView {

    private void print(String str) {
        System.out.println(str);
    }

    public void printGreetingAndMenu(Map<String, Stock> stocks) {
        StringBuilder sb = new StringBuilder();
        sb.append(MenuMessageConstants.GREETING).append(CommonMessageConstants.NEW_LINE);
        for (Stock stock : stocks.values()) {
            sb.append(stock.getMenuDetails());
        }
        //마지막 줄 개행 삭제
        sb.setLength(sb.length() - 1);
        print(sb.toString());
    }

    public void printReceipt(Receipt receipt) {
        if(receipt.isPurchaseNothing()) {
            printPurchaseNothing();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MenuMessageConstants.RECEIPT_START).append(MenuMessageConstants.RECEIPT_START_DESCRIPTION).append(receipt.lineUpPurchasedProductsReceipt());
        sb.append(MenuMessageConstants.RECEIPT_PROMOTION).append(receipt.lineUpPromotionProductsReceipt());
        sb.append(MenuMessageConstants.RECEIPT_LINE).append(receipt.lineUpPurchaseInfo()).append(CommonMessageConstants.NEW_LINE);
        print(sb.toString());
    }

    protected void printPurchaseNothing() {
        print(MenuMessageConstants.RECEIPT_BUY_NOTHING);
    }

    public void printError(String errorMessage) {
        print(ErrorMessageConstants.ERROR_MESSAGE_PREFIX + errorMessage);
    }
}
