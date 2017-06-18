package us.ihmc.euclid.tools;

public class EuclidCoreTools
{
   public static final double TwoPI = 2.0 * Math.PI;

   public static final double EPS_NORM_FAST_SQRT = 2.107342e-08;
   public static final double EPS_ANGLE_SHIFT = 1.0e-12;

   /**
    * Calculates and returns the square root of the given value.
    * <p>
    * This method is optimized when {@code squaredValueClosedToOne} is equal to
    * 1+/-{@value #EPS_NORM_FAST_SQRT} by using an approximation of the square root.
    *
    * @param squaredValueClosedToOne the value to calculates the square root of.
    * @return the value of the square root.
    */
   public static double fastSquareRoot(double squaredValueClosedToOne)
   {
      if (Math.abs(1.0 - squaredValueClosedToOne) < EPS_NORM_FAST_SQRT)
         squaredValueClosedToOne = 0.5 * (1.0 + squaredValueClosedToOne);
      else
         squaredValueClosedToOne = Math.sqrt(squaredValueClosedToOne);

      return squaredValueClosedToOne;
   }

   /**
    * Tests if at least one of the two given elements is equal to {@linkplain Double#NaN}.
    *
    * @param a the first element.
    * @param b the second element.
    * @return {@code true} if at least one element is equal to {@link Double#NaN}, {@code false}
    *         otherwise.
    */
   public static boolean containsNaN(double a, double b)
   {
      return Double.isNaN(a) || Double.isNaN(b);
   }

   /**
    * Tests if at least one of the three given elements is equal to {@linkplain Double#NaN}.
    *
    * @param a the first element.
    * @param b the second element.
    * @param c the third element.
    * @return {@code true} if at least one element is equal to {@link Double#NaN}, {@code false}
    *         otherwise.
    */
   public static boolean containsNaN(double a, double b, double c)
   {
      return Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c);
   }

   /**
    * Tests if at least one of the four given elements is equal to {@linkplain Double#NaN}.
    *
    * @param a the first element.
    * @param b the second element.
    * @param c the third element.
    * @param d the fourth element.
    * @return {@code true} if at least one element is equal to {@link Double#NaN}, {@code false}
    *         otherwise.
    */
   public static boolean containsNaN(double a, double b, double c, double d)
   {
      return Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d);
   }

   /**
    * Tests if at least one of the nine given elements is equal to {@linkplain Double#NaN}.
    *
    * @param a0 the first element.
    * @param a1 the second element.
    * @param a2 the third element.
    * @param a3 the fourth element.
    * @param a4 the fifth element.
    * @param a5 the sixth element.
    * @param a6 the seventh element.
    * @param a7 the eighth element.
    * @param a8 the ninth element.
    * @return {@code true} if at least one element is equal to {@link Double#NaN}, {@code false}
    *         otherwise.
    */
   public static boolean containsNaN(double a0, double a1, double a2, double a3, double a4, double a5, double a6, double a7, double a8)
   {
      if (Double.isNaN(a0) || Double.isNaN(a1) || Double.isNaN(a2))
         return true;
      if (Double.isNaN(a3) || Double.isNaN(a4) || Double.isNaN(a5))
         return true;
      if (Double.isNaN(a6) || Double.isNaN(a7) || Double.isNaN(a8))
         return true;
      return false;
   }

   /**
    * Tests if at least one element in the given array is equal to {@link Double#NaN}.
    *
    * @param array the array containing the elements to test for {@link Double#NaN}. Not modified.
    * @return {@code true} if at least one element is equal to {@link Double#NaN}, {@code false}
    *         otherwise.
    */
   public static boolean containsNaN(double[] array)
   {
      for (int i = 0; i < array.length; i++)
      {
         if (Double.isNaN(array[i]))
            return true;
      }
      return false;
   }

   /**
    * Calculates and returns the norm squared of the given two elements.
    * <p>
    * norm<sup>2</sup> = x<sup>2</sup> + y<sup>2</sup>
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @return the value of the square of the norm.
    */
   public static double normSquared(double x, double y)
   {
      return x * x + y * y;
   }

   /**
    * Calculates and returns the norm squared of the given three elements.
    * <p>
    * norm<sup>2</sup> = x<sup>2</sup> + y<sup>2</sup> + z<sup>2</sup>
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @param z the third element.
    * @return the value of the square of the norm.
    */
   public static double normSquared(double x, double y, double z)
   {
      return x * x + y * y + z * z;
   }

   /**
    * Calculates and returns the norm squared of the given four elements.
    * <p>
    * norm<sup>2</sup> = x<sup>2</sup> + y<sup>2</sup> + z<sup>2</sup> + s<sup>2</sup>
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @param z the third element.
    * @param s the fourth element.
    * @return the value of the square of the norm.
    */
   public static double normSquared(double x, double y, double z, double s)
   {
      return x * x + y * y + z * z + s * s;
   }

   /**
    * Calculates and returns the norm squared of the given two elements.
    * <p>
    * norm = &radic;(x<sup>2</sup> + y<sup>2</sup>)
    * </p>
    * <p>
    * This method is optimized for calculating norms closed to 1 by using
    * {@link #fastSquareRoot(double)}. For computing norms that are not closed to 1, prefer using
    * the usual {@link Math#sqrt(double)} on the norm squared.
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @return the value of the square of the norm.
    */
   public static double norm(double x, double y)
   {
      return fastSquareRoot(normSquared(x, y));
   }

   /**
    * Calculates and returns the norm squared of the given three elements.
    * <p>
    * norm = &radic;(x<sup>2</sup> + y<sup>2</sup> + z<sup>2</sup>)
    * </p>
    * <p>
    * This method is optimized for calculating norms closed to 1 by using
    * {@link #fastSquareRoot(double)}. For computing norms that are not closed to 1, prefer using
    * the usual {@link Math#sqrt(double)} on the norm squared.
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @param z the third element.
    * @return the value of the square of the norm.
    */
   public static double norm(double x, double y, double z)
   {
      return fastSquareRoot(normSquared(x, y, z));
   }

   /**
    * Calculates and returns the norm squared of the given four elements.
    * <p>
    * norm = &radic;(x<sup>2</sup> + y<sup>2</sup> + z<sup>2</sup> + s<sup>2</sup>)
    * </p>
    * <p>
    * This method is optimized for calculating norms closed to 1 by using
    * {@link #fastSquareRoot(double)}. For computing norms that are not closed to 1, prefer using
    * the usual {@link Math#sqrt(double)} on the norm squared.
    * </p>
    *
    * @param x the first element.
    * @param y the second element.
    * @param z the third element.
    * @param s the fourth element.
    * @return the value of the square of the norm.
    */
   public static double norm(double x, double y, double z, double s)
   {
      return fastSquareRoot(normSquared(x, y, z, s));
   }

   /**
    * Recomputes the angle value {@code angleToShift} such that the result is in [ -<i>pi</i>,
    * <i>pi</i> [ and still represent the same physical angle as {@code angleToShift}.
    * <p>
    * Edge cases:
    * <ul>
    * <li>if {@code Math.abs(angleToShift + Math.PI) <} {@link #EPS_ANGLE_SHIFT}, the returned angle
    * is {@code -Math.PI}.
    * <li>if {@code Math.abs(angleToShift - Math.PI) <} {@link #EPS_ANGLE_SHIFT}, the returned angle
    * is {@code -Math.PI}.
    * </ul>
    * </p>
    *
    * @param angleToShift the angle to shift.
    * @param angleStart the lowest admissible angle value.
    * @return the result that is in [ -<i>pi</i>, <i>pi</i> [
    */
   public static double trimAngleMinusPiToPi(double angleToShift)
   {
      return shiftAngleInRange(angleToShift, -Math.PI);
   }

   /**
    * Computes the angle difference:<br>
    * {@code difference = angleA - angleB}<br>
    * and shift the result to be contained in [ -<i>pi</i>, <i>pi</i> [.
    *
    * @param angleA the first angle in the difference.
    * @param angleB the second angle in the difference.
    * @return the result of the subtraction contained in [ -<i>pi</i>, <i>pi</i> [.
    */
   public static double angleDifferenceMinusPiToPi(double angleA, double angleB)
   {
      return trimAngleMinusPiToPi(angleA - angleB);
   }

   /**
    * Recomputes the angle value {@code angleToShift} such that the result is in
    * [{@code angleStart}, {@code angleStart} + 2<i>pi</i>[ and still represent the same physical
    * angle as {@code angleToShift}.
    * <p>
    * Edge cases:
    * <ul>
    * <li>if {@code Math.abs(angleToShift - angleStart) <} {@link #EPS_ANGLE_SHIFT}, the returned
    * angle is {@code angleStart}.
    * <li>if {@code Math.abs(angleToShift - angleStart + 2.0 * Math.PI) <} {@link #EPS_ANGLE_SHIFT},
    * the returned angle is {@code angleStart}.
    * </ul>
    * </p>
    *
    * @param angleToShift the angle to shift.
    * @param angleStart the lowest admissible angle value.
    * @return the result that is in [{@code angleStart}, {@code angleStart} + 2<i>pi</i>[
    */
   public static double shiftAngleInRange(double angleToShift, double angleStart)
   {
      angleStart = angleStart - EPS_ANGLE_SHIFT;

      double deltaFromStart = (angleToShift - angleStart) % TwoPI;

      if (deltaFromStart < 0)
         deltaFromStart += TwoPI;

      return angleStart + deltaFromStart;
   }

   /**
    * Find and return the argument with the maximum value.
    *
    * @param a the first argument to compare.
    * @param b the second argument to compare.
    * @param c the third argument to compare.
    * @return the maximum value of the three arguments.
    */
   public static final double max(double a, double b, double c)
   {
      if (a > b)
         return a > c ? a : c;
      else
         return b > c ? b : c;
   }

   /**
    * Find and return the argument with the minimum value.
    *
    * @param a the first argument to compare.
    * @param b the second argument to compare.
    * @param c the third argument to compare.
    * @return the minimum value of the three arguments.
    */
   public static final double min(double a, double b, double c)
   {
      if (a < b)
         return a < c ? a : c;
      else
         return b < c ? b : c;
   }
}
