package vn.gmobile.einvoice.model;

public class InvoiceAgencyPOJO {
	private Integer invoice_agency_id;
	private String seller;
	private String tin;
	private String address;
	private String tel;
	private String fax;
	
	public Integer getInvoice_agency_id() {
		return invoice_agency_id;
	}
	public void setInvoice_agency_id(Integer invoice_agency_id) {
		this.invoice_agency_id = invoice_agency_id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public static InvoiceAgencyPOJO fromBean(InvoiceAgency ia){
		InvoiceAgencyPOJO iaPOJO = new InvoiceAgencyPOJO();
		iaPOJO.setSeller(ia.get(InvoiceAgency.seller));
		iaPOJO.setTin(ia.get(InvoiceAgency.tin));
		return iaPOJO;
	}
}