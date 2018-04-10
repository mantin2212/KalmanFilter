package utils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Utils {

	public static RealMatrix getTransformationMatrix(double yaw, double roll, double pitch) {
		RealMatrix result = new Array2DRowRealMatrix(3, 3);

		// defining variables for the sine and cosine results of the angles
		double cosRoll = Math.cos(roll);
		double cosYaw = Math.cos(yaw);
		double cosPitch = Math.cos(pitch);

		double sinRoll = Math.sin(roll);
		double sinYaw = Math.sin(yaw);
		double sinPitch = Math.sin(pitch);

		// calculating each value of the matrix
		result.setEntry(0, 0, cosYaw * cosPitch);
		result.setEntry(0, 1, cosYaw * sinPitch * sinRoll - cosYaw * sinRoll);
		result.setEntry(0, 2, cosYaw * sinPitch * cosRoll + sinYaw * sinRoll);

		result.setEntry(1, 0, sinYaw * cosPitch);
		result.setEntry(1, 1, sinYaw * sinPitch * sinRoll + cosYaw * cosRoll);
		result.setEntry(1, 2, sinYaw * sinPitch * cosRoll - cosYaw * sinRoll);

		result.setEntry(2, 0, -sinPitch);
		result.setEntry(2, 1, cosPitch * sinRoll);
		result.setEntry(2, 2, cosPitch * cosRoll);

		return result;
	}

	public static RealMatrix getUnitMatrix(int size) {
		RealMatrix result = new Array2DRowRealMatrix(size, size);

		for (int i = 0; i < result.getRowDimension(); i++)
			result.setEntry(i, i, 1);

		return result;
	}
}