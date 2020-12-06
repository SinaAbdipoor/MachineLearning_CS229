package machinelearning;

import java.util.ArrayList;

/**
 *
 * @author sinaa
 */
final class InformationGain {

    static double calculateInformationGain(String[] attribute,
            boolean[] targetAttribute) {
        System.out.println("---------------------------------------------------"
                + "----------------------------------------------------------");
        System.out.println("Calculating Information Gain...");
        //Step 1: finding the different values of the attribute
        ArrayList<String> attributeValues = new ArrayList<>();
        for (String a : attribute) {
            if (!attributeValues.contains(a)) {
                attributeValues.add(a);
            }
        }

        //Step 2: finding frequency of the different values
        int[] valuesFrequencies = new int[attributeValues.size()];
        for (String a : attribute) {
            valuesFrequencies[attributeValues.indexOf(a)]++;
        }

        //Step 3: calculating the information gain
        System.out.println("\nEntropy(S): ");
        double s = calculateEntropy(targetAttribute);
        System.out.println("\nDifferent values of this attribute:");
        double v = 0;
        for (int i = 0; i < valuesFrequencies.length; i++) {
            System.out.println(attributeValues.get(i) + ":");
            v += ((double) valuesFrequencies[i] / (double) attribute.length)
                    * calculateEntropy(attribute, attributeValues.get(i),
                            targetAttribute);
        }
        double informationGain = s - v;

        //print info
        System.out.println("\nEntropy(S) = " + s);
        System.out.println("Entropy(S, V) = Σ|Sv|/|S|" + " Entropy(Sv) = " + v);
        System.out.println("Information Gain(S, V) = Entropy(S) - Entropy(S, V)"
                + " = " + informationGain);
        System.out.println("---------------------------------------------------"
                + "----------------------------------------------------------");
        return informationGain;
    }

    static double calculateEntropy(boolean[] targetAttribute) {
        int positiveCount = 0, negativeCount = 0;
        for (boolean a : targetAttribute) {
            if (a) {
                positiveCount++;
            } else {
                negativeCount++;
            }
        }
        return getEntropy(positiveCount, negativeCount);
    }

    static double calculateEntropy(String[] attribute, String attributeValue,
            boolean[] targetAttributes) {
        int positiveCount = 0, negativeCount = 0;
        for (int i = 0; i < attribute.length; i++) {
            if (attribute[i].equals(attributeValue)) {
                if (targetAttributes[i]) {
                    positiveCount++;
                } else {
                    negativeCount++;
                }
            }
        }
        return getEntropy(positiveCount, negativeCount);
    }

    static double getEntropy(int positiveCount, int negativeCount) {
        System.out.println(positiveCount + " +");
        System.out.println(negativeCount + " -");
        double p = positiveCount, n = negativeCount, total = p + n;
        return -((p / total) * log2(p / total))
                - ((n / total) * log2(n / total));
    }

    static double log2(double input) {
        return Math.log(input) / Math.log(2);
    }
}
