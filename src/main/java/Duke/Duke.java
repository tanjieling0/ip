package Duke;

import Duke.Command.Command;
import Duke.Task.TaskList;
import Duke.Exception.*;

public class Duke {

    private Storage storage;
    private TaskList tasks;

    // deals with interactions with the user
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        boolean isActive = true;
        ui.greet();

        while (isActive) {
            try {
                String userInput = this.ui.getInput();
                Command command = Parser.parseCommand(userInput);
                command.execute(this.storage, this.tasks, this.ui);
                isActive = command.getIsActive();
            } catch (DukeException e) {
                Ui.printError(e);
            }
        }

    }

    public static void main(String[] args) {
        new Duke("./data/tasks.txt").run();
    }
}

