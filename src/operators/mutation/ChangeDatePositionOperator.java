package operators.mutation;

import controller.MutationsConfigurationController;
import definition.TTPDefinition;
import definition.state.CalendarState;
import definition.state.statecode.Date;
import problem.definition.State;
import utils.CalendarConfiguration;

import java.util.concurrent.ThreadLocalRandom;

public class ChangeDatePositionOperator extends MutationOperator {


    @Override
    public State applyMutation(State state) {
        State resultState = state.clone();
        CalendarConfiguration configuration = ((CalendarState)resultState).getConfiguration();
        int selectedDate = -1;
        int dateToChange = -1;

        int startPosition = 0;

        if(configuration.isInauguralGame()){
            startPosition = 1;
        }
        if (!TTPDefinition.getInstance().getMutationsConfigurationsList().isEmpty()) {
            int position = MutationsConfigurationController.currentMutationPostion;
            selectedDate = TTPDefinition.getInstance().getMutationsConfigurationsList().get(position).get(0);
            dateToChange = TTPDefinition.getInstance().getMutationsConfigurationsList().get(position).get(1);

        }


        if (selectedDate == -1) {
            selectedDate = ThreadLocalRandom.current().nextInt(startPosition, resultState.getCode().size());
        }

        if (dateToChange == -1) {
            do {
                dateToChange = ThreadLocalRandom.current().nextInt(startPosition, resultState.getCode().size());

            } while ((resultState.getCode().size() > 3) && ((selectedDate - dateToChange) <= 1) && ((selectedDate - dateToChange) >= (-1)));
        }



        Date date = (Date)resultState.getCode().get(selectedDate);

        if (dateToChange < resultState.getCode().size() - 1) {
            if (selectedDate < dateToChange) {
                resultState.getCode().add(dateToChange + 1, date);
            } else {
                resultState.getCode().add(dateToChange, date);
            }
        } else {
            resultState.getCode().add(dateToChange, date);
            resultState.getCode().add(resultState.getCode().size() - 2, resultState.getCode().get(resultState.getCode().size() - 1));
            resultState.getCode().remove(resultState.getCode().size() - 1);
        }


        if (dateToChange >= selectedDate) {
            resultState.getCode().remove(selectedDate);
        } else {
            resultState.getCode().remove(selectedDate + 1);
        }


        return resultState;
    }
}
