package vn.gmobile.einvoice.model;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.util.DateUtils;

public class DataLoadPOJO {
	private String symbol_of_invoice;
	private String serial;
	private String invoice_no;
	private String create_date;
	private String customer_name;
	private String company_name;
	private String tax_code;
	private String msisdn;
	private String kind_of_service;
	private Integer amount;
	private Integer vat_rate; 
	private Integer vat_amount;
	private String erp_customer_code;
	
	public String getSymbol_of_invoice() {
		return symbol_of_invoice;
	}
	public void setSymbol_of_invoice(String symbol_of_invoice) {
		this.symbol_of_invoice = symbol_of_invoice;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getTax_code() {
		return tax_code;
	}
	public void setTax_code(String tax_code) {
		this.tax_code = tax_code;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getKind_of_service() {
		return kind_of_service;
	}
	public void setKind_of_service(String kind_of_service) {
		this.kind_of_service = kind_of_service;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getVat_rate() {
		return vat_rate;
	}
	public void setVat_rate(Integer vat_rate) {
		this.vat_rate = vat_rate;
	}
	public Integer getVat_amount() {
		return vat_amount;
	}
	public void setVat_amount(Integer vat_amount) {
		this.vat_amount = vat_amount;
	}
	public String getErp_customer_code() {
		return erp_customer_code;
	}
	public void setErp_customer_code(String erp_customer_code) {
		this.erp_customer_code = erp_customer_code;
	}
	
	public static DataLoadPOJO fromBean(DataLoad dl) throws Exception{
		DataLoadPOJO dlPOJO = new DataLoadPOJO();
		dlPOJO.setSymbol_of_invoice(dl.get(DataLoad.symbol_of_invoice));
		dlPOJO.setSerial(dl.get(DataLoad.serial));
		dlPOJO.setInvoice_no(dl.get(DataLoad.invoice_no));
		dlPOJO.setCreate_date(DateUtils.getShortDate(dl.get(DataLoad.create_date)));
		dlPOJO.setCustomer_name(dl.get(DataLoad.customer_name));
		dlPOJO.setCompany_name(dl.get(DataLoad.company_name));
		dlPOJO.setTax_code(dl.get(DataLoad.tax_code));
		dlPOJO.setMsisdn(dl.get(DataLoad.msisdn));
		dlPOJO.setKind_of_service(dl.get(DataLoad.kind_of_service));
		dlPOJO.setAmount(dl.get(DataLoad.amount) * (dl.get(DataLoad.parent_invoice_no).equals(Consts.DATA_LOAD.PARENT_ID_BLANK) ? 1 : dl.get(DataLoad.adjust_type)));
		dlPOJO.setVat_rate(dl.get(DataLoad.vat_rate));
		dlPOJO.setVat_amount(dl.get(DataLoad.vat_amount) * (dl.get(DataLoad.parent_invoice_no).equals(Consts.DATA_LOAD.PARENT_ID_BLANK) ? 1 : dl.get(DataLoad.adjust_type)));
		dlPOJO.setErp_customer_code(dl.get(DataLoad.erp_customer_code));
		return dlPOJO;
	}
}
