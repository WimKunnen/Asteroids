package asteroids.model.program;

import asteroids.model.program.statements.Statement;

import java.util.List;

/**
 * Created by WimKunnen on 25/04/2017.
 */
public class FunctionDefinition {

    public FunctionDefinition(String name,
            Statement body){
        this.setName(name);
        this.setBody(body);
    }

    private String name;
    //private List<?> arguments;

    private Statement body;

    public String getName(){
        return this.name;
    }

    public Statement getBody() {
        return body;
    }

    private void setName(String newName){
        this.name = newName;
    }

    private void setBody(Statement newBody){
        this.body = newBody;
    }

}
