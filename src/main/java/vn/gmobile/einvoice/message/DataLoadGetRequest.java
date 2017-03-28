package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONObject;
import com.base.filter.BaseFilter;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IsNullFilter;
import com.base.filter.StringEqualFilter;
import com.base.filter.StringLikeFilter;
import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.model.DataLoad;

public class DataLoadGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public DataLoadGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		_filter = new BeanFilter(DataLoad.class);
		if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.msisdn)){
			_filter.setFilter(DataLoad.msisdn, new StringEqualFilter(jObj.getString(JsParams.DATA_LOAD_GET_REQUEST.msisdn)));
		}
		if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.used)){
			_filter.setFilter(DataLoad.used, new IntegerEqualFilter(jObj.getInt(JsParams.DATA_LOAD_GET_REQUEST.used)));
		}
		if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.serial)){
			_filter.setFilter(DataLoad.serial, new StringLikeFilter(jObj.getString(JsParams.DATA_LOAD_GET_REQUEST.serial)));
		}
		if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.limit) && jObj.has(JsParams.DATA_LOAD_GET_REQUEST.start)){
			offset = Integer.parseInt(jObj.getString(JsParams.DATA_LOAD_GET_REQUEST.start));
			limit = Integer.parseInt(jObj.getString(JsParams.DATA_LOAD_GET_REQUEST.limit));
		}
		if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.adjust)){
			if(jObj.getBoolean(JsParams.DATA_LOAD_GET_REQUEST.adjust)){
				BaseFilter<?> strinNotEqualFilter = new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK);
				strinNotEqualFilter.setNegated(true);
				_filter.setFilter(DataLoad.parent_invoice_no, strinNotEqualFilter);
				if(jObj.has(JsParams.DATA_LOAD_GET_REQUEST.adjust_type)){
					_filter.setFilter(DataLoad.adjust_type, new IntegerEqualFilter(jObj.getInt(JsParams.DATA_LOAD_GET_REQUEST.adjust_type)));
				}
			} else {
				_filter.setFilter(DataLoad.parent_invoice_no, new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK));
			}
		}
	}

	BeanFilter _filter;
	int offset = 0, limit = 25;

	@Override
	public JsonResponse execute() {
		DataLoadGetResponse resp = new DataLoadGetResponse();
		try {
			DataLoadDAO dao = new DataLoadDAO();
			resp.setTotal(dao.countBeans(_filter));
			_filter.setLimit(offset, limit);
			List<DataLoad> beans = dao.getBeans(_filter);
			resp.setResult(beans);
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
}
