import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Entry {
    public static String[] getEntry() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение, в формате 'дробь(пример: 2/5)' 'пробел' 'дробь(пример: 5/7)' : ");
        String userInput = scanner.nextLine();
        if(Objects.equals(userInput, "quit")){
            throw new Exception("Конец ввода");
        }
        return userInput.split(" ");
    }
}
class Calс {
    public static Object[] EntryToRPN(String[] Expr) {
        ArrayList currentUser = new ArrayList();;
        Stack<String> stack = new Stack<>();
        int priority;
        for (int i =0; i < Expr.length; i++) {
            priority = getPriority(Expr[i]);
            if (priority ==0) currentUser.add(Expr[i]);
            if (priority ==1) stack.push(Expr[i]);
            if (priority > 1) {
                while(!stack.empty()) {
                    if(getPriority(stack.peek()) >= priority)currentUser.add(stack.pop());
                    else break;

                }
                stack.push(Expr[i]);
            }
            if (priority == -1) {
                while (getPriority(stack.peek()) != 1) currentUser.add(stack.pop());
                stack.pop();
            }
        }
        while(!stack.empty())currentUser.add(stack.pop());
        return currentUser.toArray();
    }
    public static String RPNToAns(Object[] RPN) throws Exception {
        String operand = new String();
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < RPN.length; i++) {
            if (getPriority(String.valueOf(RPN[i])) == 0){
                operand = (String) RPN[i];
                stack.push(operand);
            }
            if(getPriority(String.valueOf(RPN[i])) > 1){
                drob dr1 = new drob(stack.pop());
                drob dr2 = new drob(stack.pop());
                if (RPN[i].toString().equals("+")) stack.push(drob.summa(dr1,dr2));
                if (RPN[i].toString().equals("-")) stack.push(drob.diff(dr1,dr2));
                if (RPN[i].toString().equals("*")) stack.push(drob.prod(dr1,dr2));
                if (RPN[i].toString().equals(":")) stack.push(drob.quot(dr1,dr2));
            }
        }
        return stack.pop();
    }
    public static int getPriority(String token) {
        if (Objects.equals(token, "*" ) || Objects.equals(token, ":")) return 3;
        else if (Objects.equals(token, "+" ) || Objects.equals(token, "-")) return 2;
        else if (Objects.equals(token, "(" )) return 1;
        else if (Objects.equals(token, ")" )) return -1;
        else return 0;
    }
}
class fraction {
    public int numer, denom;
    public String fraction;
    public fraction(String fraction)throws Exception {
        int len = fraction.length();
        StringBuilder numer0 = new StringBuilder();
        StringBuilder denom0 = new StringBuilder();
        String pattern="[0-9]+/[1-9]+|-[0-9]+/[1-9]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fraction);
        int index = fraction.indexOf('/');
        if (m.matches()) {
            for (int i = 0; i < index; i++) {
                numer0.append(fraction.charAt(i));
            }
            for (int i = index + 1; i < len; i++) {
                denom0.append(fraction.charAt(i));
            }
        } else { throw new Exception("Неверный формат входных данных");
        }
        this.denom = Integer.parseInt(denom0.toString());
        this.numer = Integer.parseInt(numer0.toString());
    }
    public static String summa(fraction fraction0, fraction fraction1) {
        return (fraction0.denom * fraction1.numer + fraction0.numer * fraction1.denom) + "/" + fraction0.denom * fraction1.denom;
    }
    public static String diff(fraction fraction0, fraction fraction1) {
        return (fraction0.denom * fraction1.numer - fraction0.numer * fraction1.denom) + "/" + fraction0.denom * fraction1.denom;
    }
    public static String prod(fraction fraction0, fraction fraction1) {
        return fraction0.numer * fraction1.numer + "/" + fraction0.denom * fraction1.denom;
    }
    public static String quot(fraction fraction0, fraction fraction1) {
        return fraction0.numer * fraction1.denom + "/" + fraction0.denom * fraction1.numer;
    }
}
public class k_lab {
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println(Calс.RPNToAns(Calс.EntryToRPN(Entry.getEntry())));
        }
    }
}