package view;

import java.util.ArrayList;
import java.util.Arrays;

import model.Boat;
import model.Boat.BoatType;

public class EngBoatConsole {

	//TODO old menu standard
	//TODO only accepts 0 as "go back" during input
    public Boat displayRegisterBoat(EngConsole console) {
		console.printSeparation();
		console.displayTitle("Register Boat");

		BoatType[] availableBoatTypes = BoatType.values();
		ArrayList<Object> menuOptions = new ArrayList<>(Arrays.asList(availableBoatTypes));
		menuOptions.add("Cancel");
		console.displayMenuOptions(menuOptions.toArray());
		
		int chosenOption = console.promptForValidMenuInput("Choose boat type", menuOptions.size() - 1);
		if(chosenOption == 0) {
			return null;
		}
		BoatType chosenBoatType = availableBoatTypes[chosenOption - 1];
		
		double enteredBoatSize = promptForValidBoatSize(console);
		if(enteredBoatSize == 0) {
			return null;
		}
		
		return new Boat(chosenBoatType, enteredBoatSize);
	}

	//TODO old menu standard. bad double menu
	public Boat displayEditBoat(EngConsole console, Boat currentBoat) {
		console.printSeparation();
		console.displayTitle("Edit \'" + currentBoat + "\'");

		String[] menuOptions = { 
			"Edit Type",
			"Edit Size",
			"Back"
		};
		console.displayMenuOptions(menuOptions);

		int chosenOption = console.promptForValidMenuInput("", menuOptions.length - 1);
		Boat tempBoat = new Boat(currentBoat.getType(), currentBoat.getSize());

		if(chosenOption == 1) {
			console.printSeparation();
			console.displayTitle("Select new type");
			BoatType[] availableBoatTypes = BoatType.values();
			ArrayList<Object> menuOptions2 = new ArrayList<>(Arrays.asList(availableBoatTypes));
			menuOptions2.add("Cancel");
			console.displayMenuOptions(menuOptions2.toArray());

			int chosenOption2 = console.promptForValidMenuInput("", menuOptions2.size() - 1);
			if(chosenOption2 == 0) {
				return null;
			}
			BoatType chosenBoatType = availableBoatTypes[chosenOption2 - 1];
			tempBoat.editType(chosenBoatType);
		}
		else if(chosenOption == 2) {
			console.printSeparation();
			console.displayTitle("Enter new size");
			double enteredBoatSize = promptForValidBoatSize(console);
			if(enteredBoatSize == 0)
				return null;
			tempBoat.editSize(enteredBoatSize);
		}
		else {
			return null;
		}
		
		return tempBoat;
	}

	//TODO old menu standard
    public Boat displayBoatSelection(EngConsole console, ArrayList<Boat> availableBoats) {
		console.printSeparation();
		console.displayTitle("Select boat");

		String[] options = new String[availableBoats.size() + 1];
		for(int i = 0; i < availableBoats.size(); i ++) {
			options[i] = availableBoats.get(i).toString();
		}
		options[availableBoats.size()] = "Cancel";
		int chosenOption = console.createMenu("Select boat", "", options);

		while(!console.isValidMenuChoice(chosenOption, availableBoats.size())) {
            console.displayInvalidMenuChoiceError();
            // console.displayMenuOptions(menuOptions.toArray());
			// chosenOption = displayBoatChoicePrompt(console);
			chosenOption = console.getMenuInput();
		}
		if(chosenOption == 0)
			return null;
		return availableBoats.get(chosenOption - 1);
    }
    
    private double promptForValidBoatSize(EngConsole console) {
		double enteredBoatSize = displayBoatSizePrompt(console);
		while (enteredBoatSize < 0) {
			console.displayInvalidInputError();
			enteredBoatSize = displayBoatSizePrompt(console);
		}
		return enteredBoatSize;
    }
    
	private double displayBoatSizePrompt(EngConsole console) {
		System.out.print("Enter new boat size (in meters): ");
		return console.getInputDouble();
	}
}