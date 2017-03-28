package vn.gmobile.einvoice.model;

import java.util.Date;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_usage_report")
public class InvoiceUsageReport extends Bean {
	@Attribute(name = "invoice_name")
	public static final BeanProperty<String> invoice_name = BeanProperty.stringType();
	@Attribute(name = "symbol_of_invoice")
	public static final BeanProperty<String> symbol_of_invoice = BeanProperty.stringType();
	@Attribute(name = "serial")
	@ExternalKey
	public static final BeanProperty<String> serial = BeanProperty.stringType();
	@Attribute(name = "from_date")
	@ExternalKey
	public static final BeanProperty<Date> from_date = BeanProperty.dateType();
	@Attribute(name = "to_date")
	@ExternalKey
	public static final BeanProperty<Date> to_date = BeanProperty.dateType();
	@Attribute(name = "opening_stock_number_from")
	public static final BeanProperty<Integer> opening_stock_number_from = BeanProperty.integerType();
	@Attribute(name = "opening_stock_number_to")
	public static final BeanProperty<Integer> opening_stock_number_to = BeanProperty.integerType();
	@Attribute(name = "receiving_stock_number_from")
	public static final BeanProperty<Integer> receiving_stock_number_from = BeanProperty.integerType();
	@Attribute(name = "receiving_stock_number_to")
	public static final BeanProperty<Integer> receiving_stock_number_to = BeanProperty.integerType();
	@Attribute(name = "total_invoice_from")
	public static final BeanProperty<Integer> total_invoice_from = BeanProperty.integerType();
	@Attribute(name = "total_invoice_to")
	public static final BeanProperty<Integer> total_invoice_to = BeanProperty.integerType();
	@Attribute(name = "invoice_used")
	public static final BeanProperty<Integer> invoice_used = BeanProperty.integerType();
	@Attribute(name = "remain_invoice_from")
	public static final BeanProperty<Integer> remain_invoice_from = BeanProperty.integerType();
	@Attribute(name = "remain_invoice_to")
	public static final BeanProperty<Integer> remain_invoice_to = BeanProperty.integerType();
	@Attribute(name = "fixed")
	public static final BeanProperty<Boolean> fixed = BeanProperty.boolType();
}