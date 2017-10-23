import java.sql.DriverManager

user = "admuser";
password ="admuser";
gatewayHost = "localhost";
gatewayPort = 8443;
contextPath = "gateway/sandbox/hive";
connectionString = String.format( "jdbc:hive2://%s:%d/?hive.server2.servermode=https;hive.server2.http.path=%s", gatewayHost, gatewayPort, contextPath );

 // Load Hive JDBC Driver
Class.forName( "org.apache.hive.jdbc.HiveDriver" );
 
// Configure JDBC connection
connection = DriverManager.getConnection( connectionString, user, password );

statement = connection.createStatement();
 
// Disable Hive authorization - This can be ommited if Hive authorization is configured properly
statement.execute( "set hive.security.authorization.enabled=false" );
 
// Create sample table
//statement.execute( "CREATE TABLE logs(column1 string, column2 string, column3 string, column4 string, column5 string, column6 string, column7 string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ' '" );
