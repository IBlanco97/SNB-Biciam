package operators.factory;

import operators.heuristics.HeuristicOperator;
import operators.heuristics.HeuristicOperatorType;
import operators.initialSolution.InitialSolution;
import operators.initialSolution.InitialSolutionType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InitialSolutionFactory {

    private InitialSolutionFactory(){

    }

    public static InitialSolution getInstance(InitialSolutionType type){
        InitialSolution solution =null;
        try{
            solution = (InitialSolution) InitialSolution.class.getClassLoader().loadClass(type.toString()).newInstance();
        }catch (IllegalAccessException | ClassNotFoundException | InstantiationException e){
            Logger.getLogger(HeuristicOperator.class.getName()).log(Level.SEVERE, null, e);
        }

        return solution;
    }
}
