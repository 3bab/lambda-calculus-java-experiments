import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


class Main {

    public static Lambda IDENTITY = a -> a;

    public static Lambda TRUE = x -> y -> x;
    public static Lambda FALSE = x -> y -> y;
    public static Lambda SUCC = n -> f -> x -> f.$(n.$(f).$(x));// n -> f -> x -> f.$(n.$(f).$(x));

    public static Lambda ZERO = f -> x -> x;
    public static Lambda ONE = SUCC.$(ZERO);
    public static Lambda TWO = SUCC.$(ONE);
    public static Lambda THREE = SUCC.$(TWO);
    public static Lambda FOUR = SUCC.$(THREE);
    public static Lambda FIVE = SUCC.$(FOUR);


    public static Lambda PLUS = x -> x.$(SUCC);


    public static void main(String[] args) {

        Lambda IS_ZERO = n -> n.$(x -> FALSE).$(TRUE);

        System.out.println(toBoolean(IDENTITY.$(TRUE)));
        System.out.println(toBoolean(IDENTITY.$(FALSE)));
        System.out.println(toInt(IDENTITY.$(TWO)));
        System.out.println(toBoolean(IS_ZERO.$(ZERO)));
        System.out.println(toBoolean(IS_ZERO.$(ONE)));
        System.out.println(toInt(PLUS.$(ZERO).$(ZERO)));
        System.out.println(toInt(PLUS.$(ONE).$(ZERO)));
        System.out.println(toInt(PLUS.$(ONE).$(TWO)));
    }

    public static Lambda fromInt(int integer) {
        if (integer == 0) {
            return ZERO;
        }

        return SUCC.$(fromInt(integer - 1));
    }

    public static int toInt(Lambda lambda) {
        AtomicInteger result = new AtomicInteger();
        lambda.$(x -> {
                    result.set(result.get() + 1);
                    return x;
                })
                .$(IDENTITY);
        return result.get();
    }

    public static boolean toBoolean(Lambda lambda) {
        AtomicBoolean result = new AtomicBoolean();
        lambda
                .$(x -> {
                    result.set(true);
                    return x;
                })
                .$(x -> {
                    result.set(false);
                    return x;
                })
                .$(IDENTITY);
        return result.get();
    }
}