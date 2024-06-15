package handlers;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ThrowableAdapter extends TypeAdapter<Throwable> {
  @Override
  public void write(JsonWriter out, Throwable value) throws IOException {
    out.beginObject();
    out.name("message").value(value.getMessage());
    out.name("type").value(value.getClass().getName());
    out.endObject();
  }

  @Override
  public Throwable read(JsonReader in) throws IOException {
    in.beginObject();
    String message = null;
    String type = null;
    while (in.hasNext()) {
      switch (in.nextName()) {
        case "message":
          message = in.nextString();
          break;
        case "type":
          type = in.nextString();
          break;
      }
    }
    in.endObject();

    Throwable throwable;
    try {
      throwable = (Throwable) Class.forName(type).getConstructor(String.class).newInstance(message);
    } catch (Exception e) {
      throw new IOException("Failed to deserialize Throwable", e);
    }
    return throwable;
  }
}


