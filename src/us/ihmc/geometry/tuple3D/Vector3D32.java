package us.ihmc.geometry.tuple3D;

import java.io.Serializable;

import us.ihmc.geometry.tuple2D.Vector2D32;
import us.ihmc.geometry.tuple3D.interfaces.Tuple3DReadOnly;
import us.ihmc.geometry.tuple3D.interfaces.Vector3DBasics;

/**
 * A 3D vector represents a physical quantity with a magnitude and a direction. For instance, it can
 * be used to represent a 3D velocity, force, or translation from one 3D point to another.
 * <p>
 * This version of 3D vector uses double precision fields to save the value of each component. It is
 * meant for garbage free usage and for situations where heap memory is limited. When memory is not
 * a constraint, the use of {@link Vector2D32} is preferable.
 * </p>
 * 
 * @author Sylvain Bertrand
 */
public class Vector3D32 implements Serializable, Vector3DBasics<Vector3D32>
{
   private static final long serialVersionUID = 1186918378545386628L;

   /** The x-component. */
   private float x;
   /** The y-component. */
   private float y;
   /** The z-component. */
   private float z;

   /**
    * Creates a new vector and initializes it components to zero.
    */
   public Vector3D32()
   {
      setToZero();
   }

   /**
    * Creates a new vector and initializes it with the given components.
    * 
    * @param x the x-component.
    * @param y the y-component.
    * @param z the z-component.
    */
   public Vector3D32(float x, float y, float z)
   {
      set(x, y, z);
   }

   /**
    * Creates a new vector and initializes it from the given array.
    * 
    * @param pointArray the array containing this vector's components. Not modified.
    */
   public Vector3D32(float[] vectorArray)
   {
      set(vectorArray);
   }

   /**
    * Creates a new vector and initializes it to {@code other}
    * 
    * @param other the tuple to copy the components from. Not modified.
    */
   public Vector3D32(Tuple3DReadOnly<?> tuple)
   {
      set(tuple);
   }

   /**
    * Sets the x-component of this vector.
    * 
    * @param x the x-component.
    */
   @Override
   public void setX(double x)
   {
      this.x = (float) x;
   }

   /**
    * Sets the y-component of this vector.
    * 
    * @param y the y-component.
    */
   @Override
   public void setY(double y)
   {
      this.y = (float) y;
   }

   /**
    * Sets the z-component of this vector.
    * 
    * @param z the z-component.
    */
   @Override
   public void setZ(double z)
   {
      this.z = (float) z;
   }

   /**
    * Sets the x-component of this vector.
    * 
    * @param x the x-component.
    */
   public void setX(float x)
   {
      this.x = x;
   }

   /**
    * Sets the y-component of this vector.
    * 
    * @param y the y-component.
    */
   public void setY(float y)
   {
      this.y = y;
   }

   /**
    * Sets the z-component of this vector.
    * 
    * @param z the z-component.
    */
   public void setZ(float z)
   {
      this.z = z;
   }

   /**
    * Returns the value of the x-component of this vector.
    * 
    * @return the x-component's value.
    */
   @Override
   public double getX()
   {
      return x;
   }

   /**
    * Returns the value of the y-component of this vector.
    * 
    * @return the y-component's value.
    */
   @Override
   public double getY()
   {
      return y;
   }

   /**
    * Returns the value of the z-component of this vector.
    * 
    * @return the z-component's value.
    */
   @Override
   public double getZ()
   {
      return z;
   }

   /**
    * Returns the value of the x-component of this vector.
    * 
    * @return the x-component's value.
    */
   @Override
   public float getX32()
   {
      return x;
   }

   /**
    * Returns the value of the y-component of this vector.
    * 
    * @return the y-component's value.
    */
   @Override
   public float getY32()
   {
      return y;
   }

   /**
    * Returns the value of the z-component of this vector.
    * 
    * @return the z-component's value.
    */
   @Override
   public float getZ32()
   {
      return z;
   }

   /**
    * Tests if the given {@code object}'s class is the same as this, in which case the method
    * returns {@link #equals(Vector3D32)}, it returns {@code false} otherwise.
    * 
    * @param object the object to compare against this. Not modified.
    * @return {@code true} if {@code object} and this are exactly equal, {@code false} otherwise.
    */
   @Override
   public boolean equals(Object object)
   {
      try
      {
         return equals((Vector3D32) object);
      }
      catch (ClassCastException e)
      {
         return false;
      }
   }

   /**
    * Provides a {@code String} representation of this vector 3D as follows: (x, y, z).
    * 
    * @return the {@code String} representing this vector 3D.
    */
   @Override
   public String toString()
   {
      return "(" + x + ", " + y + ", " + z + ")";
   }

   /**
    * Calculates and returns a hash code value from the value of each component of this vector 3D.
    * 
    * @return the hash code value for this vector 3D.
    */
   @Override
   public int hashCode()
   {
      long bits = 1L;
      bits = 31L * bits + Float.floatToIntBits(x);
      bits = 31L * bits + Float.floatToIntBits(y);
      bits = 31L * bits + Float.floatToIntBits(z);
      return (int) (bits ^ bits >> 32);
   }
}
