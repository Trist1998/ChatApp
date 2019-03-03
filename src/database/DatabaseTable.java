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
	
	public void insert(String[] fieldNames, String[] fields, String[] nonStringFields) throws SQLException
        {
            String sql = "INSERT INTO " + getTableName() + " (";
            for (int i = 0; i < fieldNames.length; i++)
            {
                sql += fieldNames[i] + ((i == (fieldNames.length - 1))?"":", ");                
            }
            sql += ") VALUES(";
            for (int i = 0; i < fields.length; i++)
            {
                sql += "'" + fields[i] + "'"+ ((i == (fields.length - 1))?"":", ");                
            }
            
            for (int i = 0; i < nonStringFields.length; i++)
            {
                sql += "," + nonStringFields[i];                
            }
            sql += ")";
            System.out.println(sql + " SQL");
            
            sendTableUpdate(sql);
        }
        
	public String getLoadAllSQL()
	{
		return "SELECT * FROM " + getTableName();
	}
	
	public String buildLoadAllWhereSQLString(String fieldName, String value)
	{
            return "SELECT * FROM " + getTableName() + " WHERE " + fieldName + " = '" + value + "'";
	}
	
	public static ResultSet getObjectResultSet(String SQL) throws SQLException//Please remember to ServerDatabaseConnection.closeQuery(resultSetName)
	{
            return  ServerDatabaseConnection.query(SQL);
	}
        
        public void sendTableUpdate(String SQL) throws SQLException
	{
            ServerDatabaseConnection.update(SQL);
	}
}
