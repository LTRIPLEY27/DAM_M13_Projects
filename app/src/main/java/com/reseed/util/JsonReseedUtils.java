package com.reseed.util;

import com.reseed.objects.TaskObj;
import com.reseed.objects.UserObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonReseedUtils {

    private JSONObject userJSONInfo;
    private JSONArray tasksJson;
    private UserObj userObj;

    private ArrayList<TaskObj> taskObjs;

    public JsonReseedUtils() {
    }

    /**
     * Classe para convertir de JsonObject a TaskObj
     * @param jsonProfileObject objeto request de la peticion de informacion del user
     * @param token token del usuario para añadir al UserObj
     * @return devuelve el objeto UserObj
     */

    //TODO finalizar la clase para convertir el objeto request en UserObj
    public UserObj convertToUserObj(JSONObject jsonProfileObject, String token){

        try {
            userJSONInfo = jsonProfileObject;

            userObj = new UserObj(
                    userJSONInfo.getString("user"),
                    userJSONInfo.getString("nombre"),
                    userJSONInfo.getString("apellido"),
                    userJSONInfo.getString("email"),
                    userJSONInfo.getString("telefono"),
                    token,
                    userJSONInfo.getString("rol"));

            tasksJson = userJSONInfo.getJSONArray("tarea");

            for (int i = 0; i < tasksJson.length(); i++) {
                extractTask(tasksJson.getJSONObject(i));
            }
            /*Iterator<String> taskObjIterator = tasksJson.keys();
            while (taskObjIterator.hasNext()){

                JSONObject jsonObject = (JSONObject) tasksJson.get(taskObjIterator.next());
            }*/
            
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return userObj;
    }

    private TaskObj extractTask(JSONObject jsonObject) {
        //TODO finalizar
        /*TaskObj taskObj = new TaskObj(
                jsonObject.getString("name"),
                jsonObject.getString())
*/
        return null;
    }

    /**
     * Classe para testear la estructura de el JsonObject
     * @param jsonUserObject
     * @return true si es correcto y false si el objeto no esta bien estructurado.
     */

    //TODO hacer la clase de test para comprovar el objeto de user de la request.
    public boolean testJsonUser(JSONObject jsonUserObject){
        return false;
    }
}
