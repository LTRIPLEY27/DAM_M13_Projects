using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Ubicacion
    {
        //ATRIBUTOS
        private String id;
        private String centroLatitud;
        private String centroLongitud;
        private String zoom;
        private String [] mapa;
        private String tarea;

        //CONSTRUCTOR
        public Ubicacion(String id, String centroLatitud, String centroLongitud, String zoom, String[] mapa, 
            String tarea) { 

            this.id = id;
            this.centroLatitud = centroLatitud;
            this.centroLongitud = centroLongitud;
            this.zoom = zoom;   
            this.mapa = mapa;
            this.tarea = tarea;
        
        }

        //GETTERS Y SETTERS
        public String Id {get { return id; }  set { id = value; }}
        public String CentroLatitud { get {  return centroLatitud; } set {  centroLatitud = value; }}
        public String CentroLongitud { get { return centroLongitud; } set { centroLongitud = value; }}
        public String Zoom { get { return zoom; } set { zoom = value; }}
        public String [] Mapa { get {  return mapa; } set {  mapa = value; }}
        public String Tarea { get { return tarea; } set { tarea = value; }}

       
    }
}
