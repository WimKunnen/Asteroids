package asteroids.model.program.expressions.entityexpressions;

import asteroids.model.*;
import asteroids.model.program.expressions.Expression;
import asteroids.model.program.types.EntityType;

@SuppressWarnings("all")

/**
 * @author WimKunnen and Maarten Doclo
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
