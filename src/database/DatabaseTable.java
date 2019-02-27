package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public abstract class DatabaseTable 
{
			
	public abstract String[] getFields();
	
	public abstract String getTableName();

        public HashMap<String, Object> getObjectData(ResultSet rs) throws SQLException 
	{
		HashMap<String, Object> objectData = new HashMap<>();
		for(String fieldName: getFields()) 
		{
			objectData.put(fieldName, rs.getObject(fieldName));
		}
		return objectData;
	}
	
	
	public String getLoadAllSQL()
	{
		return "SELECT * FROM " + getTableName();
	}
	
	public String buildLoadAllWhereSQLString(String fieldName, String value)
	{
		return "SELECT * FROM " + getTableName() + " WHERE " + fieldName + " = '" + value + "'";
	}
	
	public ResultSet getObjectResultSet(String SQL) throws SQLException
	{
		return  ServerDatabaseConnection.Query(SQL);
	}
        
        public void sendTableUpdate(String SQL) throws SQLException
	{
		ServerDatabaseConnection.Update(SQL);
	}
}
