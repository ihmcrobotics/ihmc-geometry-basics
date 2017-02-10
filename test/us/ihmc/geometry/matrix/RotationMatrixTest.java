package us.ihmc.geometry.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.ejml.data.DenseMatrix64F;
import org.junit.Assert;
import org.junit.Test;

import us.ihmc.geometry.axisAngle.AxisAngle;
import us.ihmc.geometry.matrix.interfaces.Matrix3DReadOnly;
import us.ihmc.geometry.matrix.interfaces.RotationMatrixReadOnly;
import us.ihmc.geometry.testingTools.GeometryBasicsRandomTools;
import us.ihmc.geometry.testingTools.GeometryBasicsTestTools;
import us.ihmc.geometry.transform.AffineTransform;
import us.ihmc.geometry.transform.QuaternionBasedTransform;
import us.ihmc.geometry.transform.RigidBodyTransform;
import us.ihmc.geometry.tuple2D.Vector2D;
import us.ihmc.geometry.tuple3D.RotationVectorConversion;
import us.ihmc.geometry.tuple3D.Vector3D;
import us.ihmc.geometry.tuple4D.Quaternion;
import us.ihmc.geometry.tuple4D.QuaternionTools;
import us.ihmc.geometry.tuple4D.Vector4D;
import us.ihmc.geometry.yawPitchRoll.YawPitchRollConversion;

public class RotationMatrixTest extends Matrix3DBasicsTest<RotationMatrix>
{
   public static final int NUMBER_OF_ITERATIONS = 100;
   public static final double EPS = 1.0e-10;

   @Test
   public void testRotationMatrix()
   {
      Random random = new Random(46876L);
      RotationMatrix actualRotationMatrix = new RotationMatrix();
      RotationMatrix expectedRotationMatrix = new RotationMatrix();

      { // Test RotationMatrix()
         assertTrue(new RotationMatrix().isIdentity());
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22)
         expectedRotationMatrix = createRandomMatrix(random);
         double m00 = expectedRotationMatrix.getM00();
         double m01 = expectedRotationMatrix.getM01();
         double m02 = expectedRotationMatrix.getM02();
         double m10 = expectedRotationMatrix.getM10();
         double m11 = expectedRotationMatrix.getM11();
         double m12 = expectedRotationMatrix.getM12();
         double m20 = expectedRotationMatrix.getM20();
         double m21 = expectedRotationMatrix.getM21();
         double m22 = expectedRotationMatrix.getM22();
         actualRotationMatrix = new RotationMatrix(m00, m01, m02, m10, m11, m12, m20, m21, m22);
         GeometryBasicsTestTools.assertMatrix3DEquals(expectedRotationMatrix, actualRotationMatrix, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(double[] rotationMatrixArray)
         expectedRotationMatrix = createRandomMatrix(random);
         double[] rotationMatrixArray = new double[50];
         expectedRotationMatrix.get(rotationMatrixArray);
         actualRotationMatrix = new RotationMatrix(rotationMatrixArray);
         GeometryBasicsTestTools.assertMatrix3DEquals(expectedRotationMatrix, actualRotationMatrix, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(DenseMatrix64F rotationMatrix)
         expectedRotationMatrix = createRandomMatrix(random);
         DenseMatrix64F rotationMatrixDenseMatrix = new DenseMatrix64F(3, 3);
         expectedRotationMatrix.get(rotationMatrixDenseMatrix);
         actualRotationMatrix = new RotationMatrix(rotationMatrixDenseMatrix);
         GeometryBasicsTestTools.assertMatrix3DEquals(expectedRotationMatrix, actualRotationMatrix, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(Matrix3DReadOnly<?> rotationMatrix)
         expectedRotationMatrix = createRandomMatrix(random);
         Matrix3D matrix3D = new Matrix3D(expectedRotationMatrix);
         actualRotationMatrix = new RotationMatrix((Matrix3DReadOnly<?>) matrix3D);
         GeometryBasicsTestTools.assertMatrix3DEquals(expectedRotationMatrix, actualRotationMatrix, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(Matrix3DReadOnly<?> rotationMatrix)
         expectedRotationMatrix = createRandomMatrix(random);
         actualRotationMatrix = new RotationMatrix((RotationMatrixReadOnly<?>) expectedRotationMatrix);
         GeometryBasicsTestTools.assertMatrix3DEquals(expectedRotationMatrix, actualRotationMatrix, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(AxisAngleBasics axisAngle)
         AxisAngle axisAngle = GeometryBasicsRandomTools.generateRandomAxisAngle(random);

         actualRotationMatrix = new RotationMatrix(axisAngle);
         RotationMatrixConversion.convertAxisAngleToMatrix(axisAngle, expectedRotationMatrix);

         GeometryBasicsTestTools.assertMatrix3DEquals(actualRotationMatrix, expectedRotationMatrix, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(QuaternionBasics quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);

         actualRotationMatrix = new RotationMatrix(quaternion);
         RotationMatrixConversion.convertQuaternionToMatrix(quaternion, expectedRotationMatrix);

         GeometryBasicsTestTools.assertMatrix3DEquals(actualRotationMatrix, expectedRotationMatrix, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test RotationMatrix(VectorBasics rotationVector)
         Vector3D rotationVector = GeometryBasicsRandomTools.generateRandomVector3D(random);

         actualRotationMatrix = new RotationMatrix(rotationVector);
         RotationMatrixConversion.convertRotationVectorToMatrix(rotationVector, expectedRotationMatrix);

         GeometryBasicsTestTools.assertMatrix3DEquals(actualRotationMatrix, expectedRotationMatrix, EPS);
      }
   }

   @Test
   public void testSetToZero()
   {
      RotationMatrix rotationMatrix = new RotationMatrix();
      RotationMatrix identityMatrix = new RotationMatrix();
      identityMatrix.setToNaN();
      rotationMatrix.setToNaN();

      GeometryBasicsTestTools.assertMatrix3DContainsOnlyNaN(identityMatrix);
      GeometryBasicsTestTools.assertMatrix3DContainsOnlyNaN(rotationMatrix);

      identityMatrix.setIdentity();
      rotationMatrix.setToZero();

      GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, identityMatrix, EPS);
   }

   @Test
   public void testCheckIfMatrixProper() throws Exception
   {
      Random random = new Random(46876L);
      Matrix3D matrix, matrixCopy = new Matrix3D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         matrix = GeometryBasicsRandomTools.generateRandomMatrix3D(random);
         matrixCopy.set(matrix);

         RotationMatrix rotationMatrix = new RotationMatrix();

         try
         {
            rotationMatrix = new RotationMatrix(matrix);
            rotationMatrix.checkIfRotationMatrix();
            Assert.assertTrue(matrix.isRotationMatrix());
         }
         catch (RuntimeException e)
         {
            if (matrix.isRotationMatrix())
               throw e;
            // else it is good
         }

         GeometryBasicsTestTools.assertMatrix3DEquals(matrix, matrixCopy, EPS);
      }
   }

   @Test
   public void testSet() throws Exception
   {
      { // Test set(RotationMatrix other)
         Random random = new Random(648967L);
         Matrix3D expectedMatrix;
         RotationMatrix rotationMatrix = new RotationMatrix(), expectedRotationMatrix;

         for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         {
            expectedMatrix = GeometryBasicsRandomTools.generateRandomMatrix3D(random);
            expectedRotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
            rotationMatrix.setToNaN();

            rotationMatrix.set(expectedRotationMatrix);
            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedRotationMatrix, EPS);

            try
            {
               rotationMatrix.set(expectedMatrix);
               GeometryBasicsTestTools.assertMatrix3DEquals(expectedMatrix, rotationMatrix, EPS);
            }
            catch (RuntimeException e)
            {
               if (expectedMatrix.isRotationMatrix())
                  throw e;
               // else it is good
            }
         }
      }

      { // Test set(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22)
         Random random = new Random(648967L);

         for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         {
            double[] matrixArray = new double[9];
            double[] matrixArrayCopy = new double[9];

            for (int j = 0; j < matrixArray.length; j++)
               matrixArray[j] = matrixArrayCopy[j] = random.nextDouble() + 1;

            RotationMatrix rotationMatrix = new RotationMatrix();
            rotationMatrix.setToNaN();

            try
            {
               rotationMatrix.set(matrixArray[0], matrixArray[1], matrixArray[2], matrixArray[3], matrixArray[4], matrixArray[5], matrixArray[6],
                                  matrixArray[7], matrixArray[8]);

               Assert.assertTrue(rotationMatrix.getM00() == matrixArray[0]);
               Assert.assertTrue(rotationMatrix.getM01() == matrixArray[1]);
               Assert.assertTrue(rotationMatrix.getM02() == matrixArray[2]);
               Assert.assertTrue(rotationMatrix.getM10() == matrixArray[3]);
               Assert.assertTrue(rotationMatrix.getM11() == matrixArray[4]);
               Assert.assertTrue(rotationMatrix.getM12() == matrixArray[5]);
               Assert.assertTrue(rotationMatrix.getM20() == matrixArray[6]);
               Assert.assertTrue(rotationMatrix.getM21() == matrixArray[7]);
               Assert.assertTrue(rotationMatrix.getM22() == matrixArray[8]);
            }
            catch (RuntimeException e)
            {
               if (rotationMatrix.isRotationMatrix())
                  throw e;
               // else it is good
            }

            for (int k = 0; k < matrixArray.length; k++)
               Assert.assertTrue(matrixArray[k] == matrixArrayCopy[k]);
         }
      }

      { // Test set(double[] rotationMatrixArray)
         Random random = new Random(46876L);

         for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         {
            double[] matrixArray = new double[9];
            double[] matrixArrayCopy = new double[9];

            for (int j = 0; j < matrixArray.length; j++)
               matrixArray[j] = matrixArrayCopy[j] = random.nextDouble() + 1;

            RotationMatrix rotationMatrix = new RotationMatrix();
            rotationMatrix.setToNaN();

            try
            {
               rotationMatrix.set(matrixArray);

               Assert.assertTrue(rotationMatrix.getM00() == matrixArray[0]);
               Assert.assertTrue(rotationMatrix.getM01() == matrixArray[1]);
               Assert.assertTrue(rotationMatrix.getM02() == matrixArray[2]);
               Assert.assertTrue(rotationMatrix.getM10() == matrixArray[3]);
               Assert.assertTrue(rotationMatrix.getM11() == matrixArray[4]);
               Assert.assertTrue(rotationMatrix.getM12() == matrixArray[5]);
               Assert.assertTrue(rotationMatrix.getM20() == matrixArray[6]);
               Assert.assertTrue(rotationMatrix.getM21() == matrixArray[7]);
               Assert.assertTrue(rotationMatrix.getM22() == matrixArray[8]);
            }
            catch (RuntimeException e)
            {
               if (rotationMatrix.isRotationMatrix())
                  throw e;
               // else it is good
            }

            for (int k = 0; k < matrixArray.length; k++)
               Assert.assertTrue(matrixArray[k] == matrixArrayCopy[k]);
         }
      }

      { // Test set(DenseMatrix64F matrix)
         Random random = new Random(46876L);

         for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         {
            RotationMatrix actualMatrix = new RotationMatrix();
            RotationMatrix randomRotation = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
            DenseMatrix64F denseMatrix = new DenseMatrix64F(3, 3);
            for (int row = 0; row < 3; row++)
            {
               for (int column = 0; column < 3; column++)
               {
                  denseMatrix.set(row, column, randomRotation.getElement(row, column));
               }
            }

            actualMatrix.set(denseMatrix);

            for (int row = 0; row < 3; row++)
            {
               for (int column = 0; column < 3; column++)
               {
                  assertTrue(denseMatrix.get(row, column) == actualMatrix.getElement(row, column));
               }
            }
         }
      }

      { // Test set(DenseMatrix64F matrix, int startRow, int startColumn)
         Random random = new Random(46876L);

         for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         {
            RotationMatrix actualMatrix = new RotationMatrix();
            RotationMatrix randomRotation = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
            int startRow = random.nextInt(10);
            int startColumn = random.nextInt(10);
            DenseMatrix64F denseMatrix = new DenseMatrix64F(3 + startRow, 3 + startColumn);

            for (int row = 0; row < 3; row++)
            {
               for (int column = 0; column < 3; column++)
               {
                  denseMatrix.set(row + startRow, column + startColumn, randomRotation.getElement(row, column));
               }
            }

            actualMatrix.set(startRow, startColumn, denseMatrix);

            for (int row = 0; row < 3; row++)
            {
               for (int column = 0; column < 3; column++)
               {
                  assertTrue(denseMatrix.get(row + startRow, column + startColumn) == actualMatrix.getElement(row, column));
               }
            }
         }
      }
   }

   @Test
   public void testSetToYawPitchRollMatrix()
   {
      Random random = new Random(35454L);
      RotationMatrix rotationMatrix, rotationMatrixCopy = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);

         { // Test setToPitchMatrix()
            double pitch, pitchCopy;
            pitch = pitchCopy = random.nextDouble();

            rotationMatrix.setToNaN();
            rotationMatrixCopy.setToNaN();

            rotationMatrix.setToPitchMatrix(pitch);
            RotationMatrixConversion.computePitchMatrix(pitch, rotationMatrixCopy);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);

            Assert.assertTrue(pitch == pitchCopy);
         }

         { // Test setToRollMatrix()
            double roll, rollCopy;
            roll = rollCopy = random.nextDouble();

            rotationMatrix.setToNaN();
            rotationMatrixCopy.setToNaN();

            rotationMatrix.setToRollMatrix(roll);
            RotationMatrixConversion.computeRollMatrix(roll, rotationMatrixCopy);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);

            Assert.assertTrue(roll == rollCopy);
         }

         { // Test setToYawMatrix()
            double yaw, yawCopy;
            yaw = yawCopy = random.nextDouble();

            rotationMatrix.setToNaN();
            rotationMatrixCopy.setToNaN();

            rotationMatrix.setToYawMatrix(yaw);
            RotationMatrixConversion.computeYawMatrix(yaw, rotationMatrixCopy);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);

            Assert.assertTrue(yaw == yawCopy);
         }
      }
   }

   @Test
   public void testSetYawPitchRoll()
   {
      Random random = new Random(6465L);
      RotationMatrix rotationMatrix, rotationMatrixCopy = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test setYawPitchRoll (double[] yawPitchRoll)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);

         double[] yawPitchRoll, yawPitchRollCopy;
         yawPitchRoll = yawPitchRollCopy = new double[] {random.nextDouble(), random.nextDouble(), random.nextDouble()};

         rotationMatrix.setYawPitchRoll(yawPitchRoll);
         RotationMatrixConversion.convertYawPitchRollToMatrix(yawPitchRoll, rotationMatrixCopy);

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         Assert.assertTrue(yawPitchRoll == yawPitchRollCopy);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test setYawPitchRoll(double yaw, double pitch, double roll)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);

         double[] yawPitchRoll, yawPitchRollCopy;
         yawPitchRoll = yawPitchRollCopy = new double[] {random.nextDouble(), random.nextDouble(), random.nextDouble()};

         rotationMatrix.setYawPitchRoll(yawPitchRoll[0], yawPitchRoll[1], yawPitchRoll[2]);
         RotationMatrixConversion.convertYawPitchRollToMatrix(yawPitchRoll, rotationMatrixCopy);

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         Assert.assertTrue(yawPitchRoll == yawPitchRollCopy);
      }
   }

   @Test
   public void testSetEuler()
   {
      Random random = new Random(65466L);
      RotationMatrix rotationMatrix, rotationMatrixCopy;
      RotationMatrix yawPitchRoll = new RotationMatrix();
      RotationMatrix expected = new RotationMatrix();
      Vector3D eulerAngles, eulerAnglesCopy;

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test setEuler(VectorBasics eulerAngles)
         rotationMatrix = rotationMatrixCopy = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         eulerAngles = eulerAnglesCopy = GeometryBasicsRandomTools.generateRandomVector3D(random);
         yawPitchRoll.setEuler(eulerAngles);
         RotationMatrixConversion.convertYawPitchRollToMatrix(eulerAngles.getZ(), eulerAngles.getY(), eulerAngles.getX(), expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertRotationVectorEquals(eulerAngles, eulerAnglesCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(yawPitchRoll, expected, EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test setEuler(double rotX, double rotY, double rotZ)
         rotationMatrix = rotationMatrixCopy = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         eulerAngles = eulerAnglesCopy = GeometryBasicsRandomTools.generateRandomVector3D(random);
         yawPitchRoll.setEuler(eulerAngles.getX(), eulerAngles.getY(), eulerAngles.getZ());
         RotationMatrixConversion.convertYawPitchRollToMatrix(eulerAngles.getZ(), eulerAngles.getY(), eulerAngles.getX(), expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertRotationVectorEquals(eulerAngles, eulerAnglesCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(yawPitchRoll, expected, EPS);
      }
   }

   @Test
   public void testGet()
   {
      Random random = new Random(6841L);
      RotationMatrix rotationMatrix, rotationMatrixCopy = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test get(VectorBasics rotationVectorToPack)
         rotationMatrix = rotationMatrixCopy = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);

         Vector3D vector = new Vector3D();
         Vector3D expectedVector = new Vector3D();

         rotationMatrix.get(vector);
         RotationVectorConversion.convertMatrixToRotationVector(rotationMatrix, expectedVector);

         GeometryBasicsTestTools.assertRotationVectorEquals(vector, expectedVector, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
      }
   }

   @Test
   public void testGetEuler()
   {
      Random random = new Random(65466L);
      RotationMatrix yawPitchRoll = new RotationMatrix(), expected;
      Vector3D eulerAngles = new Vector3D();
      Vector3D eulerAnglesCopy = new Vector3D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
         try
         {
            expected = yawPitchRoll = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
            yawPitchRoll.getEuler(eulerAngles);
            YawPitchRollConversion.convertMatrixToYawPitchRoll(expected, eulerAnglesCopy);

            GeometryBasicsTestTools.assertRotationVectorEquals(eulerAngles, eulerAnglesCopy, EPS);
            GeometryBasicsTestTools.assertMatrix3DEquals(yawPitchRoll, expected, EPS);
         }
         catch (AssertionError e)
         {
            double pitch = YawPitchRollConversion.computePitch(yawPitchRoll);
            if (!Double.isNaN(pitch))
               throw e;
         }
   }

   @Test
   public void testGetToYawPitchRollMatrix()
   {
      Random random = new Random(35454L);
      RotationMatrix rotationMatrix, expectedMatrix;

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         rotationMatrix = expectedMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);

         { // Test getToPitchMatrix()
            double pitch = rotationMatrix.getPitch();
            double expectedPitch = YawPitchRollConversion.computePitch(expectedMatrix);

            Assert.assertEquals(pitch, expectedPitch, EPS);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);
         }

         { // Test getToRollMatrix()

            double roll = rotationMatrix.getRoll();
            double expectedRoll = YawPitchRollConversion.computeRoll(expectedMatrix);

            Assert.assertEquals(roll, expectedRoll, EPS);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);
         }

         { // Test getToYawMatrix()
            double yaw = rotationMatrix.getYaw();
            double expectedYaw = YawPitchRollConversion.computeYaw(expectedMatrix);

            Assert.assertEquals(yaw, expectedYaw, EPS);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);

            GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);
         }
      }
   }

   @Test
   public void testGetYawPitchRoll()
   {
      Random random = new Random(35454L);
      RotationMatrix rotationMatrix = new RotationMatrix(), expectedMatrix = new RotationMatrix();
      double[] yawPitchRoll, yawPitchRollCopy = new double[3];

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expectedMatrix.set(rotationMatrix);
         yawPitchRoll = GeometryBasicsRandomTools.generateRandomYawPitchRoll(random);
         yawPitchRollCopy = yawPitchRoll;

         rotationMatrix.getYawPitchRoll(yawPitchRoll);
         RotationMatrixConversion.convertYawPitchRollToMatrix(yawPitchRoll, expectedMatrix);

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);
         Assert.assertTrue(yawPitchRoll == yawPitchRollCopy);
      }
   }

   @Test
   public void testApplyTransform()
   {
      Random random = new Random(23523L);

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RigidBodyTransform transform = GeometryBasicsRandomTools.generateRandomRigidBodyTransform(random);
         RotationMatrix original = createRandomMatrix(random);
         RotationMatrix expected = new RotationMatrix();
         RotationMatrix actual = new RotationMatrix();

         expected.set(original);
         expected.preMultiply(transform.getRotationMatrix());
         actual.set(original);
         actual.applyTransform(transform);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         QuaternionBasedTransform transform = GeometryBasicsRandomTools.generateRandomQuaternionBasedTransform(random);
         RotationMatrix original = createRandomMatrix(random);
         RotationMatrix expected = new RotationMatrix();
         RotationMatrix actual = new RotationMatrix();

         expected.set(original);
         expected.preMultiply(transform.getQuaternion());
         actual.set(original);
         actual.applyTransform(transform);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, SMALL_EPS);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         AffineTransform transform = GeometryBasicsRandomTools.generateRandomAffineTransform(random);
         RotationMatrix original = createRandomMatrix(random);
         RotationMatrix expected = new RotationMatrix();
         RotationMatrix actual = new RotationMatrix();

         expected.set(original);
         expected.preMultiply(transform.getRotationMatrix());
         actual.set(original);
         actual.applyTransform(transform);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, SMALL_EPS);
      }
   }

   @Test
   public void testInvert()
   {
      Random random = new Random(65474L);
      RotationMatrix rotationMatrix, expectedMatrix = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expectedMatrix.set(rotationMatrix);

         rotationMatrix.invert();
         expectedMatrix.transpose();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, expectedMatrix, EPS);
      }
   }

   @Test
   public void testMultiply()
   {
      Random random = new Random(645864L);
      RotationMatrix multiplied, expected = new RotationMatrix();
      RotationMatrix rotationMatrix, rotationMatrixCopy = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiply(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiply(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiply(expected, rotationMatrixCopy, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiply(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiply(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiply(expected, quaternion, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyTransposeThis(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyTransposeThis(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeLeft(expected, rotationMatrixCopy, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyTransposeThis(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyTransposeThis(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyTransposeMatrix(expected, quaternion, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyTransposeOther(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyTransposeOther(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeRight(expected, rotationMatrixCopy, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyConjugateQuaternion(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyConjugateQuaternion(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyConjugateQuaternion(expected, quaternion, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyTransposeBoth(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyTransposeBoth(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeBoth(expected, rotationMatrixCopy, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test multiplyTransposeMatrixConjugateQuaternion(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.multiplyTransposeThisConjugateQuaternion(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyTransposeMatrixConjugateQuaternion(expected, quaternion, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }
   }

   @Test
   public void testPreMultiply()
   {
      Random random = new Random(645864L);
      RotationMatrix multiplied, expected = new RotationMatrix();
      RotationMatrix rotationMatrix, rotationMatrixCopy = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiply(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiply(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiply(rotationMatrixCopy, expected, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiply(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiply(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiply(quaternion, expected, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyTransposeThis(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyTransposeThis(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeRight(rotationMatrixCopy, expected, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyTransposeThis(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyTransposeThis(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyTransposeMatrix(quaternion, expected, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyTransposeOther(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyTransposeOther(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeLeft(rotationMatrixCopy, expected, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyConjugateQuaternion(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyConjugateQuaternion(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyConjugateQuaternion(quaternion, expected, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyTransposeBoth(RotationMatrixReadOnly<?> other)
         rotationMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         rotationMatrixCopy.set(rotationMatrix);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyTransposeBoth(rotationMatrix);

         expected.checkIfRotationMatrix();
         RotationMatrixTools.multiplyTransposeBoth(rotationMatrixCopy, expected, expected);
         expected.normalize();

         GeometryBasicsTestTools.assertMatrix3DEquals(rotationMatrix, rotationMatrixCopy, EPS);
         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      { // Test preMultiplyTransposeThisConjugateQuaternion(QuaternionReadOnly<?> quaternion)
         Quaternion quaternion = GeometryBasicsRandomTools.generateRandomQuaternion(random);
         multiplied = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         expected.set(multiplied);

         multiplied.preMultiplyTransposeThisConjugateQuaternion(quaternion);

         expected.checkIfRotationMatrix();
         QuaternionTools.multiplyConjugateQuaternionTransposeMatrix(quaternion, expected, expected);

         GeometryBasicsTestTools.assertMatrix3DEquals(multiplied, expected, EPS);

         assertEquals(1.0, multiplied.determinant(), 1.0e-10);
      }
   }

   @Test
   public void testNormalize() throws Exception
   {
      Random random = new Random(39456L);
      RotationMatrix matrixExpected = new RotationMatrix();
      RotationMatrix matrixActual = new RotationMatrix();

      { // Check that identity does not get modified
         matrixActual.setIdentity();
         matrixExpected.setIdentity();

         matrixActual.normalize();
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
      }

      // Test that normalizing a proper rotation matrix does not change it.
      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         matrixExpected.set(GeometryBasicsRandomTools.generateRandomRotationMatrix(random));
         matrixActual.set(matrixExpected);

         matrixActual.normalize();
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
      }

      Vector3D vector1 = new Vector3D();
      Vector3D vector2 = new Vector3D();

      // Test that it actually makes a random matrix ortho-normal
      double corruptionFactor = 0.1;
      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix randomRotation = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         double m00 = randomRotation.getM00() + corruptionFactor * random.nextDouble();
         double m01 = randomRotation.getM01() + corruptionFactor * random.nextDouble();
         double m02 = randomRotation.getM02() + corruptionFactor * random.nextDouble();
         double m10 = randomRotation.getM10() + corruptionFactor * random.nextDouble();
         double m11 = randomRotation.getM11() + corruptionFactor * random.nextDouble();
         double m12 = randomRotation.getM12() + corruptionFactor * random.nextDouble();
         double m20 = randomRotation.getM20() + corruptionFactor * random.nextDouble();
         double m21 = randomRotation.getM21() + corruptionFactor * random.nextDouble();
         double m22 = randomRotation.getM22() + corruptionFactor * random.nextDouble();
         matrixActual.setUnsafe(m00, m01, m02, m10, m11, m12, m20, m21, m22);
         matrixActual.normalize();

         // Test that each row & column vectors are unit-length
         for (int j = 0; j < 3; j++)
         {
            matrixActual.getRow(j, vector1);
            assertEquals(1.0, vector1.length(), EPS);

            matrixActual.getColumn(j, vector1);
            assertEquals(1.0, vector1.length(), EPS);
         }

         // Test that each pair of rows and each pair of columns are orthogonal
         for (int j = 0; j < 3; j++)
         {
            matrixActual.getRow(j, vector1);
            matrixActual.getRow((j + 1) % 3, vector2);
            assertEquals(0.0, vector1.dot(vector2), EPS);

            matrixActual.getColumn(j, vector1);
            matrixActual.getColumn((j + 1) % 3, vector2);
            assertEquals(0.0, vector1.dot(vector2), EPS);
         }
      }
   }

   @Test
   public void testSetAndNormalize() throws Exception
   {
      Random random = new Random(39456L);
      RotationMatrix matrixExpected = new RotationMatrix();
      RotationMatrix matrixActual = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         matrixExpected = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         double m00 = matrixExpected.getM00();
         double m01 = matrixExpected.getM01();
         double m02 = matrixExpected.getM02();
         double m10 = matrixExpected.getM10();
         double m11 = matrixExpected.getM11();
         double m12 = matrixExpected.getM12();
         double m20 = matrixExpected.getM20();
         double m21 = matrixExpected.getM21();
         double m22 = matrixExpected.getM22();
         matrixActual.setAndNormalize(m00, m01, m02, m10, m11, m12, m20, m21, m22);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

         matrixActual.setToNaN();
         matrixActual.setAndNormalize(matrixExpected);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

         matrixActual.setToNaN();
         matrixActual.setAndNormalize((Matrix3DReadOnly<?>) matrixExpected);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
      }

      double corruptionFactor = 0.1;
      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix corrupted = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         double m00 = corrupted.getM00() + corruptionFactor * random.nextDouble();
         double m01 = corrupted.getM01() + corruptionFactor * random.nextDouble();
         double m02 = corrupted.getM02() + corruptionFactor * random.nextDouble();
         double m10 = corrupted.getM10() + corruptionFactor * random.nextDouble();
         double m11 = corrupted.getM11() + corruptionFactor * random.nextDouble();
         double m12 = corrupted.getM12() + corruptionFactor * random.nextDouble();
         double m20 = corrupted.getM20() + corruptionFactor * random.nextDouble();
         double m21 = corrupted.getM21() + corruptionFactor * random.nextDouble();
         double m22 = corrupted.getM22() + corruptionFactor * random.nextDouble();
         corrupted.setUnsafe(m00, m01, m02, m10, m11, m12, m20, m21, m22);

         matrixExpected.setUnsafe(m00, m01, m02, m10, m11, m12, m20, m21, m22);
         matrixExpected.normalize();
         matrixActual.setAndNormalize(m00, m01, m02, m10, m11, m12, m20, m21, m22);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

         matrixActual.setToNaN();
         matrixActual.setAndNormalize(corrupted);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

         matrixActual.setToNaN();
         matrixActual.setAndNormalize((Matrix3DReadOnly<?>) corrupted);
         GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
      }
   }

   @Test
   public void testSetAndInvert() throws Exception
   {
      Random random = new Random(545L);
      RotationMatrix matrixActual = new RotationMatrix();
      RotationMatrix matrixExpected = new RotationMatrix();

      RotationMatrix randomMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
      matrixExpected.set(randomMatrix);
      matrixExpected.invert();

      matrixActual.setAndInvert(randomMatrix);
      GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

      matrixActual.setToNaN();
      matrixActual.setAndInvert((Matrix3DReadOnly<?>) randomMatrix);
      GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
   }

   @Test
   public void testSetAndTranspose() throws Exception
   {
      Random random = new Random(545L);
      RotationMatrix matrixActual = new RotationMatrix();
      RotationMatrix matrixExpected = new RotationMatrix();

      RotationMatrix randomMatrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
      matrixExpected.set(randomMatrix);
      matrixExpected.transpose();

      matrixActual.setAndTranspose(randomMatrix);
      GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);

      matrixActual.setToNaN();
      matrixActual.setAndTranspose((Matrix3DReadOnly<?>) randomMatrix);
      GeometryBasicsTestTools.assertMatrix3DEquals(matrixExpected, matrixActual, EPS);
   }

   @Test
   public void testTransformTuple() throws Exception
   {
      Random random = new Random(435L);
      Vector3D actual = new Vector3D();
      Vector3D expected = new Vector3D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Vector3D original = GeometryBasicsRandomTools.generateRandomVector3D(random);

         Matrix3DTools.transform(matrix, original, expected);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertTuple3DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertTuple3DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testTransformTuple2D() throws Exception
   {
      Random random = new Random(435L);
      Vector2D actual = new Vector2D();
      Vector2D expected = new Vector2D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = new RotationMatrix();
         matrix.setToYawMatrix(2.0 * Math.PI * random.nextDouble());
         Vector2D original = GeometryBasicsRandomTools.generateRandomVector2D(random);

         Matrix3DTools.transform(matrix, original, expected, true);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);

         actual.setToNaN();
         Matrix3DTools.transform(matrix, original, expected, true);
         actual.set(original);
         matrix.transform(actual, true);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual, true);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testTransformMatrix() throws Exception
   {
      Random random = new Random(435L);
      Matrix3D actual = new Matrix3D();
      Matrix3D expected = new Matrix3D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Matrix3D original = GeometryBasicsRandomTools.generateRandomMatrix3D(random);

         Matrix3DTools.transform(matrix, original, expected);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testTransformQuaternion() throws Exception
   {
      Random random = new Random(435L);
      Quaternion actual = new Quaternion();
      Quaternion expected = new Quaternion();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Quaternion original = GeometryBasicsRandomTools.generateRandomQuaternion(random);

         matrix.transform(original, expected);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertQuaternionEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertQuaternionEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testTransformVector4D() throws Exception
   {
      Random random = new Random(435L);
      Vector4D actual = new Vector4D();
      Vector4D expected = new Vector4D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Vector4D original = GeometryBasicsRandomTools.generateRandomVector4D(random);

         matrix.transform(original, expected);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testTransformRotationMatrix() throws Exception
   {
      Random random = new Random(435L);
      RotationMatrix actual = new RotationMatrix();
      RotationMatrix expected = new RotationMatrix();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         RotationMatrix original = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);

         matrix.transform(original, expected);
         actual.set(original);
         matrix.transform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.transform(original, actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformTuple() throws Exception
   {
      Random random = new Random(435L);
      Vector3D actual = new Vector3D();
      Vector3D expected = new Vector3D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Vector3D original = GeometryBasicsRandomTools.generateRandomVector3D(random);

         Matrix3DTools.inverseTransform(matrix, original, expected);
         actual.set(original);
         matrix.inverseTransform(actual);
         GeometryBasicsTestTools.assertTuple3DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.inverseTransform(original, actual);
         GeometryBasicsTestTools.assertTuple3DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformTuple2D() throws Exception
   {
      Random random = new Random(435L);
      Vector2D actual = new Vector2D();
      Vector2D expected = new Vector2D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = new RotationMatrix();
         matrix.setToYawMatrix(2.0 * Math.PI * random.nextDouble());
         Vector2D original = GeometryBasicsRandomTools.generateRandomVector2D(random);

         Matrix3DTools.inverseTransform(matrix, original, expected, true);
         actual.set(original);
         matrix.inverseTransform(actual);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.inverseTransform(original, actual);
         GeometryBasicsTestTools.assertTuple2DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformVector4D() throws Exception
   {
      Random random = new Random(435L);
      Vector4D actual = new Vector4D();
      Vector4D expected = new Vector4D();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         RotationMatrix matrix = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
         Vector4D original = GeometryBasicsRandomTools.generateRandomVector4D(random);

         Matrix3DTools.inverseTransform(matrix, original, expected);
         actual.set(original);
         matrix.inverseTransform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);

         actual.setToNaN();
         matrix.inverseTransform(original, actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testEquals() throws Exception
   {
      Random random = new Random(2354L);
      RotationMatrix m1 = GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
      RotationMatrix m2 = new RotationMatrix();

      assertFalse(m1.equals(m2));
      assertFalse(m1.equals(null));
      assertFalse(m1.equals(new double[4]));
      m2.set(m1);
      assertTrue(m1.equals(m2));
      assertTrue(m1.equals((Object) m2));

      double smallestEpsilon = 1.0e-16;
      double[] coeffs = new double[9];

      for (int row = 0; row < 3; row++)
      {
         for (int column = 0; column < 3; column++)
         {
            m2.set(m1);
            assertTrue(m1.equals(m2));
            m1.get(coeffs);
            coeffs[3 * row + column] += smallestEpsilon;
            m2.set(coeffs);
            assertFalse(m1.equals(m2));

            m2.set(m1);
            assertTrue(m1.equals(m2));
            m1.get(coeffs);
            coeffs[3 * row + column] -= smallestEpsilon;
            m2.set(coeffs);
            assertFalse(m1.equals(m2));
         }
      }
   }

   @Test
   public void testHashCode() throws Exception
   {
      Random random = new Random(621541L);
      Matrix3D matrix = GeometryBasicsRandomTools.generateRandomMatrix3D(random);
      RotationMatrix rotationMatrix = new RotationMatrix();

      int newHashCode, previousHashCode;
      newHashCode = matrix.hashCode();
      assertEquals(newHashCode, matrix.hashCode());

      previousHashCode = matrix.hashCode();

      for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
      {
         int row = random.nextInt(3);
         int column = random.nextInt(3);
         matrix.setElement(row, column, random.nextDouble());

         double m00 = matrix.getM00();
         double m01 = matrix.getM01();
         double m02 = matrix.getM02();
         double m10 = matrix.getM10();
         double m11 = matrix.getM11();
         double m12 = matrix.getM12();
         double m20 = matrix.getM20();
         double m21 = matrix.getM21();
         double m22 = matrix.getM22();
         rotationMatrix.setUnsafe(m00, m01, m02, m10, m11, m12, m20, m21, m22);

         newHashCode = rotationMatrix.hashCode();
         assertNotEquals(newHashCode, previousHashCode);
         previousHashCode = newHashCode;
      }
   }

   @Override
   public RotationMatrix createEmptyMatrix()
   {
      return new RotationMatrix();
   }

   @Override
   public RotationMatrix createMatrix(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22)
   {
      RotationMatrix rotationMatrix = new RotationMatrix();
      rotationMatrix.setUnsafe(m00, m01, m02, m10, m11, m12, m20, m21, m22);
      return rotationMatrix;
   }

   @Override
   public RotationMatrix createRandomMatrix(Random random)
   {
      return GeometryBasicsRandomTools.generateRandomRotationMatrix(random);
   }
}
