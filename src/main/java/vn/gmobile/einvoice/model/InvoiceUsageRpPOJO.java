package vn.gmobile.einvoice.model;

import java.util.ArrayList;
import java.util.List;

public class InvoiceUsageRpPOJO {
	// No Repeat Data
	private String time;
	
	// Repeat Data
	private Integer no;
	private String name;
    private String symbol_of_invoice;
    private String serial;
    
    private Integer opening_stock_number_from;
    private Integer opening_stock_number_to;
    private Integer receiving_stock_number_from;
    private Integer receiving_stock_number_to;
    private Integer invoice_in_stock;
    
    private Integer total_invoice_from;
    private Integer total_invoice_to;
    private Integer total_invoice_subtract;
    
    private Integer remain_invoice_from;
    private Integer remain_invoice_to;
    
    private Integer invoice_used;
    private List invDetails = new ArrayList();

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public InvoiceUsageRpPOJO(String name) {
        this.name = name;
    }
    
    public InvoiceUsageRpPOJO(String name, String symbol_of_invoice, String serial) {
        this.name = name;
        this.symbol_of_invoice = symbol_of_invoice;
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

	public int getOpening_stock_number_from() {
		return opening_stock_number_from;
	}

	public void setOpening_stock_number_from(Integer opening_stock_number_from) {
		this.opening_stock_number_from = opening_stock_number_from;
	}

	public Integer getOpening_stock_number_to() {
		return opening_stock_number_to;
	}

	public void setOpening_stock_number_to(Integer opening_stock_number_to) {
		this.opening_stock_number_to = opening_stock_number_to;
	}

	public Integer getReceiving_stock_number_from() {
		return receiving_stock_number_from;
	}

	public void setReceiving_stock_number_from(Integer receiving_stock_number_from) {
		this.receiving_stock_number_from = receiving_stock_number_from;
	}

	public Integer getReceiving_stock_number_to() {
		return receiving_stock_number_to;
	}

	public void setReceiving_stock_number_to(Integer receiving_stock_number_to) {
		this.receiving_stock_number_to = receiving_stock_number_to;
	}

	public Integer getInvoice_in_stock() {
		return invoice_in_stock;
	}

	public void setInvoice_in_stock(Integer invoice_in_stock) {
		this.invoice_in_stock = invoice_in_stock;
	}

	public Integer getTotal_invoice_from() {
		return total_invoice_from;
	}

	public void setTotal_invoice_from(Integer total_invoice_from) {
		this.total_invoice_from = total_invoice_from;
	}

	public Integer getTotal_invoice_to() {
		return total_invoice_to;
	}

	public void setTotal_invoice_to(Integer total_invoice_to) {
		this.total_invoice_to = total_invoice_to;
	}

	public Integer getTotal_invoice_subtract() {
		return total_invoice_subtract;
	}

	public void setTotal_invoice_subtract(Integer total_invoice_subtract) {
		this.total_invoice_subtract = total_invoice_subtract;
	}

	public List getInvDetails() {
		return invDetails;
	}

	public void setInvDetails(List invDetails) {
		this.invDetails = invDetails;
	}
	
	public void addInvDetail(InvoiceDetailUsageRpPOJO invDetail) {
		this.invDetails.add(invDetail);
	}
	
	public Integer getInvoice_used() {
		return invoice_used;
	}

	public void setInvoice_used(Integer invoice_used) {
		this.invoice_used = invoice_used;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getRemain_invoice_from() {
		return remain_invoice_from;
	}

	public void setRemain_invoice_from(Integer remain_invoice_from) {
		this.remain_invoice_from = remain_invoice_from;
	}

	public Integer getRemain_invoice_to() {
		return remain_invoice_to;
	}

	public void setRemain_invoice_to(Integer remain_invoice_to) {
		this.remain_invoice_to = remain_invoice_to;
	}
}
   