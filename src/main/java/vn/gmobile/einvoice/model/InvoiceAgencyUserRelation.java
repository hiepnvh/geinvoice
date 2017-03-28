package vn.gmobile.einvoice.model;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.annot.ExternalKey;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_agency_user_relation")
public class InvoiceAgencyUserRelation extends Bean {
	@Attribute(name = "invoice_agency_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_agency_id = BeanProperty.integerType();
	@Attribute(name = "username")
	@ExternalKey
	public static final BeanProperty<Integer> username = BeanProperty.integerType();
}