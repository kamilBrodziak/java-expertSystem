package kamilbrodziak.expertsystem;

public class Main {
    public static void main(String[] args) {
        ESProvider esprovider = new ESProvider(new FactParser(), new RuleParser());
        esprovider.collectAnswers();
        System.out.println(esprovider.evaluate());
    }
}