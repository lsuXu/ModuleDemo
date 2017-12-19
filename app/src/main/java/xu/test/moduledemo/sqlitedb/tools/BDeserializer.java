package xu.test.moduledemo.sqlitedb.tools;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import xu.test.moduledemo.sqlitedb.result.B;

/**
 * Created by 12852 on 2017/12/18.
 */

public class BDeserializer implements JsonDeserializer{
    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        final JsonObject jsonObject = jsonElement.getAsJsonObject();

        final String btnList = jsonObject.get("button").getAsString();
        final String productList = jsonObject.get("product").getAsString();
        final Long unique_id = jsonObject.get("uniqueId").getAsLong();

        final B b = new B();
        b.setUnique_id(unique_id);

        return b;
    }
}
