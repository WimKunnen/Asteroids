package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

/**
 * Created by WimKunnen on 02/05/2017.
 */
public class BulletOfShip implements Expression<EntityType>{

    public BulletOfShip(){
    }

    @Override
    public EntityType calculate(Program program) throws RuntimeException {

        Ship theExecutor = program.getShip();
        World alderaan = theExecutor.getWorld();

        Bullet bullet = (Bullet) alderaan.getAllEntities().stream()
                .filter((Entity e)
                        -> e instanceof Bullet && ((Bullet)e).getSource() == theExecutor && e.getWorld() == alderaan)
                .findAny()
                .orElse(null);

        return new EntityType(bullet);
    }
}
