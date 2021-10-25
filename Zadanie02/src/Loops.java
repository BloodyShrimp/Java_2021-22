import java.util.*;

class Loops implements GeneralLoops {
    private List<Integer> lowerLimits;
    private List<Integer> upperLimits;
    private List<List<Integer>> results;
    private List<Integer> currentLoop;

    public void setLowerLimits(List<Integer> limits) {
        lowerLimits = new ArrayList<Integer>(limits);
    }

    public void setUpperLimits(List<Integer> limits) {
        upperLimits = new ArrayList<Integer>(limits);
    }

    public List<List<Integer>> getResult() {
        if (lowerLimits == null && upperLimits == null) {
            lowerLimits = new ArrayList<Integer>();
            lowerLimits.add(0);
            upperLimits = new ArrayList<Integer>();
            upperLimits.add(0);
        } else if ((lowerLimits == null) && (upperLimits != null)) {
            lowerLimits = new ArrayList<Integer>();
            for (int i = 0; i < upperLimits.size(); i++) {
                lowerLimits.add(0);
            }
        } else if ((lowerLimits != null) && (upperLimits == null)) {
            upperLimits = new ArrayList<Integer>();
            for (int i = 0; i < lowerLimits.size(); i++) {
                upperLimits.add(0);
            }
        } else if (lowerLimits.size() < upperLimits.size()) {
            lowerLimits = new ArrayList<Integer>();
            for (int i = 0; i < upperLimits.size(); i++) {
                lowerLimits.add(0);
            }
        } else if (lowerLimits.size() > upperLimits.size()) {
            upperLimits = new ArrayList<Integer>();
            for (int i = 0; i < lowerLimits.size(); i++) {
                upperLimits.add(0);
            }
        }
        results = new ArrayList<List<Integer>>();
        currentLoop = new ArrayList<Integer>(lowerLimits);
        results.add(currentLoop);
        currentLoop = new ArrayList<Integer>(currentLoop);
        while ((currentLoop = simulateLoop(currentLoop, upperLimits, lowerLimits, lowerLimits.size() - 1)) != null) {
            results.add(currentLoop);
            currentLoop = new ArrayList<Integer>(currentLoop);
        }
        return results;
    }

    public List<Integer> simulateLoop(List<Integer> currentList, List<Integer> upper, List<Integer> lower, int index) {
        if (currentList.get(index) != upper.get(index)) {
            currentList.set(index, currentList.get(index) + 1);
            return currentList;
        } else if (index != 0) {
            currentList.set(index, lower.get(index));
            return simulateLoop(currentList, upper, lower, index - 1);
        } else {
            return null;
        }
    }
}
