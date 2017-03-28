package vn.gmobile.einvoice.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import snaq.db.ConnectionPool;
import vn.gmobile.einvoice.conf.DbConfig;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceProcess;

import com.bean.base.Bean;
import com.bean.base.Bean.BeanVisitor;
import com.bean.base.BeanFilter;
import com.bean.base.BeanSchema;
import com.bean.db.DataSource;
import com.bean.db.mysql.MysqlDbHelper;

public class DbAdapter {

	protected static final Logger LOGGER = Logger.getLogger(DbAdapter.class.getName());
	MysqlDbHelper _dataService;

	public static ConnectionPool POOL;

	Connection _con;

	static {
		POOL = new ConnectionPool(DbConfig.getPoolName(), DbConfig.getMinPool(), DbConfig.getMaxPool(),
				DbConfig.getMaxSize(), DbConfig.getIdleTimeout(), DbConfig.getlUrl(), DbConfig.getUser(),
				DbConfig.getPassword());

	}

	public class SetDefaultValueVisitor implements BeanVisitor {
		private Integer getAutoKey(String table) throws Exception {
			String SQL = "SELECT seq('" + table + "')";
			Statement statement = _con.createStatement();
			ResultSet generatedKeys = statement.executeQuery(SQL);
			if (generatedKeys.next()) {
				Integer nextVal = generatedKeys.getInt(1);
				return nextVal;
			}
			return 0;
		}

		/**
		 * This method is used to set ID for new Invoice Batch
		 * 
		 * @param invoice_batch
		 *            Invoice Batch
		 * @throws Exception
		 */
		private void visit(InvoiceBatch bean) throws Exception {
			if (bean.get(InvoiceBatch.invoice_batch_id) == null) {
				BeanSchema metaInfo = BeanSchema.loadSchema(InvoiceBatch.class);
				Integer id = getAutoKey(metaInfo.getTable());
				bean.set(InvoiceBatch.invoice_batch_id, id);
			}
		}
		
		/**
		 * This method is used to set ID for new Invoice Process
		 * 
		 * @param invoice_process
		 *            Invoice Process
		 * @throws Exception
		 */
		private void visit(InvoiceProcess bean) throws Exception {
			if (bean.get(InvoiceProcess.invoice_process_id) == null) {
				BeanSchema metaInfo = BeanSchema.loadSchema(InvoiceProcess.class);
				Integer id = getAutoKey(metaInfo.getTable());
				bean.set(InvoiceProcess.invoice_process_id, id);
			}
		}

		@Override
		public void visit(Bean b) throws Exception {
			if (b instanceof InvoiceBatch)
				visit((InvoiceBatch) b);
			if (b instanceof InvoiceProcess)
				visit((InvoiceProcess) b);
		}
	}

	public DbAdapter() throws Exception {
		_con = POOL.getConnection();
		_dataService = new MysqlDbHelper(_con);
	}

	public void close() {
		try {
			_con.close();
		} catch (SQLException e) {
			LOGGER.severe("BeanDbAdapter close connection" + e.getMessage());
			e.printStackTrace();
		}
	}

	public <T extends Bean> void processBatch(List<T> beans, List<String> cols) throws Exception {

		_dataService.insertOrUpdateBatch(beans.get(0).getClass(), beans, cols);
	}

	public <T extends Bean> List<T> getBeans(BeanFilter filter) throws Exception {
		LOGGER.info("Connection : " + _con);
		if (_con == null) {
			_con = POOL.getConnection();
			_dataService = new MysqlDbHelper(_con);
			LOGGER.info("Connection after regetConnection: " + _con);
			LOGGER.info("Data service:" + _dataService);
		}
		DataSource<T> dataSource = _dataService.getBeans(filter);
		List<T> beans = new ArrayList<T>();
		for (int i = 0; i < dataSource.getBeanCount(); i++)
			beans.add(dataSource.getBean(i));
		return beans;
	}
	
	public <T extends Bean> long countBeans(BeanFilter filter) throws Exception {
		LOGGER.info("Connection : " + _con);
		if (_con == null) {
			_con = POOL.getConnection();
			_dataService = new MysqlDbHelper(_con);
			LOGGER.info("Connection after regetConnection: " + _con);
			LOGGER.info("Data service:" + _dataService);
		}
		long quantity = _dataService.countBeans(filter);
		return quantity;
	}

	public <T extends Bean> void processBeans(T bean) throws Exception {
		processBeans(Arrays.asList(bean));
	}

	public <T extends Bean> void processBeans(List<T> beans) throws Exception {
		BeanVisitor defaulVisitor = new SetDefaultValueVisitor();
		for (T bean : beans) {
			bean.visit(defaulVisitor);
		}
		_dataService.insertOrUpdateBeans(beans);
	}

	public <T extends Bean> void removeBean(T bean) throws Exception {
		_dataService.removeBean(bean);
	}

	public void removeBeans(BeanFilter filter) throws Exception {
		_dataService.removeBeans(filter);
	}

}