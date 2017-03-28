package vn.gmobile.einvoice.model;

public class InvoiceDetailUsageRpPOJO {
	
	private int str1, str2, str3;
	private int sl1, sl2, sl3;

	
	public InvoiceDetailUsageRpPOJO() {
	}
	
	public InvoiceDetailUsageRpPOJO(int sl1, int str1, int sl2, int str2, int sl3, int str3){
		super();
		this.sl1 = sl1;
		this.sl2 = sl2;
		this.sl3 = sl3;
		
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}
	
	public InvoiceDetailUsageRpPOJO(Object sl1, Object str1, Object sl2, Object str2, Object sl3, Object str3){
		super();
		if(sl1 != null) this.sl1 = (Integer) sl1;
		if(sl2 != null) this.sl2 = (Integer) sl2;
		if(sl3 != null) this.sl3 = (Integer) sl3;
		
		if(str1 != null) this.str1 = (Integer) str1;
		if(str2 != null) this.str2 = (Integer) str2;
		if(str3 != null) this.str3 = (Integer) str3;
	}
	
	public int getStr1() {
		return str1;
	}

	public void setStr1(int str1) {
		this.str1 = str1;
	}

	public int getStr2() {
		return str2;
	}

	public void setStr2(int str2) {
		this.str2 = str2;
	}

	public int getStr3() {
		return str3;
	}

	public void setStr3(int str3) {
		this.str3 = str3;
	}

	public int getSl1() {
		return sl1;
	}

	public void setSl1(int sl1) {
		this.sl1 = sl1;
	}

	public int getSl2() {
		return sl2;
	}

	public void setSl2(int sl2) {
		this.sl2 = sl2;
	}

	public int getSl3() {
		return sl3;
	}

	public void setSl3(int sl3) {
		this.sl3 = sl3;
	}
}