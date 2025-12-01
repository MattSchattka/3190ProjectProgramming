
public class Environment {
    // VARIABLES
    private int rows;
    private int cols;
    State[][] stateMatrix;
    private int range;

    private int agentX;
    private int agentY;

    // CONSTRUCTOR
    public Environment(int r, int c, int range, Agent a) {
        rows = r;
        cols = c;
        this.range = range;
        stateMatrix = new State[r][c];
        createStates();

        a.initializeState(stateMatrix[0][0]);
        agentX = 0;
        agentY = 0;
    }

    // METHODS
    private void createStates() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stateMatrix[i][j] = new State((int) (Math.random() * range) + 1, j, i);
            }
        }
    }

    public boolean terminalState(Agent a) {
        return a.state() == stateMatrix[rows - 1][cols - 1];
    }

    public void reset(Agent a) {
        a.initializeState(stateMatrix[0][0]);
        agentX = 0;
        agentY = 0;
    }

    public void printEnv() {
        System.out.println("Environment:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == agentY && j == agentX) {
                    System.out.print("X");
                } else {
                    System.out.print(stateMatrix[i][j].height());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public State performAction(Agent.Actions a) {
        State newState = stateMatrix[agentY][agentX];
        switch (a) {
            case Agent.Actions.up:
                if (agentY > 0)
                    newState = stateMatrix[agentY - 1][agentX];
                break;
            case Agent.Actions.down:
                if (agentY < rows - 1)
                    newState = stateMatrix[agentY + 1][agentX];
                break;
            case Agent.Actions.left:
                if (agentX > 0)
                    newState = stateMatrix[agentY][agentX - 1];
                break;
            case Agent.Actions.right:
                if (agentX < cols - 1)
                    newState = stateMatrix[agentY][agentX + 1];
                break;
        }
        agentX = newState.x();
        agentY = newState.y();
        return newState;
    }

    /// Returns an integer value of the reward which is the negative difference
    /// between the previous state There is also a penalty if the agent doesn't move,
    /// aka it tried to move out of bounds
    public int getReward(State oldState, State newState) {
        int diff = -Math.abs(newState.height() - oldState.height());
        int penalty = 0;
        int endReward = 0;
        if (oldState == newState)
            penalty = -50;
        if (newState.x() == cols - 1 && newState.y() == rows - 1)
            endReward = 50;
        return diff + penalty + endReward;
    }
}
