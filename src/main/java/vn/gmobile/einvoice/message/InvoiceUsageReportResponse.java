package vn.gmobile.einvoice.message;

import vn.gmobile.einvoice.conf.JsParams;

public class InvoiceUsageReportResponse extends JsonResponse {
	public void setFilePath(String file_path) throws Exception {
		write(JsParams.INVOICE_USAGE_REPORT_RESPONSE.file_path, file_path);
	}
}
