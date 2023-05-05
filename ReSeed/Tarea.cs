using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Tarea
    {
        //ATRIBUTOS
        private String id;
        private String name;
        private String fecha_creacion;
        private String fecha_culminacion;
        private String tarea;
        private String estatus;
        private String tecnico;
        private String admin;
        private Ubicacion ubicacion;
        private String[] mensajes;
        private String ubicacionId;

        //CONTRUCTOR
        public Tarea (String id,String name, String fecha_creacion, String fecha_culminacion, String tarea, String estatus, String tecnico,
            String admin, Ubicacion ubicacion, String[] mensajes, String ubicacionId) { 
        
            this.id = id;
            this.name = name;
            this.fecha_creacion = fecha_creacion;
            this.fecha_culminacion = fecha_culminacion;
            this.tarea = tarea;
            this.estatus = estatus;
            this.tecnico = tecnico;
            this.admin = admin;
            this.ubicacion = ubicacion;
            this.mensajes = mensajes;
            this.ubicacionId = ubicacionId;

        }

        //GETTERS Y SETTERS
        public String Id {
            get { return id; }
            set { id = value; }
        }
        public String Name { 
            get { return name; } 
            set { name = value; }
        }
        public String Fecha_creacion { 
            get { return fecha_creacion; }
            set { fecha_creacion =  value; }
        }
        public String Fecha_culminacion {
            get { return fecha_culminacion;}
            set { fecha_culminacion= value; }   
        }
        public String TArea { 
            get { return tarea; }
            set {tarea = value;}
        }
        public String Estatus {
            get { return estatus; }
            set { estatus = value; }
        }
        public String Tecnico {
            get { return tecnico; }
            set { tecnico = value; }
        }
        public String Admin {
            get { return admin; }
            set { admin = value; }
        }
        public Ubicacion Ubicacion {
            get { return ubicacion; }
            set { ubicacion = value; }
        }
        public String[] Mensajes {
            get { return mensajes; }
            set { mensajes = value; }
        }
        public String UbicacionId {
            get { return ubicacionId; }
            set { ubicacionId = value; }
        }



    }
}
