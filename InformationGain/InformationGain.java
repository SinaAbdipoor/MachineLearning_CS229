package id3;

import java.util.ArrayList;

/**
 *
 * @author sinaa
 */
class InformationGain {

    static double log2(double input) {
        return Math.log(input) / Math.log(2);
    }

    static double entropy(int positiveCount, int negativeCount) {
        double p = positiveCount, n = negativeCount, total = p + n;
        //If p or n is zero -> entropy will be zero
        if (p == 0 || n == 0) {
            return 0;
        }
        return -(p / total) * log2(p / total) - (n / total) * log2(n / total);
    }

    static double calcEntropy(ArrayList<Boolean> targetAttribute,
            ArrayList<String> attribute, String value) {
        int positiveCount = 0, negativeCount = 0;
        for (int i = 0; i < attribute.size(); i++) {
            if (attribute.get(i).equals(value)) {
                if (targetAttribute.get(i)) {
                    positiveCount++;
                } else {
                    negativeCount++;
                }
            }
        }
        return entropy(positiveCount, negativeCount);
    }

    static double informationGain(ArrayList<Boolean> targetAttribute,
            ArrayList<String> attribute) {
        //Gain(S,A) = Entropy(S) - ∑(Sv/S)Entropy(Sv)
        //Step 1: Calculating Entropy(S)
        int positiveCount = 0, negativeCount = 0;
        for (boolean bool : targetAttribute) {
            if (bool) {
                positiveCount++;
            } else {
                negativeCount++;
            }
        }
        double s = entropy(positiveCount, negativeCount);
        //Step 2: Calculating ∑(Sv/S)Entropy(Sv)
        //Step 2.1: Getting the different values(v) of "attribute"
        ArrayList<String> v = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        for (String str : attribute) {
            if (!v.contains(str)) {
                v.add(str);
                count.add(1);
            } else {    //Adding 1 to the count
                count.set(v.indexOf(str), count.get(v.indexOf(str)) + 1);
            }
        }
        //Step 2.2: ∑(Sv/S)Entropy(Sv)
        double a = 0;
        for (int i = 0; i < v.size(); i++) {
            double prop = (double) count.get(i) / (double) attribute.size();
            a += (prop * calcEntropy(targetAttribute, attribute, v.get(i)));
        }
        return s - a;
    }
}