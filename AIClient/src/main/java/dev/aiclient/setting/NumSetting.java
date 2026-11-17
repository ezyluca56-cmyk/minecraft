package dev.aiclient.setting;

public class NumSetting extends Setting {
	public double value;
	public final double min;
	public final double max;
	public final double step;

	public NumSetting(String name, double value, double min, double max, double step) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.step = step;
	}

	/** dir = +1 or -1 */
	public void step(int dir) {
		double v = value + dir * step;
		value = Math.round(Math.max(min, Math.min(max, v)) * 1000.0) / 1000.0;
	}

	public float f() {
		return (float) value;
	}

	public int i() {
		return (int) Math.round(value);
	}
}
