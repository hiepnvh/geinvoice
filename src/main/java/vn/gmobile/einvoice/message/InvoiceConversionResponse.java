package vn.gmobile.einvoice.message;

public class InvoiceConversionResponse extends JsonResponse {
//	
//	public void setResultList(List<AccountInfoMaster> log) throws Exception {
//		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(log);
//		for(int i =0;i< jArr.length();i++){
//			JSONObject e = jArr.getJSONObject(i);
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			SimpleDateFormat targetSdf = new SimpleDateFormat("yyyy-MM-dd");
//			e.put("birthday", targetSdf.format(sdf.parse(e.getString("birthday"))));
//			e.put("issuedate", targetSdf.format(sdf.parse(e.getString("issuedate"))));
//			e.put("contract_received_date", targetSdf.format(sdf.parse(e.getString("contract_received_date"))));
//		}
//		write(JsParams.ACCOUNT_INFO_REQUEST_GET_RESPONSE.ACCOUNT_INFO_REQUEST_LIST, jArr);
//	}
//	
//	public void setResults(Integer total) throws Exception {
//		write(JsParams.ACCOUNT_INFO_REQUEST_GET_RESPONSE.TOTAL, total);
//	}
}
