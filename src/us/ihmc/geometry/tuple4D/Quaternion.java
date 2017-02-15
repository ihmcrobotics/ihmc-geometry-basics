package us.ihmc.geometry.tuple4D;

import java.io.Serializable;

import us.ihmc.geometry.axisAngle.interfaces.AxisAngleReadOnly;
import us.ihmc.geometry.interfaces.GeometryObject;
import us.ihmc.geometry.matrix.interfaces.RotationMatrixReadOnly;
import us.ihmc.geometry.tools.EuclidCoreIOTools;
import us.ihmc.geometry.tuple3D.interfaces.Vector3DReadOnly;
import us.ihmc.geometry.tuple4D.interfaces.QuaternionBasics;
import us.ihmc.geometry.tuple4D.interfaces.QuaternionReadOnly;
import us.ihmc.geometry.tuple4D.interfaces.Tuple4DReadOnly;

/**
 * Class used to represent unit-quaternions which are used to represent 3D orientations.
 * <p>
 * This version of quaternion uses double precision fields to save the value of each component. It
 * is meant for garbage free usage.
 * </p>
 *
 * @author Sylvain Bertrand
 *
 */
public class Quaternion implements Serializable, QuaternionBasics, GeometryObject<Quaternion>
{
   private static final long serialVersionUID = -3523313039213464150L;

   /** The x-component. */
   private double x;
   /** The y-component. */
   private double y;
   /** The z-component. */
   private double z;
   /** The s-component. */
   private double s;

   /**
    * Creates a new quaternion and initializes it to the neutral quaternion which represents a
    * 'zero' rotation.
    */
   public Quaternion()
   {
      setToZero();
   }

   /**
    * Creates a new quaternion and initializes it with the given components.
    * <p>
    * The quaternion is immediately normalized.
    * </p>
    *
    * @param x the x-component.
    * @param y the y-component.
    * @param z the z-component.
    * @param s the s-component.
    */
   public Quaternion(double x, double y, double z, double s)
   {
      set(x, y, z, s);
   }

   /**
    * Creates a new quaternion and initializes its component {@code x}, {@code y}, {@code z},
    * {@code s} in order from the given array.
    * <p>
    * The quaternion is immediately normalized.
    * </p>
    *
    * @param pointArray the array containing this quaternion's components. Not modified.
    */
   public Quaternion(double[] quaternionArray)
   {
      set(quaternionArray);
   }

   /**
    * Creates a new quaternion and initializes it to {@code other}.
    *
    * @param other the quaternion to copy the components from. Not modified.
    */
   public Quaternion(QuaternionReadOnly other)
   {
      set(other);
   }

   /**
    * Creates a new quaternion and initializes such that it represents the same orientation as the
    * given {@code rotationMatrix}.
    *
    * @param rotationMatrix the rotation matrix to initialize this quaternion. Not modified.
    */
   public Quaternion(RotationMatrixReadOnly rotationMatrix)
   {
      set(rotationMatrix);
   }

   /**
    * Creates a new quaternion and initializes such that it represents the same orientation as the
    * given {@code axisAngle}.
    *
    * @param axisAngle the axis-angle to initialize this quaternion. Not modified.
    */
   public Quaternion(AxisAngleReadOnly axisAngle)
   {
      set(axisAngle);
   }

   /**
    * Creates a new quaternion and initializes such that it represents the same orientation as the
    * given {@code rotationVector}.
    * <p>
    * WARNING: a rotation vector is different from a yaw-pitch-roll or Euler angles representation.
    * A rotation vector is equivalent to the axis of an axis-angle that is multiplied by the angle
    * of the same axis-angle.
    * </p>
    *
    * @param rotationVector the rotation vector to initialize this quaternion. Not modified.
    */
   public Quaternion(Vector3DReadOnly rotationVector)
   {
      set(rotationVector);
   }

   /**
    * Sets this quaternion to {@code other}.
    *
    * @param other the other quaternion to copy the values from. Not modified.
    */
   @Override
   public void set(Quaternion other)
   {
      QuaternionBasics.super.set(other);
   }

   /** {@inheritDoc} */
   @Override
   public void setUnsafe(double qx, double qy, double qz, double qs)
   {
      x = qx;
      y = qy;
      z = qz;
      s = qs;
   }

   /** {@inheritDoc} */
   @Override
   public double getX()
   {
      return x;
   }

   /** {@inheritDoc} */
   @Override
   public double getY()
   {
      return y;
   }

   /** {@inheritDoc} */
   @Override
   public double getZ()
   {
      return z;
   }

   /** {@inheritDoc} */
   @Override
   public double getS()
   {
      return s;
   }

   /**
    * Tests if the given {@code object}'s class is the same as this, in which case the method
    * returns {@link #equals(Tuple4DReadOnly)}, it returns {@code false} otherwise.
    *
    * @param object the object to compare against this. Not modified.
    * @return {@code true} if {@code object} and this are exactly equal, {@code false} otherwise.
    */
   @Override
   public boolean equals(Object object)
   {
      try
      {
         return equals((Tuple4DReadOnly) object);
      }
      catch (ClassCastException e)
      {
         return false;
      }
   }

   /**
    * Tests on a per component basis if this quaternion is equal to the given {@code other} to an
    * {@code epsilon}.
    *
    * @param other the other quaternion to compare against this. Not modified.
    * @param epsilon the tolerance to use when comparing each component.
    * @return {@code true} if the two tuples are equal, {@code false} otherwise.
    */
   @Override
   public boolean epsilonEquals(Quaternion other, double epsilon)
   {
      return QuaternionBasics.super.epsilonEquals(other, epsilon);
   }

   /**
    * Provides a {@code String} representation of this quaternion converted to yaw-pitch-roll angles
    * as follows: yaw-pitch-roll: (yaw, pitch, roll).
    *
    * @return
    */
   public String toStringAsYawPitchRoll()
   {
      return "yaw-pitch-roll: (" + getYaw() + ", " + getPitch() + ", " + getRoll() + ")";
   }

   /**
    * Provides a {@code String} representation of this quaternion as follows: (x, y, z, s).
    *
    * @return the {@code String} representing this quaternion.
    */
   @Override
   public String toString()
   {
      return EuclidCoreIOTools.getTuple4DString(this);
   }

   /**
    * Calculates and returns a hash code value from the value of each component of this quaternion.
    *
    * @return the hash code value for this quaternion.
    */
   @Override
   public int hashCode()
   {
      long bits = 1L;
      bits = 31L * bits + Double.doubleToLongBits(x);
      bits = 31L * bits + Double.doubleToLongBits(y);
      bits = 31L * bits + Double.doubleToLongBits(z);
      bits = 31L * bits + Double.doubleToLongBits(s);
      return (int) (bits ^ bits >> 32);
   }
}
