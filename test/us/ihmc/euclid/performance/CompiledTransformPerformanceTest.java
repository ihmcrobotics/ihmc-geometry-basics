package us.ihmc.euclid.performance;

import java.util.Random;

import org.junit.Test;

import us.ihmc.euclid.transform.RigidBodyTransform;
import us.ihmc.euclid.tuple3D.Vector3D;

/**
 * This test is designed to measure the speed of transform code after it has been compiled by the
 * JVM.
 *
 * @author Duncan Calvert <a href="mailto:dcalvert@ihmc.us">(dcalvert@ihmc.us)</a>
 */
public class CompiledTransformPerformanceTest
{
   @Test
   public void testTransformingAVectorManyTimes()
   {
      Random random = new Random(1230930210L);

      RigidBodyTransform rigidBodyTransform = new RigidBodyTransform();
      rigidBodyTransform.setRotationYawPitchRollAndZeroTranslation(2.0 * Math.PI * random.nextDouble(), 2.0 * Math.PI * random.nextDouble(),
                                                                   2.0 * Math.PI * random.nextDouble());
      rigidBodyTransform.setTranslation(10000.0 * (random.nextDouble() - 0.5), 10000.0 * (random.nextDouble() - 0.5), 10000.0 * (random.nextDouble() - 0.5));

      Vector3D vector = new Vector3D();
      vector.setX(10000.0 * (random.nextDouble() - 0.5));
      vector.setY(10000.0 * (random.nextDouble() - 0.5));
      vector.setZ(10000.0 * (random.nextDouble() - 0.5));

      int numberOfMeasuredRuns = 20;

      String[] vectorOutput = new String[numberOfMeasuredRuns];

      for (int i = 0; i < numberOfMeasuredRuns; i++)
      {
         long nanoTime = tranformVectorNTimes(rigidBodyTransform, vector, 10000);
         vectorOutput[i] = vector.toString();
         System.out.println(i + ", " + nanoTime + ", ");
      }

      for (int i = 0; i < vectorOutput.length; i++)
      {
         System.out.println("Vector: " + vectorOutput[i]);
      }
   }

   private long tranformVectorNTimes(RigidBodyTransform rigidBodyTransform, Vector3D vector, int numberOfOperationsPerMeasurement)
   {
      long start = System.nanoTime();

      for (int i = 0; i < numberOfOperationsPerMeasurement; i++)
      {
         rigidBodyTransform.transform(vector);
      }

      long end = System.nanoTime();

      return end - start;
   }
}
