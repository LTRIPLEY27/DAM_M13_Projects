using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Coordenada
    {
        //ATRIBUTOS
        private String id;
        private Double latitud;
        private Double longitud;

        //CONTRUCTOR
        public Coordenada(String id, Double latitud, Double longitud) { 
        
            this.id = id;
            this.latitud = latitud;
            this.longitud = longitud;   

        }

        //CONSTRUCTOR 2
        public Coordenada (Double latitud, Double longitud)
        {
            this.latitud = latitud;
            this.longitud = longitud;
        }

        //GETTERS Y SETTERS
        public String Id { get { return id; } set { id = value; }}
        public Double Latitud { get {  return latitud; } set {  latitud = value; }}
        public Double Longitud { get { return longitud; } set {  longitud = value; }}

    }
}
