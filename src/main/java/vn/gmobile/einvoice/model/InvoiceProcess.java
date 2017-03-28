package vn.gmobile.einvoice.model;

import java.util.Date;

import com.bean.annot.*;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_process")
public class InvoiceProcess extends Bean {
	@Attribute(name = "invoice_process_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_process_id = BeanProperty.integerType();
	@Attribute(name = "invoice_process_type")
	public static final BeanProperty<Integer> invoice_process_type = BeanProperty.integerType();
	@Attribute(name = "start_time")
	public static final BeanProperty<Date> start_time = BeanProperty.dateType();
	@Attribute(name = "finish_time")
	public static final BeanProperty<Date> finish_time = BeanProperty.dateType();
	@Attribute(name = "quantity")
	public static final BeanProperty<Integer> quantity = BeanProperty.integerType();
	@Attribute(name = "serial")
	public static final BeanProperty<String> serial = BeanProperty.stringType();
	@Attribute(name = "status")
	public static final BeanProperty<Integer> status = BeanProperty.integerType();
	@Attribute(name = "username")
	public static final BeanProperty<String> username = BeanProperty.stringType();
	@Attribute(name = "process_unique")
	public static final BeanProperty<String> process_unique = BeanProperty.stringType();
}