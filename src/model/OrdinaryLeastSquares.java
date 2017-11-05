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
    private ArrayList<Double> xiyi;
    private ArrayList<Double> xiPow2;
    private ArrayList<Double> xiPow3;
    private ArrayList<Double> xiPow4;
    private ArrayList<Double> xiPow2ToYi;
    private ArrayList<Double> yiDb;
    private ArrayList<Double> d;
    private ArrayList<Double> dPow2;
    private double matrix[][];
    private double result[][];
    double a, b, c;

    public OrdinaryLeastSquares(int num_of_formula, double x, double step_of_x) {
        this.num_of_formula = num_of_formula;
        this.x = x;
        this.step_of_x = step_of_x;
        n = 8;
        xi = new ArrayList<Double>();
        yi = new ArrayList<Double>();
        xiyi = new ArrayList<Double>();
        xiPow2 = new ArrayList<Double>();
        xiPow3 = new ArrayList<Double>();
        xiPow4 = new ArrayList<Double>();
        xiPow2ToYi = new ArrayList<Double>();
        yiDb = new ArrayList<Double>();
        d = new ArrayList<Double>();
        dPow2 = new ArrayList<Double>();
        generateXi();
        generateYi();
        generateXiPow2();
        generateXiPow3();
        generateXiPow4();
        generateXiPow2ToYi();
        generateXiYi();
        matrix = new double[][]{
                {xiPow4.get(n), xiPow3.get(n), xiPow2.get(n), xiPow2ToYi.get(n)},
                {xiPow3.get(n), xiPow2.get(n), xi.get(n), xiyi.get(n)},
                {xiPow2.get(n), xi.get(n), n, yi.get(n)}
        };
        inversionOfMatrix(matrix, 3);
        result = matrixMultiplication();
        a = result[0][0];
        b = result[0][1];
        c = result[0][2];
        findYiDb();
        findD();
        findDpow2();
        //TODO: generating results, deleting the worst and recalculation
    }
    private void findDpow2 () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = Math.pow(yiDb.get(i), 2);
            dPow2.add(next);
            sum += next;
        }
        dPow2.add(sum);
    }
    private void findD () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = yi.get(i) - yiDb.get(i);
            d.add(next);
            sum += next;
        }
        d.add(sum);
    }
    private void findYiDb () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = a*xiPow2.get(i) + b*xi.get(i) + c;
            yiDb.add(next);
            sum += next;
        }
        yiDb.add(sum);
    }

    private void generateXiYi () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = xi.get(i)*yi.get(i);
            xiyi.add(next);
            sum += next;
        }
        xiyi.add(sum);
    }
    private void generateXiPow2ToYi () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = xiPow2.get(i)*yi.get(i);
            xiPow2ToYi.add(next);
            sum += next;
        }
        xiPow2ToYi.add(sum);
    }
    private void generateXiPow4 () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = Math.pow(xi.get(i), 4);
            xiPow4.add(next);
            sum += next;
        }
        xiPow4.add(sum);
    }
    private void generateXiPow3 () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = Math.pow(xi.get(i), 3);
            xiPow3.add(next);
            sum += next;
        }
        xiPow3.add(sum);
    }
    private void generateXiPow2 () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = Math.pow(xi.get(i), 2);
            xiPow2.add(next);
            sum += next;
        }
        xiPow2.add(sum);
    }
    private void generateYi () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = formula(num_of_formula, xi.get(i));
            yi.add(next);
            sum += next;
        }
        yi.add(sum);
    }
    private void generateXi () {
        double next, sum = 0;
        for (int i = 0; i < n; i++) {
            next = x + i*step_of_x;
            xi.add(next);
            sum += next;
        }
        xi.add(sum);
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

    private double[][] matrixMultiplication () {
        double[][] C = new double[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < 3; k++) {
                    C[i][j] += matrix[i][k] * matrix[k][3+j];
                }
            }
        }
        return C;
    }
    void inversionOfMatrix (double [][]A, int N) {
        double temp;
        double [][] E = new double [N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                E[i][j] = 0f;

                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < N; k++) {
            temp = A[k][k];

            for (int j = 0; j < N; j++) {
                A[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < N; i++) {
                temp = A[i][k];

                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = N - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = A[i][k];
                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = E[i][j];
            }
        }
    }
}
