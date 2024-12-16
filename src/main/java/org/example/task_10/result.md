Было:

```java
@UtilityClass
public class WidgetUtils {

    public static String getDeeplinkLabel(CashbackConditions conditions) {
        for (CardSectionDetailsDTO cardDetails : conditions.getCardDetailsList()) {
            String deeplinkLabel = cardDetails.getDeeplinkLabel();
            if (deeplinkLabel != null) {
                return deeplinkLabel;
            }
        }
        return StringUtils.EMPTY;
    }

    public static int getAppliedLevel(CashbackConditions conditions) {
        for (CardContentDTO cardContent : conditions.getAppliedCardCashbackLevel()) {
            Integer cashbackLevel = cardContent.getCashbackLevel();
            if (cashbackLevel != null) {
                return cashbackLevel;
            }
        }
        return -1;
    }

    public static int getPreviousLevel(CashbackConditions cashbackConditions, int appliedLevel) {
        for (CardContentDTO cardContent : cashbackConditions.getCardCashbackLevels()) {
            Integer cashbackLevel = cardContent.getCashbackLevel();
            if (cashbackLevel != null && cashbackLevel.equals(appliedLevel - 1)) {
                return cashbackLevel;
            }
        }
        return -1;
    }

    public static int getMaxLevel(CashbackConditions conditions) {
        int maxLevel = -1;
        for (CardContentDTO cardContent : conditions.getCardCashbackLevels()) {
            Integer cashbackLevel = cardContent.getCashbackLevel();
            if (cashbackLevel != null && cashbackLevel > maxLevel) {
                maxLevel = cashbackLevel;
            }
        }
        return maxLevel;
    }
}
```

Стало:

```java
@UtilityClass
public class WidgetUtils {
    
    public static String getDeeplinkLabel(CashbackConditions conditions) {
        return conditions.getCardDetailsList().stream()
                .map(CardSectionDetailsDTO::getDeeplinkLabel)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

    public static int getAppliedLevel(CashbackConditions conditions) {
        return conditions.getAppliedCardCashbackLevel().stream()
                .map(CardContentDTO::getCashbackLevel)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(-1);
    }

    public static int getPreviousLevel(CashbackConditions cashbackConditions, int appliedLevel) {
        return cashbackConditions.getCardCashbackLevels().stream()
                .map(CardContentDTO::getCashbackLevel)
                .filter(Objects::nonNull)
                .filter(level -> level.equals(appliedLevel - 1))
                .findFirst()
                .orElse(-1);
    }

    public static int getMaxLevel(CashbackConditions conditions) {
        return conditions.getCardCashbackLevels().stream()
                .map(CardContentDTO::getCashbackLevel)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(-1);
    }
}
```