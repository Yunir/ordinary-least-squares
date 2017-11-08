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
    private double a, b, c;
    private int worst_ind;

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
        b = result[1][0];
        c = result[2][0];
        findYiDb();
        findD();
        worst_ind = findDpow2();
    }

    public void calculateNewOLS () {
        yiDb = new ArrayList<Double>();
        d = new ArrayList<Double>();
        dPow2 = new ArrayList<Double>();
        matrix = new double[][]{
                {xiPow4.get(n)-xiPow4.get(worst_ind), xiPow3.get(n)-xiPow3.get(worst_ind), xiPow2.get(n)-xiPow2.get(worst_ind), xiPow2ToYi.get(n)-xiPow2ToYi.get(worst_ind)},
                {xiPow3.get(n)-xiPow3.get(worst_ind), xiPow2.get(n)-xiPow2.get(worst_ind), xi.get(n)-xi.get(worst_ind), xiyi.get(n)-xiyi.get(worst_ind)},
                {xiPow2.get(n)-xiPow2.get(worst_ind), xi.get(n)-xi.get(worst_ind), n-1, yi.get(n)-yi.get(worst_ind)}
        };
        inversionOfMatrix(matrix, 3);
        result = matrixMultiplication();
        a = result[0][0];
        b = result[1][0];
        c = result[2][0];
        findYiDb();
        findD();
        int s = findDpow2();
    }

    private int findDpow2 () {
        double next, sum = 0;
        int ind = -1;
        double max = -1;
        for (int i = 0; i < n; i++) {
            next = Math.pow(yiDb.get(i), 2);
            if (max < next) {
                ind = i;
                max = next;
            }
            System.out.print(next + " ");
            dPow2.add(next);
            sum += next;
        }
        System.out.println("Max = " + max + " of index = " + ind);
        dPow2.add(sum);
        return ind;
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
        double n1, n2, n3;
        switch(num) {
            case 1:
                n1 = Math.cos(x);
                n2 = (Math.pow(x, 2) + 1);
                n3 = n1/n2;
                return n3;
            case 2:
                n1 = 1+2*Math.pow(x,2)-Math.pow(x,3);
                //n2 = Math.sqrt(n1);
                return n1;
            case 3:
                n1 = 3+Math.pow(x, 5);
                //n2 = Math.sqrt(n1);
                n3 = 1/n1;
                return n3;
            case 4:
                n1 = Math.pow(x, 2)+3;
                //n2 = Math.sqrt(n1);
                return n1;
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

    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public double getC() {
        return c;
    }
    public int getN() {
        return n;
    }
    public ArrayList<Double> getXi() {
        return xi;
    }
    public ArrayList<Double> getYi() {
        return yi;
    }
    public ArrayList<Double> getYiDb() {
        return yiDb;
    }
    public ArrayList<Double> getdPow2() {
        return dPow2;
    }
    public int getWorst_ind() {
        return worst_ind;
    }
}
