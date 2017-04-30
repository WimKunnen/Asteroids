package asteroids.model.program.types;

import be.kuleuven.cs.som.annotate.Value;
import jdk.internal.org.objectweb.asm.TypeReference;

/**
 * Created by WimKunnen on 23/04/2017.
 */

@Value
public abstract class Type<C> {
    public Type(C type) {
        this.type = type;
    }

    public abstract Type<C> getNewTypeOfSameClass(C value);

    public C getType() {
        return this.type;
    }

    private C type;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        // The following type cast will be succesfull and thus the suppresion of the waring is ok,
                // because this class 'Type' is abstract and therefore only subclasses of 'Type' can be used,
                // and if two different subclasses are compared, this method will have quit on the previous
                // line of code.
        Type<C> other = (Type<C>) obj;
        if (getType() == null) {
            if (other.getType() != null)
                return false;
        } else if (!getType().equals(other.getType()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        if (getType() == null)
            return 0;
        else
            return getType().hashCode();
    }

    public abstract Type<C> getDefaultTypeForThisClass();
}
