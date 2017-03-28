package vn.gmobile.einvoice.model;

import com.bean.annot.Attribute;
import com.bean.annot.Entity;
import com.bean.base.Bean;
import com.bean.base.BeanProperty;

@Entity(name = "system_param")
public class SystemParam extends Bean {
	@Attribute(name = "value")
	public static final BeanProperty<String> VALUE = BeanProperty.stringType();
	@Attribute(name = "name")
	public static final BeanProperty<String> NAME = BeanProperty.stringType();
}
