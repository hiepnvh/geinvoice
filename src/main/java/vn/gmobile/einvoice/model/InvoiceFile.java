package vn.gmobile.einvoice.model;

import java.util.Date;
import com.bean.annot.*;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "invoice_file")
public class InvoiceFile extends Bean {
	@Attribute(name = "invoice_detail_id")
	@ExternalKey
	public static final BeanProperty<Integer> invoice_detail_id = BeanProperty.integerType();
	@Attribute(name = "data_load_id")
	public static final BeanProperty<Integer> data_load_id = BeanProperty.integerType();
	@Attribute(name = "msisdn")
	public static final BeanProperty<String> msisdn = BeanProperty.stringType();
	@Attribute(name = "create_date")
	public static final BeanProperty<String> create_date = BeanProperty.stringType();
	@Attribute(name = "from_date")
	public static final BeanProperty<Date> from_date = BeanProperty.dateType();
	@Attribute(name = "to_date")
	public static final BeanProperty<Date> to_date = BeanProperty.dateType();
	@Attribute(name = "file_name_xml")
	public static final BeanProperty<String> file_name_xml = BeanProperty.stringType();
}