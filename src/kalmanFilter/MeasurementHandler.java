// TODO - change package name
package kalmanFilter;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import orientationUtils.Orientation3D;
import orientationUtils.Utils;
import orientationUtils.preferences.AnglesUnit;

public class MeasurementHandler implements Function<RealVector, RealVector> {

	private Orientation3D initialState;

	private AnglesUnit anglesUnit;

	private RealVector biases;

	public MeasurementHandler(Orientation3D initialState, AnglesUnit anglesUnit, RealVector biases) {
		this.initialState = initialState;

		this.anglesUnit = anglesUnit;

		if (biases.getDimension() == 3)
			this.biases = biases;
		else
			throw new IllegalArgumentException("the biases' vector should have 3 elements");
	}

	public MeasurementHandler(Orientation3D initialState, AnglesUnit anglesUnit) {
		this(initialState, anglesUnit, new ArrayRealVector(new double[] { 0, 0, 0 }));
	}

	private RealVector toNavigationFrame(RealVector vector) {
		// calculating the current orientation angles
		double yaw = initialState.getYaw() + anglesUnit.getYaw();
		double pitch = initialState.getPitch() + anglesUnit.getPitch();
		double roll = initialState.getRoll() + anglesUnit.getRoll();

		RealMatrix transformation = Utils.getTransformationMatrix(yaw, roll, pitch);
		return transformation.preMultiply(vector);
	}

	@Override
	public RealVector apply(RealVector measurement) {
		return toNavigationFrame(measurement).subtract(biases);
	}
}
