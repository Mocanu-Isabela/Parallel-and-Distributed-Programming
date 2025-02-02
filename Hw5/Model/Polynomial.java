package Hw5.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {
    private List<Integer> coefficients;
    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.generateCoefficients(degree);
    }

    private void generateCoefficients(int degree) {
        coefficients = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < degree; i++) {
            coefficients.add(r.nextInt(10));
        }
        coefficients.add(r.nextInt(10) + 1);
//        if(degree == 10) {
//            coefficients.add(2);
//            coefficients.add(9);
//            coefficients.add(5);
//            coefficients.add(4);
//            coefficients.add(8);
//            coefficients.add(7);
//            coefficients.add(7);
//            coefficients.add(6);
//            coefficients.add(5);
//            coefficients.add(5);
//            coefficients.add(7);
//        }
//        else {
//            coefficients.add(1);
//            coefficients.add(6);
//            coefficients.add(7);
//            coefficients.add(7);
//            coefficients.add(5);
//            coefficients.add(9);
//            coefficients.add(8);
//            coefficients.add(3);
//            coefficients.add(5);
//            coefficients.add(8);
//            coefficients.add(5);
//        }
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(coefficients.get(degree)).append("x^").append(degree);
        for (var i = degree - 1; i > 0; --i) {
            if (coefficients.get(i) != 0)
                builder.append("+").append(coefficients.get(i)).append("x^").append(i);
        }
        if (coefficients.get(0) != 0)
            builder.append("+").append(coefficients.get(0));
        return builder.toString();
    }
}
