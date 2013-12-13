package com.flexxo.mobil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.flexxo.mobil.infra.vo.imovel.Imovel;
import com.flexxo.mobil.infra.vo.imovel.TipoImovel;

public class WebService {

		private final static String URL = "http://10.100.10.177/MvcApplication1";

		public void receberTiposImovel() throws JSONException {
			String path = "api/TipoImovel";
			JSONArray json = new JSONArray(request(path));
			TipoImovel.clearAll();
			for (int i = 0; i<json.length();i++){
				new TipoImovel((JSONObject)json.get(i)).save();
			}
		}
		
		public void receberImovel() throws JSONException {
			String path = "api/Imovel";
			JSONArray json = new JSONArray(request(path));
			Imovel.clearAll();
			for (int i = 0; i<json.length();i++){
				new Imovel((JSONObject)json.get(i)).save();
			}
		}

		public String request(String path) {
			StringBuilder stringBuilder = new StringBuilder();
			HttpClient httpClient = new DefaultHttpClient();

			try {
				HttpResponse response;
				if (1 == 2) {
					HttpPost httpPost = new HttpPost(URL + "/" + path);
					httpPost.addHeader("Accept-Encoding", "gzip");
					JSONObject json = new JSONObject();
					json.put("ID", 1);
					json.put("NOME", "MANOEL");
					StringEntity se = new StringEntity(json.toString());
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
					httpPost.setEntity(se);
					response = httpClient.execute(httpPost);
				}else{
					HttpGet httpGet = new HttpGet(URL+ "/" + path);
					response = httpClient.execute(httpGet);
				}
				
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line);
					}
					inputStream.close();
					// JSONObject obj = new
					// JSONObject(stringBuilder.toString()).get("NOME");
				} else {
					Log.d("JSON", "Failed to download file");
				}
			} catch (Exception e) {
				e.printStackTrace();
//				Log.e("readJSONFeed", e.getLocalizedMessage());
			}

			return stringBuilder.toString();

		}

		// private class ReadWeatherJSONFeedTask extends AsyncTask<String, Void,
		// String> {
		// protected String doInBackground(String... urls) {
		// return WebService.re;
		// }
		//
		// protected void onPostExecute(String result) {
		// try {
		// // JSONObject jsonObject = new JSONObject(result);
		// // JSONArray arr = new JSONArray(result)
		// JSONArray jsonArrayGeoPoint = new JSONArray(result);
		// // new JSONArray(jsonObject
		// // .getString("jsondataResult").toString());
		// // Toast.makeText(
		// // getApplicationContext(),
		// // jsonArrayGeoPoint.getString(0).toString() + "||"
		// // + jsonArrayGeoPoint.getString(1).toString(),
		// // Toast.LENGTH_SHORT).show();
		//
		// // String[] strArrtemp = new String[5];
		// // Double[] strArrLat = new Double[5];
		// // Double[] strArrLon = new Double[5];
		//
		// for (int i = 0; i < jsonArrayGeoPoint.length(); i++) {
		// try {
		// Log.d("Teste", jsonArrayGeoPoint.getJSONObject(i).getString("ID"));
		//
		// } catch (JSONException e) {
		// Log.e("JsonArray ERROR", e.getLocalizedMessage());
		// }
		// }
		//
		// // String[] arrytemp = new String[2];
		// // String temp;
		// //
		// // for (int i = 0; i < strArrtemp.length; i++) {
		// // temp = strArrtemp[i].toString();
		// // strArrLat[i] = Double.parseDouble(temp.substring(0, 6));
		// // strArrLon[i] = Double.parseDouble(temp.substring(7, 13));
		// // }
		// // Toast.makeText(
		// // getApplicationContext(),
		// // "Lat1=" + strArrLat[1].toString() + " & Lon1="
		// // + strArrLon.toString(), Toast.LENGTH_SHORT)
		// // .show();
		// // Button getDir;
		// // getDir = (Button)findViewById(R.id.getLocationBtn);
		// // getDir.setText(strArrLat[1].toString());
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// Log.e("ReadWeatherJSONFeedTask", e.getMessage());
		// }
		// }
	}