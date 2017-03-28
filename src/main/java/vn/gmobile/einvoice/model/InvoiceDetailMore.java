package vn.gmobile.einvoice.model;

import com.bean.annot.*;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_detail")
public class InvoiceDetailMore extends Bean {
	@Attribute(name = "invoice_detail_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_detail_id = BeanProperty.integerType();
	@Attribute(name = "invoice_no")
	public static final BeanProperty<Integer> invoice_no = BeanProperty.integerType();
	@Attribute(name = "status")
	public static final BeanProperty<Integer> status = BeanProperty.integerType();
	@Attribute(name = "invoice_batch_id")
	public static final BeanProperty<Integer> invoice_batch_id = BeanProperty.integerType();
	@Attribute(name = "data_load_id")
	public static final BeanProperty<Integer> data_load_id = BeanProperty.integerType();
	
	/*ADD SOME INFO FOR INVOICE DETAIL*/
	@Attribute(name = "symbol_of_invoice")
	public static final BeanProperty<String> symbol_of_invoice = BeanProperty.stringType();
	@Attribute(name = "serial")
	public static final BeanProperty<String> serial = BeanProperty.stringType();
}