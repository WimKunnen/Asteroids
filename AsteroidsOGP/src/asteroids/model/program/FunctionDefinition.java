package asteroids.model.program;

import asteroids.model.program.statements.Sequence;
import asteroids.model.program.statements.Statement;

import java.util.List;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionDefinition {
<<<<<<< Updated upstream

    public FunctionDefinition(String name, //List<?> arguments,
            Statement body){
=======
    //TODO fix constructors
    public FunctionDefinition(String name, List<?> arguments, Sequence body){
>>>>>>> Stashed changes
        this.setName(name);
        //this.setArguments(arguments);
        this.setBody(body);
    }

    public FunctionDefinition(String name, Sequence body){
        this.setName(name);
        this.setBody(body);
    }

    private String name;
<<<<<<< Updated upstream
    //private List<?> arguments;

    private Statement body;
=======
    private List<?> arguments;
    private Sequence body;
>>>>>>> Stashed changes

    public String getName(){
        return this.name;
    }
<<<<<<< Updated upstream
//    public List<?> getArguments() {
//        return arguments;
//    }
    public Statement getBody() {
=======
    public List<?> getArguments() {
        return arguments;
    }
    public Sequence getBody() {
>>>>>>> Stashed changes
        return body;
    }

    private void setName(String newName){
        this.name = newName;
    }
<<<<<<< Updated upstream
//    private void setArguments(List<?> newArguments){
//        this.arguments = newArguments;
//    }
    private void setBody(Statement newBody){
=======
    private void setArguments(List<?> newArguments){
        this.arguments = newArguments;
    }
    private void setBody(Sequence newBody){
>>>>>>> Stashed changes
        this.body = newBody;
    }

}
