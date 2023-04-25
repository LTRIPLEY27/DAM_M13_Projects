package com.reseed.util;

import android.util.Log;

import com.reseed.objects.MapPoint;
import com.reseed.objects.TaskComment;
import com.reseed.objects.TaskLocation;
import com.reseed.objects.TaskObj;
import com.reseed.objects.UserObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonReseedUtils {

	private JSONObject userJSONInfo;
	private UserObj userObj;

	private ArrayList<TaskObj> taskObjs;

	public JsonReseedUtils() {
	}

	/**
	 * Classe para convertir de JsonObject a TaskObj
	 *
	 * @param jsonProfileObject objeto request de la peticion de informacion del user
	 * @param token             token del usuario para añadir al UserObj
	 * @return devuelve el objeto UserObj, null en caso de error y muestra log error.
	 */
	//TODO finalizar la clase para convertir el objeto request en UserObj
	public UserObj convertToUserObj(JSONObject jsonProfileObject, String token) {

		try {
			userJSONInfo = jsonProfileObject;

			userObj = new UserObj(
					userJSONInfo.getString("id"),
					userJSONInfo.getString("user"),
					userJSONInfo.getString("nombre"),
					userJSONInfo.getString("apellido"),
					userJSONInfo.getString("email"),
					userJSONInfo.getString("telefono"),
					token,
					userJSONInfo.getString("rol"));

		} catch (JSONException e) {
			Log.e("Error en objeto Json Tarea", e.getMessage());
			return null;
			//throw new RuntimeException(e);
		}

		return userObj;
	}

	/**
	 * Método para extraer el objeto de tarea con el que trabajamos internamente en la app, del json.
	 *
	 * @param jsonProfileObject objeto json del usuario.
	 * @return Retorna un objeto ArrayList<TaskObj>
	 * @throws JSONException lanza esta excepción si el json no es correcto.
	 */
	public ArrayList<TaskObj> convertToTasksObjects(JSONObject jsonProfileObject) throws JSONException {

		JSONObject jsonObjectTask = new JSONObject();
		ArrayList<TaskObj> taskObj = new ArrayList<>();

		JSONArray tasksJson = jsonProfileObject.getJSONArray("tarea");

		for (int i = 0; i < tasksJson.length(); i++) {
			jsonObjectTask = tasksJson.getJSONObject(i);

			taskObj.add(new TaskObj(
					jsonObjectTask.getString("id"),
					jsonObjectTask.getString("name"),
					jsonObjectTask.getString("tarea"),
					jsonObjectTask.getString("fecha_culminacion"),
					jsonObjectTask.getString("fecha_creacion"),
					jsonObjectTask.getString("estatus"),
					jsonObjectTask.getString("tecnico"),
					jsonObjectTask.getString("admin"),
					extractLocation(jsonObjectTask.getJSONObject("ubicacion")),
					extractComments(jsonObjectTask.getJSONArray("mensajes"))
			));
		}

		return taskObj;
	}

	/**
	 * Metodo para convertir una tarea a objeto tarea {@link TaskObj}
	 *
	 * @param jsonTaskObject recibe un objeto tarea en formato json.
	 * @return devuelve un {@link TaskObj}.
	 * @throws JSONException
	 */
	public TaskObj convertToTaskObject(JSONObject jsonTaskObject) throws JSONException {

		return new TaskObj(
				jsonTaskObject.getString("id"),
				jsonTaskObject.getString("name"),
				jsonTaskObject.getString("tarea"),
				jsonTaskObject.getString("fecha_culminacion"),
				jsonTaskObject.getString("fecha_creacion"),
				jsonTaskObject.getString("estatus"),
				jsonTaskObject.getString("tecnico"),
				jsonTaskObject.getString("admin"),
				extractLocation(jsonTaskObject.getJSONObject("ubicacion")),
				extractComments(jsonTaskObject.getJSONArray("mensajes"))
		);
	}

	/**
	 * Extraemos el Objeto de localización del mapa.
	 *
	 * @param jsonObject La tarea
	 * @return Devolvemos la parte de la localización como un objeto de TaskLocation.
	 * @throws JSONException lanza esta excepción si el json no es correcto.
	 */
	private TaskLocation extractLocation(JSONObject jsonObject) throws JSONException {

		return new TaskLocation(
				jsonObject.getString("id"),
				Float.parseFloat(jsonObject.getString("centroLatitud")),
				Float.parseFloat(jsonObject.getString("centroLatitud")),
				jsonObject.getInt("zoom"),
				extractMap(jsonObject.getJSONArray("mapa")));
	}

	/**
	 * Método para extraer los puntos de los mapas.
	 *
	 * @param mapa Objeto json que corresponde con el JSONArray de los puntos del mapa.
	 * @return Devuelve un ArrayList<MapPoint> de los puntos del mapa.
	 */
	private ArrayList<MapPoint> extractMap(JSONArray mapa) throws JSONException {

		ArrayList<MapPoint> mapPoints = new ArrayList<>();

		for (int i = 0; i < mapa.length(); i++) {

			MapPoint mapPoint = new MapPoint(
					mapa.getJSONObject(i).getInt("id"),
					Float.parseFloat(mapa.getJSONObject(i).getString("latitud")),
					Float.parseFloat(mapa.getJSONObject(i).getString("longitud")));

			mapPoints.add(mapPoint);

		}
		return mapPoints;
	}

	/**
	 * Método para extraer los comentarios de la tarea.
	 *
	 * @param jsonArrayComments Objeto json array los comentarios de la tarea.
	 * @return Devolvemos un Arraylist de objetos comentario.
	 */
	private ArrayList<TaskComment> extractComments(JSONArray jsonArrayComments) throws JSONException {

		ArrayList<TaskComment> arrayListComments = new ArrayList<>();

		for (int i = 0; i < jsonArrayComments.length(); i++) {
			TaskComment taskComment = new TaskComment(
					jsonArrayComments.getJSONObject(i).getInt("id"),
					jsonArrayComments.getJSONObject(i).getString("descripcion"),
					jsonArrayComments.getJSONObject(i).getString("fecha"),
					jsonArrayComments.getJSONObject(i).getString("tarea"),
					jsonArrayComments.getJSONObject(i).getString("tecnico"),
					jsonArrayComments.getJSONObject(i).getString("admin")
			);

			arrayListComments.add(taskComment);

		}
		return arrayListComments;
	}

	/**
	 * Metodo que devuelve el objeto json de una tarea.
	 *
	 * @param jsonUserObject pide el objeto json del usuario.
	 * @param position       posicion del objeto en el array.
	 * @return devuelve un Array Json.
	 * @throws JSONException
	 */
	public JSONObject extractJsonTask(JSONObject jsonUserObject, int position) throws JSONException {

		return jsonUserObject.getJSONArray("tarea").getJSONObject(position);
	}
}
