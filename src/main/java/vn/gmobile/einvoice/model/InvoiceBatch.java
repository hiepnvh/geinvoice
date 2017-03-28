package vn.gmobile.einvoice.model;

import java.util.Date;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.annot.Final;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_batch")
public class InvoiceBatch extends Bean {
	@Attribute(name = "invoice_batch_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_batch_id = BeanProperty.integerType();
	@Attribute(name = "create_date")
	@Final
	public static final BeanProperty<Date> create_date = BeanProperty.dateType();
	@Attribute(name = "invoice_agency_id")
	public static final BeanProperty<Integer> invoice_agency_id = BeanProperty.integerType();
	@Attribute(name = "symbol_of_invoice")
	public static final BeanProperty<String> symbol_of_invoice = BeanProperty.stringType();
	@Attribute(name = "serial")
	public static final BeanProperty<String> serial = BeanProperty.stringType();
	@Attribute(name = "start_no")
	public static final BeanProperty<Integer> start_no = BeanProperty.integerType();
	@Attribute(name = "quantity")
	public static final BeanProperty<Integer> quantity = BeanProperty.integerType();
	@Attribute(name = "invoice_created_date")
	public static final BeanProperty<Date> invoice_created_date = BeanProperty.dateType();
}