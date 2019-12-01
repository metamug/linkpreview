package net.metamug.scrapper.exec;

import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;
import java.util.Map;
import javax.sql.DataSource;
import net.metamug.scrapper.factory.MetaDataFactory;
import net.metamug.scrapper.entity.WebMetaData;

public class SnippetGenerator implements RequestProcessable {

    @Override
    public Response process(Request rqst, DataSource ds, Map<String, Object> params) throws Exception {
        String url = (String) params.get("url");
        WebMetaData result = null;
        try {
            result = MetaDataFactory.create(url);
        } catch (Exception e) {
            result = new WebMetaData("Unable to read information",
                    "", "", "Something is wrong with this webpage or we aren't good enough yet.",
                    "https://lh3.googleusercontent.com/-MVEG-8gyeEs/WX9f1ZSbspI/AAAAAAAACi0/muowN-vTfGsEfAjK1LvrETXIa0nz6OnUwCL0BGAYYCw/h215/2017-07-31.png");

        }
        Response response = new Response(result);
        return response;
    }
}
