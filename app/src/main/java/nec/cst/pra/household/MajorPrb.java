package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class MajorPrb implements Serializable {
    public String problem;
    public String possible;

    public MajorPrb(String problem, String possible) {
        this.problem = problem;
        this.possible = possible;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPossible() {
        return possible;
    }

    public void setPossible(String possible) {
        this.possible = possible;
    }
}
