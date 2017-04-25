package asteroids.model.program;

import asteroids.model.program.statements.Statement;

import java.util.List;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionDefinition {

    public FunctionDefinition(String name, List<?> arguments, List<Statement> body){
        this.setName(name);
        this.setArguments(arguments);
        this.setBody(body);
    }

    private String name;
    private List<?> arguments;
    private List<Statement> body;

    public String getName(){
        return this.name;
    }
    public List<?> getArguments() {
        return arguments;
    }
    public List<Statement> getBody() {
        return body;
    }

    private void setName(String newName){
        this.name = newName;
    }
    private void setArguments(List<?> newArguments){
        this.arguments = newArguments;
    }
    private void setBody(List<Statement> newBody){
        this.body = newBody;
    }

}
