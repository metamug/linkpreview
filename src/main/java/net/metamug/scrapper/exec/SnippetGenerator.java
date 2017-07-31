package net.metamug.scrapper.exec;
import com.metamug.exec.RequestProcessable;
import java.util.Map;
import javax.sql.DataSource;
import net.metamug.scrapper.factory.MetaDataFactory;
import net.metamug.scrapper.entity.WebMetaData;


public class SnippetGenerator implements RequestProcessable {

    public Object process(Map<String, String> param, DataSource ds,
        Map<String, String> requestHeaders){
		String url = param.get("url");
        WebMetaData result = null;
        try{
        	result = MetaDataFactory.create(url);
        }catch(Exception e){
        	result = new WebMetaData("Unable to read information",
        	 "", "", "Something is wrong with this webpage or we aren't good enough yet.", 
        	 "https://lh3.googleusercontent.com/-MVEG-8gyeEs/WX9f1ZSbspI/AAAAAAAACi0/muowN-vTfGsEfAjK1LvrETXIa0nz6OnUwCL0BGAYYCw/h215/2017-07-31.png");
        	return result;
        }
        return result;
	}
}