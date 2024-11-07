package store.view;

import java.util.List;
import store.common.CommonMessageConstants;
import store.common.MenuMessageConstants;
import store.model.Stock;

public class OutputView {

    private void print(String str) {
        System.out.println(str);
    }

    public void printGreetingAndMenu(List<Stock> stocks) {
        StringBuilder sb = new StringBuilder();
        sb.append(MenuMessageConstants.GREETING).append(CommonMessageConstants.NEW_LINE);
        stocks.stream().map(Stock::getMenuDetails).forEach(sb::append);
        print(sb.toString());
    }
}
