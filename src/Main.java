import java.util.*;


class Main {
    public static void main(String[] args) {

        Lambda IDENTITY = a -> a;

        Lambda TRUE = x -> y -> x;
        Lambda FALSE = x -> y -> y;

        Lambda ZERO = f -> x -> x;
        Lambda ONE = f -> x -> x.$(ZERO);

        Lambda SUCC = n -> f -> x -> f.$(n.$(f).$(x));
        Lambda PLUS = x -> x.$(SUCC);

        LambdaHash hashes = new LambdaHash();
        hashes.put("IDENTITY", IDENTITY.hashCode());
        hashes.put("TRUE", TRUE.hashCode());
        hashes.put("FALSE", FALSE.hashCode());
        hashes.put("ZERO", ZERO.hashCode());
        hashes.put("SUCC", SUCC.hashCode());

        Lambda IS_ZERO = n -> n.$(x -> FALSE).$(TRUE);

        System.out.println(hashes.getName(IDENTITY.$(TRUE).hashCode()));
        System.out.println(IS_ZERO.$(ZERO).hashCode());

    }
}

class LambdaHash {
    private final Map<String, Integer> hashes = new HashMap<>();

    public void put(String name, int hash) {
        hashes.put(name, hash);
    }

    public String getName(int hash) {
        for (Map.Entry<String, Integer> entry : hashes.entrySet()) {
            if (entry.getValue().equals(hash)) {
                return entry.getKey();
            }
        }
        return "hash not found";
    }
}