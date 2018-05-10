package sistemske_operacije;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domenske_klase.Drzava;

public class SOVratiDrzave {

	public static final String service = "/countries";
	public static final String CURRENCY_LAYER_API_URL = "http://free.currencyconverterapi.com/api/v3";

	public static LinkedList<Drzava> izvrsi() {

		String url = CURRENCY_LAYER_API_URL + service;

		try {
			String content = SOUcitajSaURL.izvrsi(url);

			Gson gson = new GsonBuilder().create();

			JsonObject contentJson = gson.fromJson(content, JsonObject.class);

			JsonObject countriesJson = contentJson.get("results").getAsJsonObject();

			LinkedList<Drzava> list = new LinkedList<Drzava>();

			for (Entry<String, JsonElement> entry : countriesJson.entrySet()) {
				Drzava item = gson.fromJson(entry.getValue().getAsJsonObject(), Drzava.class);
				list.add(item);
			}
			return list;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}