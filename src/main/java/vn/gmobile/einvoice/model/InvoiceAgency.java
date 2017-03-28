package vn.gmobile.einvoice.model;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_agency")
public class InvoiceAgency extends Bean {
	@Attribute(name = "invoice_agency_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_agency_id = BeanProperty.integerType();
	@Attribute(name = "seller")
	public static final BeanProperty<String> seller = BeanProperty.stringType();
	@Attribute(name = "tin")
	public static final BeanProperty<String> tin = BeanProperty.stringType();
	@Attribute(name = "address")
	public static final BeanProperty<String> address = BeanProperty.stringType();
	@Attribute(name = "tel")
	public static final BeanProperty<String> tel = BeanProperty.stringType();
	@Attribute(name = "fax")
	public static final BeanProperty<String> fax = BeanProperty.stringType();
}