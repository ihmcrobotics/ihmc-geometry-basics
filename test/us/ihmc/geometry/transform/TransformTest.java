package us.ihmc.geometry.transform;

import java.util.Random;

import org.junit.Test;

import us.ihmc.geometry.matrix.Matrix3D;
import us.ihmc.geometry.matrix.RotationMatrix;
import us.ihmc.geometry.testingTools.EuclidCoreRandomTools;
import us.ihmc.geometry.testingTools.GeometryBasicsTestTools;
import us.ihmc.geometry.transform.interfaces.Transform;
import us.ihmc.geometry.tuple2D.Point2D;
import us.ihmc.geometry.tuple2D.Vector2D;
import us.ihmc.geometry.tuple3D.Point3D;
import us.ihmc.geometry.tuple3D.Vector3D;
import us.ihmc.geometry.tuple4D.Quaternion;
import us.ihmc.geometry.tuple4D.Vector4D;

public abstract class TransformTest<T extends Transform>
{
   private static final double EPS = 1.0e-10;

   public abstract T createRandomTransform(Random random);

   public abstract T createRandomTransform2D(Random random);

   @Test
   public void testInverseTransformWithTuple() throws Exception
   {
      Random random = new Random(3454L);
      T transform = createRandomTransform(random);

      { // Test inverseTransform(PointBasics pointToTransform)
         Point3D pointExpected = EuclidCoreRandomTools.generateRandomPoint3D(random);
         Point3D pointActual = new Point3D();
         pointActual.set(pointExpected);
         transform.transform(pointActual);
         transform.inverseTransform(pointActual);
         GeometryBasicsTestTools.assertTuple3DEquals(pointExpected, pointActual, EPS);
      }

      { // Test inverseTransform(PointReadOnly pointOriginal, PointBasics pointTransformed)
         Point3D pointExpected = EuclidCoreRandomTools.generateRandomPoint3D(random);
         Point3D pointActual = new Point3D();

         transform.inverseTransform(pointExpected, pointActual);
         transform.transform(pointActual);
         GeometryBasicsTestTools.assertTuple3DEquals(pointExpected, pointActual, EPS);
      }

      { // Test inverseTransform(VectorBasics vectorToTransform)
         Vector3D vectorExpected = EuclidCoreRandomTools.generateRandomVector3D(random);
         Vector3D vectorActual = new Vector3D();
         vectorActual.set(vectorExpected);
         transform.transform(vectorActual);
         transform.inverseTransform(vectorActual);
         GeometryBasicsTestTools.assertTuple3DEquals(vectorExpected, vectorActual, EPS);
      }

      { // Test inverseTransform(VectorReadOnly vectorOriginal, VectorBasics vectorTransformed)
         Vector3D vectorExpected = EuclidCoreRandomTools.generateRandomVector3D(random);
         Vector3D vectorActual = new Vector3D();

         transform.inverseTransform(vectorExpected, vectorActual);
         transform.transform(vectorActual);
         GeometryBasicsTestTools.assertTuple3DEquals(vectorExpected, vectorActual, EPS);
      }
   }

   @Test
   public void testInverseTransformWithTuple2D() throws Exception
   {
      Random random = new Random(3454L);
      T transfom2D = createRandomTransform2D(random);

      { // Test inverseTransform(Point2DBasics pointToTransform)
         Point2D pointExpected = EuclidCoreRandomTools.generateRandomPoint2D(random);
         Point2D pointActual = new Point2D();
         pointActual.set(pointExpected);
         transfom2D.transform(pointActual);
         transfom2D.inverseTransform(pointActual);
         GeometryBasicsTestTools.assertTuple2DEquals(pointExpected, pointActual, EPS);
      }

      { // Test inverseTransform(Point2DReadOnly pointOriginal, Point2DBasics pointTransformed)
         Point2D pointExpected = EuclidCoreRandomTools.generateRandomPoint2D(random);
         Point2D pointActual = new Point2D();

         transfom2D.inverseTransform(pointExpected, pointActual);
         transfom2D.transform(pointActual);
         GeometryBasicsTestTools.assertTuple2DEquals(pointExpected, pointActual, EPS);
      }

      { // Test inverseTransform(VectorBasics vectorToTransform)
         Vector2D vectorExpected = EuclidCoreRandomTools.generateRandomVector2D(random);
         Vector2D vectorActual = new Vector2D();
         vectorActual.set(vectorExpected);
         transfom2D.transform(vectorActual);
         transfom2D.inverseTransform(vectorActual);
         GeometryBasicsTestTools.assertTuple2DEquals(vectorExpected, vectorActual, EPS);
      }

      { // Test inverseTransform(VectorReadOnly vectorOriginal, VectorBasics vectorTransformed)
         Vector2D vectorExpected = EuclidCoreRandomTools.generateRandomVector2D(random);
         Vector2D vectorActual = new Vector2D();

         transfom2D.inverseTransform(vectorExpected, vectorActual);
         transfom2D.transform(vectorActual);
         GeometryBasicsTestTools.assertTuple2DEquals(vectorExpected, vectorActual, EPS);
      }
   }

   @Test
   public void testInverseTransformWithQuaternion() throws Exception
   {
      Random random = new Random(3454L);
      T transform = createRandomTransform(random);

      { // Test inverseTransform(QuaternionBasics quaternionToTransform)
         Quaternion expected = EuclidCoreRandomTools.generateRandomQuaternion(random);
         Quaternion actual = new Quaternion();
         actual.set(expected);
         transform.transform(actual);
         transform.inverseTransform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }

      { // Test inverseTransform(QuaternionReadOnly quaternionOriginal, QuaternionBasics quaternionTransformed)
         Quaternion expected = EuclidCoreRandomTools.generateRandomQuaternion(random);
         Quaternion actual = new Quaternion();
         transform.inverseTransform(expected, actual);
         transform.transform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformWithVector4D() throws Exception
   {
      Random random = new Random(3454L);
      T transform = createRandomTransform(random);

      { // Test inverseTransform(Vector4DBasics vectorToTransform)
         Vector4D expected = EuclidCoreRandomTools.generateRandomVector4D(random);
         Vector4D actual = new Vector4D();
         actual.set(expected);
         transform.transform(actual);
         transform.inverseTransform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }

      { // Test inverseTransform(Vector4DReadOnly vectorOriginal, Vector4DBasics vectorTransformed)
         Vector4D expected = EuclidCoreRandomTools.generateRandomVector4D(random);
         Vector4D actual = new Vector4D();
         transform.inverseTransform(expected, actual);
         transform.transform(actual);
         GeometryBasicsTestTools.assertTuple4DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformWithRotationMatrix() throws Exception
   {
      Random random = new Random(3454L);
      T transform = createRandomTransform(random);

      { // Test inverseTransform(RotationMatrix matrixToTransform)
         RotationMatrix expected = EuclidCoreRandomTools.generateRandomRotationMatrix(random);
         RotationMatrix actual = new RotationMatrix();
         actual.set(expected);
         transform.transform(actual);
         transform.inverseTransform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }

      { // Test inverseTransform(RotationMatrixReadOnly matrixOriginal, RotationMatrix matrixTransformed)
         RotationMatrix expected = EuclidCoreRandomTools.generateRandomRotationMatrix(random);
         RotationMatrix actual = new RotationMatrix();
         transform.inverseTransform(expected, actual);
         transform.transform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }
   }

   @Test
   public void testInverseTransformWithMatrix3D() throws Exception
   {
      Random random = new Random(3454L);
      T transform = createRandomTransform(random);

      { // Test inverseTransform(RotationMatrix matrixToTransform)
         Matrix3D expected = EuclidCoreRandomTools.generateRandomMatrix3D(random);
         Matrix3D actual = new Matrix3D();
         actual.set(expected);
         transform.transform(actual);
         transform.inverseTransform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }

      { // Test inverseTransform(RotationMatrixReadOnly matrixOriginal, RotationMatrix matrixTransformed)
         Matrix3D expected = EuclidCoreRandomTools.generateRandomMatrix3D(random);
         Matrix3D actual = new Matrix3D();
         transform.inverseTransform(expected, actual);
         transform.transform(actual);
         GeometryBasicsTestTools.assertMatrix3DEquals(expected, actual, EPS);
      }
   }

}