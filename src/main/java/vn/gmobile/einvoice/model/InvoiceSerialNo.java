package vn.gmobile.einvoice.model;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_serial_no")
public class InvoiceSerialNo extends Bean {
	@Attribute(name = "serial")
	@ExternalKey
	public static final BeanProperty<Integer> serial = BeanProperty.integerType();
	@Attribute(name = "invoice_current_no")
	public static final BeanProperty<Integer> invoice_current_no = BeanProperty.integerType();
}