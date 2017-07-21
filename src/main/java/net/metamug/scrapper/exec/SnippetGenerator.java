package net.metamug.scrapper.exec;
import com.metamug.exec.RequestProcessable;
import java.util.Map;
import javax.sql.DataSource;
import net.metamug.scrapper.factory.MetaDataFactory;
import net.metamug.scrapper.entity.WebMetaData;


public class SnippetGenerator implements RequestProcessable {

    public Object process(Map<String, String> param, DataSource ds,
        Map<String, String> requestHeaders){
		String url = "http://stackoverflow.com/questions/5963269/how-to-make-a-great-r-reproducible-example";
        WebMetaData result = MetaDataFactory.create(url);

        return result;
	}
}