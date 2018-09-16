package com.github.biconou.lab.mockito;

public class SuperCalculator {
    private Additioner additioner;

    public SuperCalculator(Additioner additioner) {
        this.additioner = additioner;
    }

    public int compute(int a, int b) {
        return additioner.add(a,b) * 2;
    }
}
