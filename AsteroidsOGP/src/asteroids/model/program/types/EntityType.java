package asteroids.model.program.types;

import asteroids.model.Entity;
import be.kuleuven.cs.som.annotate.Value;

/**
 * Created by WimKunnen on 23/04/2017.
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
