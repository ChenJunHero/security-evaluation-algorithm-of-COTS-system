package ÆÀ¹À³ÌÐò;

public class DriverInfo {
	private String driver;
	private String risk;
	private int riskIndex;
	private float cosValue;
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public DriverInfo(String driver, String risk, int riskIndex, float cosValue) {
		super();
		this.driver = driver;
		this.risk = risk;
		this.riskIndex = riskIndex;
		this.cosValue = cosValue;
	}

	public int getRiskIndex() {
		return riskIndex;
	}

	public void setRiskIndex(int riskIndex) {
		this.riskIndex = riskIndex;
	}

	public float getCosValue() {
		return cosValue;
	}

	public void setCosValue(float cosValue) {
		this.cosValue = cosValue;
	}

	public DriverInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
