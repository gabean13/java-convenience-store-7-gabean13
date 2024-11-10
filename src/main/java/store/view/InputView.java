package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import store.common.ErrorMessageConstants;
import store.common.MenuMessageConstants;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public Map<String, Integer> getPurchaseItems() {
        while (true) {
            printGuide(MenuMessageConstants.PURCHASE_GUIDE);
            String input = Console.readLine();
            if (inputValidator.isPurchaseFormatValid(input)) {
                return convertInputToItems(input);
            }
            printGuide(ErrorMessageConstants.ERROR_INVALID_PURCHASE_FORMAT);
        }
    }

    protected Map<String, Integer> convertInputToItems(String input) {
        Map<String, Integer> items = new LinkedHashMap<>();
        String[] itemsString = input.split(",");
        for (String item : itemsString) {
            String[] split = item.substring(1, item.length() - 1).split("-");
            int quantity = Integer.parseInt(split[1]);
            items.put(split[0], items.getOrDefault(split[0], 0) + quantity);
        }

        return items;
    }

    public boolean getPromotion(String name) {
        while (true) {
            printGuide(MessageFormat.format(MenuMessageConstants.PROMOTION_BENEFIT_GUIDE, name));
            String input = Console.readLine();
            if (inputValidator.isYesOrNoFormatValid(input)) {
                return convertYesOrNo(input);
            }
            printGuide(ErrorMessageConstants.ERROR_INVALID_INPUT);
        }
    }

    public boolean getNotPromotion(String name, int lostQuantity) {
        while (true) {
            printGuide(MessageFormat.format(MenuMessageConstants.PROMOTION_OUT_OF_STOCK_GUIDE, name, lostQuantity));
            String input = Console.readLine();
            if (inputValidator.isYesOrNoFormatValid(input)) {
                return convertYesOrNo(input);
            }
            printGuide(ErrorMessageConstants.ERROR_INVALID_INPUT);
        }
    }

    public boolean getMembership() {
        while (true) {
            printGuide(MenuMessageConstants.MEMBERSHIP_GUIDE);
            String input = Console.readLine();
            if (inputValidator.isYesOrNoFormatValid(input)) {
                return convertYesOrNo(input);
            }
            printGuide(ErrorMessageConstants.ERROR_INVALID_INPUT);
        }
    }

    public boolean getAdditionalPurchase() {
        while (true) {
            printGuide(MenuMessageConstants.ADDITIONAL_PURCHASE_GUIDE);
            String input = Console.readLine();
            if (inputValidator.isYesOrNoFormatValid(input)) {
                return convertYesOrNo(input);
            }
            printGuide(ErrorMessageConstants.ERROR_INVALID_INPUT);
        }
    }

    public boolean convertYesOrNo(String input) {
        return input.equals("Y");
    }

    private void printGuide(String message) {
        System.out.println(message);
    }
}
