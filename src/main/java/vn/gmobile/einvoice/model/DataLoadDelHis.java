package vn.gmobile.einvoice.model;

import java.util.Date;
import com.bean.annot.*;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "data_load_del_his")
public class DataLoadDelHis extends Bean {
	@Attribute(name = "data_load_id")
	@ExternalKey
	public static final BeanProperty<Integer> data_load_id = BeanProperty.integerType();
	@Attribute(name = "create_date")
	public static final BeanProperty<Date> create_date = BeanProperty.dateType();
	@Attribute(name = "symbol_of_invoice")
	public static final BeanProperty<String> symbol_of_invoice = BeanProperty.stringType();
	@Attribute(name = "serial")
	public static final BeanProperty<String> serial = BeanProperty.stringType();
	@Attribute(name = "invoice_no")
	public static final BeanProperty<String> invoice_no = BeanProperty.stringType();
	@Attribute(name = "customer_name")
	public static final BeanProperty<String> customer_name = BeanProperty.stringType();
	@Attribute(name = "company_name")
	public static final BeanProperty<String> company_name = BeanProperty.stringType();
	@Attribute(name = "tax_code")
	public static final BeanProperty<String> tax_code = BeanProperty.stringType();
	@Attribute(name = "address")
	public static final BeanProperty<String> address = BeanProperty.stringType();
	@Attribute(name = "msisdn")
	public static final BeanProperty<String> msisdn = BeanProperty.stringType();
	@Attribute(name = "customer_code")
	public static final BeanProperty<String> customer_code = BeanProperty.stringType();
	@Attribute(name = "erp_customer_code")
	public static final BeanProperty<String> erp_customer_code = BeanProperty.stringType();
	@Attribute(name = "from_date")
	public static final BeanProperty<Date> from_date = BeanProperty.dateType();
	@Attribute(name = "to_date")
	public static final BeanProperty<Date> to_date = BeanProperty.dateType();
	@Attribute(name = "kind_of_payment")
	public static final BeanProperty<String> kind_of_payment = BeanProperty.stringType();
	@Attribute(name = "kind_of_service_no")
	public static final BeanProperty<String> kind_of_service_no = BeanProperty.stringType();
	@Attribute(name = "kind_of_service")
	public static final BeanProperty<String> kind_of_service = BeanProperty.stringType();
	@Attribute(name = "unit")
	public static final BeanProperty<String> unit = BeanProperty.stringType();
	@Attribute(name = "quantity")
	public static final BeanProperty<Integer> quantity = BeanProperty.integerType();
	@Attribute(name = "unit_price")
	public static final BeanProperty<Integer> unit_price = BeanProperty.integerType();
	@Attribute(name = "amount")
	public static final BeanProperty<Integer> amount = BeanProperty.integerType();
	@Attribute(name = "total")
	public static final BeanProperty<Integer> total = BeanProperty.integerType();
	@Attribute(name = "vat_rate")
	public static final BeanProperty<Integer> vat_rate = BeanProperty.integerType();
	@Attribute(name = "vat_amount")
	public static final BeanProperty<Integer> vat_amount = BeanProperty.integerType();
	@Attribute(name = "grand_total")
	public static final BeanProperty<Integer> grand_total = BeanProperty.integerType();
	@Attribute(name = "sum_in_words")
	public static final BeanProperty<String> sum_in_words = BeanProperty.stringType();
	@Attribute(name = "used")
	public static final BeanProperty<Boolean> used = BeanProperty.boolType();
	@Attribute(name = "username")
	public static final BeanProperty<String> username = BeanProperty.stringType();
	@Attribute(name = "adjust_type")
	public static final BeanProperty<Integer> adjust_type = BeanProperty.integerType();
	@Attribute(name = "parent_invoice_no")
	public static final BeanProperty<String> parent_invoice_no = BeanProperty.stringType();
	@Attribute(name = "import_date")
	public static final BeanProperty<Date> import_date = BeanProperty.dateType();
}
