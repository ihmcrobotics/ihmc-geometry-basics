package us.ihmc.geometry.axisAngle;

import us.ihmc.geometry.axisAngle.interfaces.AxisAngleReadOnly;

public abstract class AxisAngleTools
{
   /**
    * Calculates the norm squared of the axis in {@code axisAngle}.
    * 
    * @param axisAngle the axis-angle with the axis to compute the norm squared of. Not modified.
    * @return the norm squared of the axis in {@code axisAngle}.
    */
   public static double axisNormSquared(AxisAngleReadOnly<?> axisAngle)
   {
      return axisNormSquared(axisAngle.getX(), axisAngle.getY(), axisAngle.getZ(), axisAngle.getAngle());
   }

   /**
    * Calculates the norm squared of the axis in {@code axisAngle}.
    * 
    * @param ux x-component of {@code axisAngle}.
    * @param uy y-component of {@code axisAngle}.
    * @param uz z-component of {@code axisAngle}.
    * @param angle of {@code axisAngle}.
    * @return the norm squared of the axis in {@code axisAngle}.
    */
   public static double axisNormSquared(double ux, double uy, double uz, double angle)
   {
      return ux * ux + uy * uy + uz * uz;
   }

   /**
    * Calculates the norm of the axis in {@code axisAngle}.
    * 
    * @param axisAngle the axis-angle with the axis to compute the norm of. Not modified.
    * @return the norm of the axis in {@code axisAngle}.
    */
   public static double axisNorm(AxisAngleReadOnly<?> axisAngle)
   {
      return axisNorm(axisAngle.getX(), axisAngle.getY(), axisAngle.getZ(), axisAngle.getAngle());
   }

   /**
    * Calculates the norm of the axis in {@code axisAngle}.
    * 
    * @param ux x-component of {@code axisAngle}.
    * @param uy y-component of {@code axisAngle}.
    * @param uz z-component of {@code axisAngle}.
    * @param angle of {@code axisAngle}.
    * @return the norm of the axis in {@code axisAngle}.
    */
   public static double axisNorm(double ux, double uy, double uz, double angle)
   {
      return Math.sqrt(axisNormSquared(ux, uy, uz, angle));
   }

   /**
    * Tests if the axis of {@code axisAngle} is of unit-length.
    * 
    * @param axisAngle the axis-angle with the axis to test. Not modified.
    * @param epsilon tolerance to use in this test.
    * @return {@code true} if the axis is unitary, {@code false} otherwise.
    */
   public static boolean isAxisUnitary(AxisAngleReadOnly<?> axisAngle, double epsilon)
   {
      return isAxisUnitary(axisAngle.getX(), axisAngle.getY(), axisAngle.getZ(), axisAngle.getAngle(), epsilon);
   }

   /**
    * Tests if the axis of {@code axisAngle} is of unit-length.
    * 
    * @param ux x-component of {@code axisAngle}.
    * @param uy y-component of {@code axisAngle}.
    * @param uz z-component of {@code axisAngle}.
    * @param angle of {@code axisAngle}.
    * @param epsilon tolerance to use in this test.
    * @return {@code true} if the axis is unitary, {@code false} otherwise.
    */
   public static boolean isAxisUnitary(double ux, double uy, double uz, double angle, double epsilon)
   {
      return Math.abs(1.0 - axisNorm(ux, uy, uz, angle)) < epsilon;
   }
}
