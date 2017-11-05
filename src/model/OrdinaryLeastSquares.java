package model;

import java.util.ArrayList;

public class OrdinaryLeastSquares {
    private int num_of_formula;
    private double x;
    private double step_of_x;
    //count of steps
    private int n;
    //step offset
    private double h;
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
        //TODO: generate table of x'es and y'es
        xi = new ArrayList<Double>();
        //generateXi();
        yi = new ArrayList<Double>();
        //generateYi();
        //TODO: generating results, deleting the worst and recalculation
        /*result = getResultOfIntegration(1);
        result_2 = getResultOfIntegration(2);*/
        //isCorrect = OrdinaryLeastSquares(result, result_2);
    }

    private double formula_1(double x) {
        return (Math.cos(x)/(Math.pow(x, 2) + 1));
    }
    private double formula_2(double x) {
        return (Math.sqrt(1+2*Math.pow(x,2)-Math.pow(x,3)));
    }
    private double formula_3(double x) {
        return (1/Math.sqrt(3+Math.pow(x, 5)));
    }
    private double formula_4(double x) {
        return (Math.sqrt(Math.pow(x, 2)+3));
    }
}
