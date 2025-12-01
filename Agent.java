public class Agent {
    public enum Actions {
        up,
        down,
        left,
        right
    }

    // CONSTANTS
    final double gamma = 0.9;
    final double epsilon = 0.1;

    final int NUM_ACTIONS = Actions.values().length;

    // VARIABLES
    private State currState;
    private double[][][] estimatedQ;

    // CONSTRUCTOR
    public Agent(int rows, int cols) {
        estimatedQ = new double[rows][cols][NUM_ACTIONS];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < NUM_ACTIONS; k++) {
                    estimatedQ[i][j][k] = 0;
                }
            }
        }
    }

    // METHODS
    public State state() {
        return currState;
    }

    public void initializeState(State s) {
        currState = s;
    }

    // e-greedy policy
    public Actions policy() {
        if (Math.random() <= epsilon) {
            // explore
            int randInt = (int) (Math.random() * NUM_ACTIONS);
            return Actions.values()[randInt];
        } else {
            // exploit
            Actions a = Actions.up;
            double max = estimatedQ[currState.y()][currState.x()][0];
            for (int n = 1; n < NUM_ACTIONS; n++) {
                if (estimatedQ[currState.y()][currState.x()][n] > max) {
                    max = estimatedQ[currState.y()][currState.x()][n];
                    a = Actions.values()[n];
                }
            }
            return a;
        }
    }

    // update estimatedQ for the current state and chosen action
    public void update(int reward, Actions chosenAction, State newState, double alphaStep) {
        int currX = currState.x();
        int currY = currState.y();
        int newX = newState.x();
        int newY = newState.y();
        int action = chosenAction.ordinal();
        double currValue = estimatedQ[currY][currX][action];

        double maxValue = estimatedQ[newY][newX][0];
        for (int n = 1; n < NUM_ACTIONS; n++) {
            if (estimatedQ[newY][newX][n] > maxValue) {
                maxValue = estimatedQ[newY][newX][n];
            }
        }
        System.out.println("action: " + chosenAction);
        System.out.println("reward: " + reward);
        System.out.println("alphaStep: " + alphaStep);
        System.out.println("old Q: " + currValue);
        System.out.println("target: " + (reward + gamma * maxValue));

        estimatedQ[currY][currX][action] += alphaStep * (reward + gamma * maxValue - currValue);

        System.out.println("new Q:" + estimatedQ[currY][currX][action]);

        // update to new state after calculations
        currState = newState;
    }
}
