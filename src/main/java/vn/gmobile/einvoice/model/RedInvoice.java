package vn.gmobile.einvoice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"ArisingDate",
	"InvoiceName",
	"InvoicePattern",
	"SerialNo",
	"InvoiceNo",
	"Kind_of_Payment",
	"ComName",
	"ComTaxCode",
	"ComAddress",
	"ComPhone",
	"ComBankNo",
	"ComBankName",
	"ComFax",
	"FromDate",
	"ToDate",
	"CusCode",
	"CusERPCode",
	"CusName",
	"CusCompany",
	"CusTaxCode",
	"CusPhone",
	"CusAddress",
	"CusBankName",
	"CusBankNo",
	"Total",
	"VAT_Rate",
	"VAT_Amount",
	"Amount",
	"GrandTotal",
	"Sum_in_words",
	"KindOfService",
	"Discount_Rate",
	"Discount_Amount",
})
@XmlRootElement(name = "RedInvoice")
public class RedInvoice {
	@XmlElement
	private String ArisingDate;
	@XmlElement
	private String InvoiceName;
	@XmlElement
	private String InvoicePattern;
	@XmlElement
	private String SerialNo;
	@XmlElement
	private String InvoiceNo;
	@XmlElement
	private String Kind_of_Payment;
	@XmlElement
	private String ComName;
	@XmlElement
	private String ComTaxCode;
	@XmlElement
	private String ComAddress;
	@XmlElement
	private String ComPhone;
	@XmlElement
	private String ComBankNo;
	@XmlElement
	private String ComBankName;
	@XmlElement
	private String ComFax;
	@XmlElement
	private String CusCode;
	@XmlElement
	private String CusERPCode;
	@XmlElement
	private String CusName;
	@XmlElement
	private String CusCompany;
	@XmlElement
	private String CusTaxCode;
	@XmlElement
	private String CusPhone;
	@XmlElement
	private String CusAddress;
	@XmlElement
	private String CusBankName;
	@XmlElement
	private String CusBankNo;
	@XmlElement
	private Integer Total;
	@XmlElement
	private Integer VAT_Amount;
	@XmlElement
	private Integer Amount;
	@XmlElement
	private Integer GrandTotal;
	@XmlElement
	private String Sum_in_words;
	@XmlElement
	private String KindOfService;
	@XmlElement
	private Integer VAT_Rate;
	@XmlElement
	private String Discount_Rate;
	@XmlElement
	private String Discount_Amount;
	@XmlElement
	private String FromDate;
	@XmlElement
	private String ToDate;

	public String getArisingDate() {
		return ArisingDate;
	}
	public void setArisingDate(String arisingDate) {
		ArisingDate = arisingDate;
	}
	public String getInvoiceName() {
		return InvoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		InvoiceName = invoiceName;
	}
	public String getInvoicePattern() {
		return InvoicePattern;
	}
	public void setInvoicePattern(String invoicePattern) {
		InvoicePattern = invoicePattern;
	}
	public String getSerialNo() {
		return SerialNo;
	}
	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}
	public String getInvoiceNo() {
		return InvoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}
	public String getKind_of_Payment() {
		return Kind_of_Payment;
	}
	public void setKind_of_Payment(String kind_of_Payment) {
		Kind_of_Payment = kind_of_Payment;
	}
	public String getComName() {
		return ComName;
	}
	public void setComName(String comName) {
		ComName = comName;
	}
	public String getComTaxCode() {
		return ComTaxCode;
	}
	public void setComTaxCode(String comTaxCode) {
		ComTaxCode = comTaxCode;
	}
	public String getComAddress() {
		return ComAddress;
	}
	public void setComAddress(String comAddress) {
		ComAddress = comAddress;
	}
	public String getComPhone() {
		return ComPhone;
	}
	public void setComPhone(String comPhone) {
		ComPhone = comPhone;
	}
	public String getComBankNo() {
		return ComBankNo;
	}
	public void setComBankNo(String comBankNo) {
		ComBankNo = comBankNo;
	}
	public String getComBankName() {
		return ComBankName;
	}
	public void setComBankName(String comBankName) {
		ComBankName = comBankName;
	}
	public String getComFax() {
		return ComFax;
	}
	public void setComFax(String comFax) {
		ComFax = comFax;
	}
	public String getCusCode() {
		return CusCode;
	}
	public void setCusCode(String cusCode) {
		CusCode = cusCode;
	}
	public String getCusERPCode() {
		return CusERPCode;
	}
	public void setCusERPCode(String cusERPCode) {
		CusERPCode = cusERPCode;
	}
	public String getCusName() {
		return CusName;
	}
	public void setCusName(String cusName) {
		CusName = cusName;
	}
	public String getCusCompany() {
		return CusCompany;
	}
	public void setCusCompany(String cusCompany) {
		CusCompany = cusCompany;
	}
	public String getCusTaxCode() {
		return CusTaxCode;
	}
	public void setCusTaxCode(String cusTaxCode) {
		CusTaxCode = cusTaxCode;
	}
	public String getCusPhone() {
		return CusPhone;
	}
	public void setCusPhone(String cusPhone) {
		CusPhone = cusPhone;
	}
	public String getCusAddress() {
		return CusAddress;
	}
	public void setCusAddress(String cusAddress) {
		CusAddress = cusAddress;
	}
	public String getCusBankName() {
		return CusBankName;
	}
	public void setCusBankName(String cusBankName) {
		CusBankName = cusBankName;
	}
	public String getCusBankNo() {
		return CusBankNo;
	}
	public void setCusBankNo(String cusBankNo) {
		CusBankNo = cusBankNo;
	}
	public Integer getTotal() {
		return Total;
	}
	public void setTotal(Integer total) {
		Total = total;
	}
	public Integer getVAT_Amount() {
		return VAT_Amount;
	}
	public void setVAT_Amount(Integer vAT_Amount) {
		VAT_Amount = vAT_Amount;
	}
	public Integer getAmount() {
		return Amount;
	}
	public void setAmount(Integer amount) {
		Amount = amount;
	}
	public Integer getGrandTotal() {
		return GrandTotal;
	}
	public void setGrandTotal(Integer grandTotal) {
		GrandTotal = grandTotal;
	}
	public String getSum_in_words() {
		return Sum_in_words;
	}
	public void setSum_in_words(String sum_in_words) {
		Sum_in_words = sum_in_words;
	}
	public String getKindOfService() {
		return KindOfService;
	}
	public void setKindOfService(String kindOfService) {
		KindOfService = kindOfService;
	}
	public Integer getVAT_Rate() {
		return VAT_Rate;
	}
	public void setVAT_Rate(Integer vAT_Rate) {
		VAT_Rate = vAT_Rate;
	}
	public String getDiscount_Rate() {
		return Discount_Rate;
	}
	public void setDiscount_Rate(String discount_Rate) {
		Discount_Rate = discount_Rate;
	}
	public String getDiscount_Amount() {
		return Discount_Amount;
	}
	public void setDiscount_Amount(String discount_Amount) {
		Discount_Amount = discount_Amount;
	}
	public String getFromDate() {
		return FromDate;
	}
	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
}
