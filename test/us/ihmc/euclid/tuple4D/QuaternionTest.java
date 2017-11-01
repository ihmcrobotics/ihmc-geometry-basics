package us.ihmc.euclid.tuple4D;

import org.junit.Test;
import us.ihmc.euclid.matrix.RotationMatrix;
import us.ihmc.euclid.rotationConversion.QuaternionConversion;
import us.ihmc.euclid.tools.EuclidCoreRandomTools;
import us.ihmc.euclid.tools.EuclidCoreTestTools;
import us.ihmc.euclid.tools.EuclidCoreTools;
import us.ihmc.euclid.tuple3D.Vector3D;

import java.util.Random;

import static org.junit.Assert.*;

public class QuaternionTest extends QuaternionBasicsTest<Quaternion>
{
   public static final int NUMBER_OF_ITERATIONS = 100;
   public static final double EPS = 1e-14;

   @Test
   public void testQuaternion()
   {
      Random random = new Random(613615L);
      Quaternion quaternion = new Quaternion();
      Quaternion quaternionCopy;
      Quaternion expected = new Quaternion();

      { // Test Quaternion()
         expected.setToZero();
         EuclidCoreTestTools.assertQuaternionEquals(quaternion, expected, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test Quaternion(QuaternionBasics other)
         quaternion = quaternionCopy = EuclidCoreRandomTools.generateRandomQuaternion(random);
         Quaternion quaternion2 = new Quaternion(quaternion);

         EuclidCoreTestTools.assertQuaternionEquals(quaternion, quaternion2, EPS);
         EuclidCoreTestTools.assertQuaternionEquals(quaternion, quaternionCopy, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test Quaternion(double x, double y, double z, double s)
         expected = EuclidCoreRandomTools.generateRandomQuaternion(random);
         expected.normalizeAndLimitToPi();
         quaternion = new Quaternion(expected.getX(), expected.getY(), expected.getZ(), expected.getS());

         EuclidCoreTestTools.assertQuaternionEquals(quaternion, expected, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test Quaternion(double[] quaternionArray)
         expected = EuclidCoreRandomTools.generateRandomQuaternion(random);

         double[] quaternionArray;
         quaternionArray = new double[] {expected.getX(), expected.getY(), expected.getZ(), expected.getS()};

         quaternion = new Quaternion(quaternionArray);

         EuclidCoreTestTools.assertQuaternionEquals(expected, quaternion, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test Quaternion(RotationMatrix rotationMatrix)
         RotationMatrix rotationMatrix, rotationMatrixCopy;
         rotationMatrix = rotationMatrixCopy = EuclidCoreRandomTools.generateRandomRotationMatrix(random);

         quaternion = new Quaternion(rotationMatrix);
         QuaternionConversion.convertMatrixToQuaternion(rotationMatrix, expected);

         EuclidCoreTestTools.assertQuaternionEquals(expected, quaternion, EPS);
         EuclidCoreTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test Quaternion(VectorBasics rotationVector)
         Vector3D rotationVector, rotationVectorCopy;
         rotationVector = rotationVectorCopy = EuclidCoreRandomTools.generateRandomRotationVector(random);

         quaternion = new Quaternion(rotationVector);
         QuaternionConversion.convertRotationVectorToQuaternion(rotationVector, expected);

         EuclidCoreTestTools.assertQuaternionEquals(quaternion, expected, EPS);
         EuclidCoreTestTools.assertRotationVectorEquals(rotationVector, rotationVectorCopy, EPS);
      }
   }

   @Test
   public void testHashCode() throws Exception
   {
      Random random = new Random(621541L);
      Quaternion q = EuclidCoreRandomTools.generateRandomQuaternion(random);

      int newHashCode, previousHashCode;
      newHashCode = q.hashCode();
      assertEquals(newHashCode, q.hashCode());

      previousHashCode = q.hashCode();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         double qx = q.getX();
         double qy = q.getY();
         double qz = q.getZ();
         double qs = q.getS();
         switch (random.nextInt(4))
         {
         case 0:
            qx = random.nextDouble();
            break;
         case 1:
            qy = random.nextDouble();
            break;
         case 2:
            qz = random.nextDouble();
            break;
         case 3:
            qs = random.nextDouble();
            break;
         }
         q.setUnsafe(qx, qy, qz, qs);
         newHashCode = q.hashCode();
         assertNotEquals(newHashCode, previousHashCode);
         previousHashCode = newHashCode;
      }
   }

   @Test
   public void testGeometricallyEquals() throws Exception
   {
      Quaternion quaternionA;
      Quaternion quaternionB;
      Random random = new Random(621541L);

      for (int i = 0; i < 100; ++i)
      {
         quaternionA = EuclidCoreRandomTools.generateRandomQuaternion(random);
         quaternionB = EuclidCoreRandomTools.generateRandomQuaternion(random);

         if (quaternionA.epsilonEquals(quaternionB, getEpsilon()))
         {
            assertTrue(quaternionA.geometricallyEquals(quaternionB, Math.sqrt(3) * getEpsilon()));
         }
         else
         {
            if (Math.sqrt(EuclidCoreTools.normSquared(quaternionA.getX() - quaternionB.getX(), quaternionA.getY() - quaternionB.getY())) <= getEpsilon())
            {
               assertTrue(quaternionA.geometricallyEquals(quaternionB, getEpsilon()));
            }
            else
            {
               assertFalse(quaternionA.geometricallyEquals(quaternionB, getEpsilon()));
            }
         }
      }
   }

   @Override
   public Quaternion createEmptyTuple()
   {
      return new Quaternion();
   }

   @Override
   public Quaternion createRandomTuple(Random random)
   {
      return EuclidCoreRandomTools.generateRandomQuaternion(random);
   }

   @Override
   public Quaternion createTuple(double x, double y, double z, double s)
   {
      Quaternion quaternion = new Quaternion();
      quaternion.setUnsafe(x, y, z, s);
      return quaternion;
   }

   @Override
   public double getEpsilon()
   {
      return 1.0e-15;
   }
}
