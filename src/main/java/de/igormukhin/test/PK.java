package de.igormukhin.test;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String field1;
	private String field2;

	public PK() {
	}

	public PK(String field1, String field2) {
		this.field1 = field1;
		this.field2 = field2;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
		result = prime * result + ((field2 == null) ? 0 : field2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PK other = (PK) obj;
		if (field1 == null) {
			if (other.field1 != null)
				return false;
		} else if (!field1.equals(other.field1))
			return false;
		if (field2 == null) {
			if (other.field2 != null)
				return false;
		} else if (!field2.equals(other.field2))
			return false;
		return true;
	}

}
