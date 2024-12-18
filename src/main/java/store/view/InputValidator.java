package store.view;

public class InputValidator {
    private static final String PURCHASE_FORMAT_REGEXP = "\\[[a-zA-Z0-9가-힣]+-\\d+\\](,\\[[a-zA-Z0-9가-힣]+-\\d+\\])*";

    public void isPurchaseFormatValid(String input) {
        if (!input.matches(PURCHASE_FORMAT_REGEXP)) {
            throw new IllegalArgumentException();
        }
        if (!isItemFormatValid(input)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isItemFormatValid(String input) {
        String[] items = input.split(",");
        for (String item : items) {
            String[] parseItem = item.substring(1, item.length() - 1).split("-");
            if (!isPositiveNumber(parseItem[1])) {
                return false;
            }
        }

        return true;
    }

    private boolean isPositiveNumber(String quantity) {
        try {
            int number = Integer.parseInt(quantity);
            if (number <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public void isYesOrNoFormatValid(String input) {
        if(!( input.equals("Y") || input.equals("N") ) )
            throw new IllegalArgumentException();
    }
}
