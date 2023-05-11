using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Mensaje
    {
        //ATRIBUTOS
        private String id;
        private String descripcion;
        private String fecha;
        private String tecnico;
        private String admin;

        //CONSTRUCTOR
        public Mensaje(String id, String descripcion, String fecha, String tecnico, String admin)
        {
            this.id = id;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.tecnico = tecnico;
            this.admin = admin; 

        }

        //GETTERS Y SETTERS
        public String Id { get { return id; } set { id = value;  } }
        public String Descripcion { get {  return descripcion; } set {  descripcion = value; } }
        public String Fecha { get {  return fecha; } set {  fecha = value; } }
        public String Tecnico { get { return tecnico; } set { tecnico = value; } }
        public String Admin { get { return admin; } set {admin = value;} }

    }
}
