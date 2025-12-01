import java.util.Scanner;

public class ProjectProgramming {
    static final double epsilon = 0.1;

    public static void main(String[] args) {
        String input = "";
        int rows = 0;
        int cols = 0;
        int range = 2;
        boolean validInput = false;

        Scanner scan = new Scanner(System.in);
        // get user input for rows
        do {
            System.out.print("rows in the environment: ");
            input = scan.nextLine();

            validInput = isInteger(input) && Integer.parseInt(input) > 0;
            if (!validInput)
                System.out.println("invalid input. Must be a positive int");
        } while (!validInput);
        rows = Integer.parseInt(input);

        // get user input for cols
        do {
            System.out.print("columns in the environment: ");
            input = scan.nextLine();

            validInput = isInteger(input) && Integer.parseInt(input) > 0;
            if (!validInput)
                System.out.println("invalid input. Must be a positive int");
        } while (!validInput);
        cols = Integer.parseInt(input);

        // get user input for range
        do {
            System.out.print("range for each state: 1 to ...");
            input = scan.nextLine();

            validInput = isInteger(input) && Integer.parseInt(input) > 1 && Integer.parseInt(input) <= 9;
            if (!validInput)
                System.out.println("invalid input. Must be a positive int in range [2,9]");
        } while (!validInput);
        range = Integer.parseInt(input);

        // set up scenario
        Agent agent = new Agent(rows, cols);
        Environment e = new Environment(rows, cols, range, agent);
        int totalT = 1;
        int t = 1;
        int oldT = 0;
        double alphaStep = 1 / t;
        boolean active = true;

        // main loop of Q-learning algo
        while (active) {
            e.reset(agent);

            // an episode
            do {
                e.printEnv();

                // agent performs action
                Agent.Actions action = agent.policy(epsilon / t);

                // get new state and reward for performing that action
                State newState = e.performAction(action);
                int reward = e.getReward(agent.state(), newState);

                // update agent's estimatedQ based on reward
                agent.update(reward, action, newState, alphaStep);

                // update t and alphaStep
                t++;
                totalT++;
                alphaStep = 1.0 / totalT;

                // wait for user's input
                // System.out.print("press enter to continue");
                // scan.nextLine();
            } while (!e.terminalState(agent));
            e.printEnv();
            System.out.println("time taken: " + (t - oldT));
            t -= oldT;
            oldT = t;

            // get user input for continuing simulation
            do {
                System.out.print("do another episode? 1 = yes, 0 = no: ");
                input = scan.nextLine();

                validInput = isInteger(input) && (Integer.parseInt(input) == 1 || Integer.parseInt(input) == 0);
                if (!validInput)
                    System.out.println("invalid input. Must be a either 0 or 1");
            } while (!validInput);
            if (Integer.parseInt(input) == 0) {
                active = false;
            }
        }

        scan.close();
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}