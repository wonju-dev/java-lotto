package lotto.userinterface;

import camp.nextstep.edu.missionutils.Console;
import lotto.Lotto;
import lotto.Util;
import lotto.condition.Condition;
import lotto.condition.ConditionGenerator;
import lotto.message.MessageGenerator;
import lotto.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserInterface {

    private static final Validator validator = Validator.getInstance();

    private UserInterface() {
    }

    public static List<Integer> getLottoNumbers() {
        String input = getInput();
        List<Condition> conditions = ConditionGenerator.getLottoNumberCondition();

        Integer notPassConditionIndex = validator.getNotPassConditionIndex(conditions, input);

        if (notPassConditionIndex != -1) {
            Condition notPassCondition = conditions.get(notPassConditionIndex);
            throw new IllegalArgumentException();
        }

        return Util.getParsedNumbers(input);
    }


    public static Integer getMoney() {
        String input = getInput();
        List<Condition> conditions = ConditionGenerator.getMoneyCondition();

        Integer notPassConditionIndex = validator.getNotPassConditionIndex(conditions, input);

        if (notPassConditionIndex != -1) {
            Condition notPassCondition = conditions.get(notPassConditionIndex);
            throw new IllegalArgumentException();
        }

        return Integer.parseInt(input);
    }

    private static String getInput() {
        return Console.readLine();
    }

    public static void printLottos(List<Lotto> lottos) {
        String purchaseCountMessage = MessageGenerator.getPurchaseConfirmMessage(lottos.size());
        System.out.println(purchaseCountMessage);

        for (Lotto lotto : lottos) {
            System.out.println(lotto);
        }
    }

    public static Integer getBonusNumber(List<Integer> answerNumbers) {
        String input = getInput();
        List<Condition> conditions = ConditionGenerator.getBonusNumberCondition();

        String numbers = getConcatenatedString(answerNumbers, input);

        Integer notPassConditionIndex = validator.getNotPassConditionIndex(conditions, numbers);

        if (notPassConditionIndex != -1) {
            Condition notPassCondition = conditions.get(notPassConditionIndex);
            throw new IllegalArgumentException();
        }

        return Integer.parseInt(input);
    }

    private static String getConcatenatedString(List<Integer> numbers, String input) {
        return String.join(",", numbers.stream().map((Integer number) -> Integer.toString(number)).collect(Collectors.toList())) + "," + input;
    }

    public static void printResult(HashMap<Hit, Integer> result) {
        for (Hit hit : result.keySet()) {
            System.out.println(MessageGenerator.getLottoResultMessage(hit, result.get(hit)));
        }
    }

    public static void printEarningRate(float earningRate) {
        System.out.println(MessageGenerator.getEarningRateMessage(earningRate));
    }
}