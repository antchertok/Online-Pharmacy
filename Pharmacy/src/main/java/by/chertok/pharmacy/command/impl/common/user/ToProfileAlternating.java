package main.java.by.chertok.pharmacy.command.impl.common.user;
;
import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToProfileAlternating implements ICommand {

    @Override
    public Path execute(Wrapper wrapper) {
        wrapper.setRequestAttribute("alternating", "true");
        return new Path(true, "profile.jsp");
    }
}
