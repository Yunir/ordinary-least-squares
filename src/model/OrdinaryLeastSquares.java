package model;

import java.util.ArrayList;

public class OrdinaryLeastSquares {
    private int num_of_formula;
    private double x;
    private double step_of_x;
    //count of steps
    private int n;
    private ArrayList<Double> xi;
    private ArrayList<Double> yi;
    private double result;
    private double result_2;
    private boolean isCorrect = false;
    private double rungeRuleOffset;

    public OrdinaryLeastSquares(int num_of_formula, double x, double step_of_x) {
        this.num_of_formula = num_of_formula;
        this.x = x;
        this.step_of_x = step_of_x;
        n = 8;
        xi = new ArrayList<Double>();
        generateXi();
        yi = new ArrayList<Double>();
        generateYi();
        //TODO: generating results, deleting the worst and recalculation
        /*result = getResultOfIntegration(1);
        result_2 = getResultOfIntegration(2);*/
        //isCorrect = OrdinaryLeastSquares(result, result_2);
    }

    private void generateYi() {
        for (int i = 0; i < n; i++) {
            yi.add(formula(num_of_formula, xi.get(i)));
        }
    }
    private void generateXi() {
        for (int i = 0; i < n; i++) {
            xi.add(x + i*step_of_x);
        }
    }

    private double formula(int num, double x) {
        switch(num) {
            case 1:
                return (Math.cos(x)/(Math.pow(x, 2) + 1));
            case 2:
                return (Math.sqrt(1+2*Math.pow(x,2)-Math.pow(x,3)));
            case 3:
                return (1/Math.sqrt(3+Math.pow(x, 5)));
            case 4:
                return (Math.sqrt(Math.pow(x, 2)+3));
            default:
                return (Math.cos(x)/(Math.pow(x, 2) + 1));
        }

    }
}
