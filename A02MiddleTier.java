import java.sql.*;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;



 /*
 * Name: Parth Barot
 * Std Id: 216827107
 */


public class A02MiddleTier extends A02FrontEnd {
	

		static JCheckBox EC = eventConference;
	
		static JCheckBox EJ = eventJournal ;
		static JCheckBox EB = eventBook;
		static JRadioButton Dates_all =allDates;
		static JRadioButton date_Range = dateRange;

		public static String getQuery() {
		
		
		String result="";
		String firstdate,seconddate;
		String DBquery = "";
		
		try{  
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/a02schema","root","user1234");  
		Statement st = con.createStatement();
		
		
		if(EC.isSelected() && EJ.isSelected() && EB.isSelected() && Dates_all.isSelected())
			{	
			
			DBquery = "Select * from event";
			
			}
		
		else if(EC.isSelected() && EJ.isSelected() && Dates_all.isSelected())
			{	
			
			DBquery = "Select X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X LEFT JOIN EventConference Y ON X.ID = Y.EventID LEFT JOIN EventJournal Z ON X.ID = Z.EventID where X.ID = Y.EventID OR X.ID = Z.EventID;";
			
			}
		
		else if(EC.isSelected() && EB.isSelected() && Dates_all.isSelected())
			{
			
			DBquery = "Select X.ID ,X.Name ,X.EventWEBLink ,X.CFPText from Event X LEFT JOIN EventConference Y ON X.ID = Y.EventID LEFT JOIN EventBook Z ON X.ID = Z.EventID where X.ID = Y.EventID OR X.ID = Z.EventID;";
			
			}
		
		else if(EJ.isSelected() && EB.isSelected() && Dates_all.isSelected())
			{
			
			DBquery = "Select X.ID ,X.Name ,X.EventWEBLink ,X.CFPText from Event X LEFT JOIN EventJournal Y ON X.ID = Y.EventID LEFT JOIN EventBook Z ON X.ID = Z.EventID where X.ID = Y.EventID OR X.ID = Z.EventID;";
			
			}
		

		//if more than one chechbox along with period is to be displayed
		//if one checkbox is selected stating the period of the events
				
		else if(EC.isSelected() && date_Range.isSelected())
			{	
			
			firstdate = fromDate.getText();
			seconddate = toDate.getText();
			DBquery = "Select X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X , EventConference Y where X.ID = Y.EventID and Y.EvDate >= '"+firstdate+"' and Y.EvDate<= '"+seconddate+"' ;";
			
			} 
		 
		else if(EJ.isSelected() && date_Range.isSelected())
			{
			
			firstdate = fromDate.getText();
			seconddate = toDate.getText();
			DBquery = "Select distinct X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X LEFT JOIN EventJournal Y ON X.ID = Y.EventID LEFT JOIN ActivityHappens Z ON X.ID = Z.EventID where Y.EventID=Z.EventID and Z.ActivityDate>= '"+firstdate+"' and Z.ActivityDate<= '"+seconddate+"';";
			
			} 
		
		else if(EB.isSelected() && date_Range.isSelected())
			{	
			
			firstdate = fromDate.getText();
			seconddate = toDate.getText();
			DBquery = "Select distinct X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X LEFT JOIN EventBook Y ON X.ID = Y.EventID LEFT JOIN ActivityHappens Z ON X.ID = Z.EventID where Y.EventID=Z.EventID and Z.ActivityDate>= '"+firstdate+"' and Z.ActivityDate<= '"+seconddate+"';";
			
			} 
		
		
		else if(EC.isSelected() && EJ.isSelected() && date_Range.isSelected())
			{	
			
			firstdate = fromDate.getText();
			seconddate = toDate.getText();
			DBquery = "Select X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X , EventConference Y where X.ID = Y.EventID and Y.EvDate >= '"+firstdate+"' and Y.EvDate<= '"+seconddate+"' ;";
			
			}
		
		
		 //only one checkbox is selected stating all events
		else if(EC.isSelected() && Dates_all.isSelected())
			{
			
				DBquery = "Select X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X , EventConference Y where X.ID = Y.EventID;";
				
			}
			
		else if(EJ.isSelected() && Dates_all.isSelected())
			{
				
				DBquery = "Select X.ID, X.Name, X.EventWEBLink, X.CFPText from Event X , EventJournal Y where X.ID = Y.EventID;";
			
			}
			
		else if(EB.isSelected() && Dates_all.isSelected())
			{
			
				DBquery = "Select X.ID ,X.Name ,X.EventWEBLink ,X.CFPText from Event X , EventBook Y where X.ID = Y.EventID;";
			
			}
		
			PreparedStatement prest = con.prepareStatement(DBquery);
	
			ResultSet rset = prest.executeQuery();
				
			while(rset.next())
			{
				
				int id = rset.getInt(1);
				String name=rset.getString(2);
				String link=rset.getString(3);
				String cfp=rset.getString(4);
				result = result +id+" | \t"+name+" | \t"+link+" | \t "+cfp+"\t"+ "\n";
				
			}
	
			con.close();  
			
			}
		
		
			catch(Exception e)
			
			{
			
				System.out.println(e);
		
			}  
		
		return result;
		}  
}