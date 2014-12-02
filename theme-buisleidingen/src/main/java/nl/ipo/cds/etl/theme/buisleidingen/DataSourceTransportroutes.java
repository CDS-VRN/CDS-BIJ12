package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class DataSourceTransportroutes implements Transportroutes {

	private final DataSource dataSource;
	
	public DataSourceTransportroutes (final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Set<String> getTransportrouteIds () {
		final Connection connection = DataSourceUtils.getConnection (dataSource);
		final HashSet<String> ids = new HashSet<> ();
		
		try {
			final Statement statement = connection.createStatement ();
			final ResultSet resultSet = statement.executeQuery ("select distinct transportroute_id from bron.buisleidingen_transportroutedeel");
			
			while (resultSet.next ()) {
				ids.add (resultSet.getString (1));
			}
		} catch (SQLException e) {
			throw new RuntimeException (e);
		} finally {
			DataSourceUtils.releaseConnection (connection, dataSource);
			
		}
		return ids;
	}
}
