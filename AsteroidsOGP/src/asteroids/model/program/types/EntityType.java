package asteroids.model.program.types;

import asteroids.model.Entity;
import be.kuleuven.cs.som.annotate.Value;

/**
 * @author WimKunnen and Maarten Doclo
 */
@Value
public class EntityType extends Type<Entity> {

    public static final EntityType NULL_REFERENCE = new EntityType(null);

    public EntityType(Entity entity) {
        super(entity);
    }

    @Override
    public EntityType getNewTypeOfSameClass(Entity entity) {
        return new EntityType(entity);
    }

    @Override
    public EntityType getDefaultTypeForThisClass(){
        return EntityType.NULL_REFERENCE;
    }

}
