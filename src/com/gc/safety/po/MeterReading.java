package com.gc.safety.po;

public class MeterReading {
	private MeterReadingPK id;
	
	private Double reading;
	
	private Double oilrate;
	
	private String comment;

	public MeterReadingPK getId() {
		return MeterReadingPK.getSafeObject(id);
	}

	public void setId(MeterReadingPK id) {
		this.id = id;
	}

	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	public Double getOilrate() {
		return oilrate;
	}

	public void setOilrate(Double oilrate) {
		this.oilrate = oilrate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
