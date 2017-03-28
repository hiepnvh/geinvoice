package vn.gmobile.einvoice.model;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_detail_status")
public class InvoiceDetailStatus extends Bean {
	@Attribute(name = "status")
	@ExternalKey
	public static final BeanProperty<Integer> status = BeanProperty.integerType();
	@Attribute(name = "text")
	public static final BeanProperty<String> text = BeanProperty.stringType();
	@Attribute(name = "description_short")
	public static final BeanProperty<String> description_short = BeanProperty.stringType();
	@Attribute(name = "description")
	public static final BeanProperty<String> description = BeanProperty.stringType();
}